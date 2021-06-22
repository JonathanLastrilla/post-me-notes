/*
 * The MIT License
 *
 * Copyright 2021 jlastril.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.jon.postmenotes.core;

import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jlastril
 */
public class ReminderManager {

    private final static Logger LOG = Logger.getLogger(ReminderManager.class.getName());
    private static final File REMINDER_FILE = new File(com.jon.postmenotes.Main.HOME_DIR, "r.eminder");
    private static final List<NoteReminder> REMINDERS = new ArrayList<>();

    private static ReminderManager INSTANCE = new ReminderManager();

    public static ReminderManager getInstance() {
        return INSTANCE;
    }

    static {
        if (REMINDER_FILE.exists()) {
            deserialize();
        }
    }

    public static int getSavedNotifications() {
        return REMINDERS.size();
    }

    public static void serialize() {
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REMINDER_FILE))) {
            oos.writeObject(REMINDERS);
            LOG.info("reminders saved");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deserialize() {
        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(REMINDER_FILE))) {
            List<NoteReminder> fromFile = (List<NoteReminder>) ois.readObject();
            LOG.log(Level.INFO, "loading reminders {0}", fromFile.size());
            REMINDERS.addAll(fromFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean contextInitialized = false;
    List<NoteReminder> context = new ArrayList<>();
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    Note contextNote;
    NoteReminder reminderInContext;

    public ReminderManager initializeContext(Note note) {
        contextNote = note;
        REMINDERS.stream()
                .filter(rem -> rem.getNote().equals(note.getTitle()))
                .filter(rem -> !rem.isExpired())
                .forEach(context::add);
        contextInitialized = true;
        return this;
    }

    private void clearContext() {
        reminderInContext = null;
        context.clear();
        contextInitialized = false;
    }

    public ReminderManager newReminderInContext(String message, LocalDateTime when) {
        if (!contextInitialized) {
            throw new RuntimeException("must call initializeContext first");
        }
        reminderInContext = new NoteReminder(contextNote, message, when);
        REMINDERS.add(reminderInContext);
        return this;
    }

    public void scheduleReminderInContextNow(final TrayIcon icon) {

        if (reminderInContext == null) {
            throw new RuntimeException("no reminder in context");
        }

        TrayIconRunnable r = new TrayIconRunnable() {

            @Override
            protected TrayIcon getIcon() {
                return icon;
            }

        };
        long delay = ChronoUnit.MINUTES.between(LocalDateTime.now(), reminderInContext.getRemindAt());

        LOG.log(Level.INFO, "scheduling {0} {1}, delay={2}", new Object[]{
            reminderInContext.getMessage(),
            reminderInContext.getRemindAt(),
            delay});

        r.setReminder(reminderInContext);
        scheduler.schedule(r, delay, TimeUnit.MINUTES);

        clearContext();
    }

    public void scheduleUnexpiredNow(final TrayIcon icon) {
        if (!contextInitialized) {
            throw new RuntimeException("must call initializeContext first");
        }

        for (NoteReminder noteReminder : context) {
            long delay = ChronoUnit.MINUTES.between(LocalDateTime.now(), noteReminder.getRemindAt());
            TrayIconRunnable r = new TrayIconRunnable() {
                @Override
                protected TrayIcon getIcon() {
                    return icon;
                }
            };
            r.setReminder(noteReminder);

            scheduler.schedule(r, delay, TimeUnit.MINUTES);

            LOG.log(Level.INFO, "scheduling {0} {1}, delay={2}", new Object[]{
                noteReminder.getMessage(),
                noteReminder.getRemindAt(),
                delay});
        }
        clearContext();
    }

    public static class NoteReminder implements Serializable {

        public final static long serialVersionUID = 5265436L;
        private String note;
        private String message;
        private String lastParagraph;
        private LocalDateTime remindAt;
        private long id = 0;
        private boolean expired = false;

        {
            id++;
        }

        public NoteReminder(Note note, String message, LocalDateTime remindAt) {
            this.note = note.getTitle();
            this.message = message;
            this.remindAt = remindAt;
            this.lastParagraph = NoteUtility.getInstance(note).getLastParagraph();
        }

        public LocalDateTime getRemindAt() {
            return remindAt;
        }

        public void setRemindAt(LocalDateTime remindAt) {
            this.remindAt = remindAt;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getId() {
            return id;
        }

        public boolean isExpired() {
            return expired;
        }

        public void setExpired(boolean expired) {
            this.expired = expired;
        }

        public String getLastParagraph() {
            return lastParagraph;
        }

    }

    static abstract class TrayIconRunnable implements Runnable {

        private TrayIcon icon;
        private NoteReminder reminder;
        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        private ActionListener al;
        private int DETAIL_EXPIRY_SECONDS = 5;

        protected TrayIcon getIcon() {
            return icon;
        }

        public void setIcon(TrayIcon icon) {
            this.icon = icon;
        }

        public LocalDateTime getRemindAt() {
            return reminder.getRemindAt();
        }

        public String getNote() {
            return reminder.getNote();
        }

        public String getMessage() {
            return reminder.getMessage();
        }

        public String getNoteContent() {
            return reminder.getLastParagraph();
        }

        protected void setReminder(NoteReminder reminder) {
            this.reminder = reminder;
        }

        @Override
        public void run() {
            String formattedMessage = String.format("%s: %s", formatter.format(getRemindAt()), getMessage());
            getIcon().displayMessage(getNote(), formattedMessage, TrayIcon.MessageType.INFO);
            al = newActionListener();
            setActionListener(al);
            autoExpireActionListener();
            reminder.setExpired(true);
        }

        private ActionListener newActionListener() {
            return e -> {
                StringBuilder message = new StringBuilder()
                        .append("<html>")
                        .append("<h3>").append(formatter.format(getRemindAt())).append("</h3>")
                        .append("<h4>").append(getMessage()).append("</h4>")
                        .append("<hr/>").append(getNoteContent())
                        .append("");
                JOptionPane.showMessageDialog(null, message, "REMINDER: " + getNote(), JOptionPane.INFORMATION_MESSAGE);
                removeActionListener(al);
            };
        }

        private void setActionListener(ActionListener al) {
            getIcon().addActionListener(al);
        }

        private void removeActionListener(ActionListener al) {
            getIcon().removeActionListener(al);
        }

        private void autoExpireActionListener() {
            new Thread(() -> {
                try {
                    Thread.sleep(DETAIL_EXPIRY_SECONDS * 1000);
                    TrayIcon ti = getIcon();
                    synchronized (ti) {
                        for (ActionListener actionListener : ti.getActionListeners()) {
                            if (al != null && actionListener.equals(al)) {
                                removeActionListener(al);
                                LOG.log(Level.INFO, "detail action listener {0} expired", al);
                            }
                        }
                    }
                } catch (InterruptedException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }).start();
        }
    }

//    public static void main(String[] args) throws AWTException {
//        SystemTray tray = SystemTray.getSystemTray();
//        TrayIcon dummy = new TrayIcon(createImageIcon(ICON_NAME, "")
//                .getImage()
//                .getScaledInstance(tray.getTrayIconSize().height,
//                        tray.getTrayIconSize().height,
//                        Image.SCALE_DEFAULT), "test");
//
//        tray.add(dummy);
//
//        ReminderManager mgr = getInstance();
//        Note note = new DefaultNoteInstance("NOTE");
//        mgr.initializeContext(note)
//                .newReminderInContext("do this", ChronoUnit.MINUTES.addTo(LocalDateTime.now(), 2))
//                .scheduleReminderInContextNow(dummy);
//
//    }
}
