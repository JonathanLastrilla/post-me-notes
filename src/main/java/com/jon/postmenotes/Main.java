/*
 * 
 * 
 * 
 */
package com.jon.postmenotes;

import com.jon.postmenotes.core.NotesManager;
import com.jon.postmenotes.core.Note;
import com.jon.postmenotes.core.NoteUtility;
import com.jon.postmenotes.core.Preference;
import com.jon.postmenotes.core.PreferenceEvent;
import com.jon.postmenotes.core.PreferenceListener;
import com.jon.postmenotes.core.ReminderManager;
import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author jlastril
 */
public class Main {

    static final Logger LOG = Logger.getLogger(Main.class.getName());
    public static final String APP_DIRNAME = ".postMeNotes";
    private static final String DATA_FILENAME = "d.data";
    private static final String SCHEMES_FILENAME = "s.chemes";
    private static final String VERSION_DIRECTORY = "v.ersion";
    private static final String PREFS_FILENAME = "p.refs";
    public static final String ICON_NAME = "/images/icon.jpg";
    public static final File HOME_DIR = new File(System.getProperty("user.home"), APP_DIRNAME);
    public static final File DATA_FILE = new File(HOME_DIR, DATA_FILENAME);
    public static final File COLOR_SCHEMES_FILE = new File(HOME_DIR, SCHEMES_FILENAME);
    public static final File PREFS_FILE = new File(HOME_DIR, PREFS_FILENAME);
    private final NotesManager MANAGER = NotesManager.getInstance();
    private Set<String> summaryFilters = new HashSet<>();
    private PreferenceListener appListener = settingsListener();
    private final static Properties properties = new Properties();
    private TrayIcon icon;
    private JFrame prefUI;
    private int separatorCharCount = 17;

    static {
        try {
            if (!HOME_DIR.exists()) {
                HOME_DIR.mkdirs();
            }

            properties.load(Main.class.getClassLoader().getResourceAsStream("project.properties"));
            File versionFile = new File(HOME_DIR, "v.ersion");
            String currentVersion = properties.getProperty("version");
            if (versionFile.exists()) {
                //already installed
                Scanner s = new Scanner(versionFile);
                if (s.hasNext()) {
                    String detectedVersion = s.nextLine();
                    if (!currentVersion.equals(detectedVersion)) {
                        JOptionPane.showMessageDialog(null, "Another version detected, backup d.data file first");
                        System.exit(1);
                    } else {
                        LOG.info("Detected version: " + currentVersion);
                    }
                }
            } else {
                try ( FileWriter w = new FileWriter(versionFile)) {
                    w.write(currentVersion);
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Main() {
        initUI();
        initApp();
    }

    public static ImageIcon createImageIcon(String path,
            String description) {
        java.net.URL imgURL = Main.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public final void initApp() {
        Preference.getInstance().addListener(appListener);
        Preference.getInstance().requestUpdate(appListener);
    }

    public final void initUI() {
        if (SystemTray.isSupported()) {
            try {
                SystemTray tray = SystemTray.getSystemTray();
                PopupMenu popUp = new PopupMenu("test");

                popUp.add(newNote());
                popUp.add(generateReport());
                popUp.addSeparator();
                popUp.add(showNotesList());
                popUp.add(showPreferences());
                popUp.addSeparator();
                popUp.add(exit());

                icon = new TrayIcon(
                        createImageIcon(ICON_NAME, "")
                                .getImage()
                                .getScaledInstance(tray.getTrayIconSize().height,
                                        tray.getTrayIconSize().height,
                                        Image.SCALE_DEFAULT),
                        String.format("PostMeNotes - %s", properties.getProperty("version")),
                        popUp);

                tray.add(icon);
            } catch (AWTException ex) {
                LOG.log(Level.SEVERE, null, ex);
                System.exit(1);
            }
        }
    }

    private MenuItem createItem(String name) {
        return new MenuItem(name);
    }

    private MenuItem newNote() {
        MenuItem newNote = createItem("New");
        newNote.addActionListener(createNote());
        return newNote;
    }

    private MenuItem showNotesList() {
        MenuItem notesList = createItem("List");
        notesList.addActionListener(showList());
        return notesList;
    }

    private MenuItem showPreferences() {
        MenuItem pref = createItem("Preferences");
        pref.addActionListener(showPref());
        return pref;
    }

    private MenuItem generateReport() {
        MenuItem report = createItem("Summarize");
        report.addActionListener(genReport());
        return report;
    }

    private MenuItem exit() {
        MenuItem exit = createItem("Exit");
        exit.addActionListener(a -> System.exit(0));
        return exit;
    }

    private ActionListener createNote() {
        return a -> {
            java.awt.EventQueue.invokeLater(() -> {
                MANAGER.newNoteInstance()
                        .setVisible(true);
            });
        };
    }

    private ActionListener showList() {
        return a -> {
            final JFrame notesList = new NotesList();
            notesList.pack();
            notesList.setLocationRelativeTo(null);
            EventQueue.invokeLater(() -> {
                notesList.setVisible(true);
            });
        };
    }

    public void restoreSavedNotes() {
        MANAGER.getSavedNotes()
                .stream()
                .filter(n -> !n.isHidden())
                .map(PostMeNoteDialog::new)
                .forEach(dialog -> {
                    dialog.setTrayIcon(icon);
                    dialog.addTrayIconNotifier(TrayIcon.MessageType.INFO, s -> icon.displayMessage(dialog.getModel().getTitle(), s, TrayIcon.MessageType.INFO));
                    EventQueue.invokeLater(() -> {
                        dialog.setSizeExternal();
                        dialog.setVisible(true);
                    });
                });
    }

    private ActionListener showPref() {
        return a -> {
            if (prefUI == null) {
                prefUI = new PreferenceUI();
                prefUI.pack();
                prefUI.setLocationRelativeTo(null);
            }
            EventQueue.invokeLater(() -> {
                prefUI.setVisible(true);
            });

        };
    }

    class Switch {

        boolean exp;

        public Switch(boolean exp) {
            this.exp = exp;
        }

        void toggle() {
            exp = !exp;
        }

        boolean is() {
            return exp;
        }
    }

    private ActionListener genReport() {
        char sep = '-';
        String separator = IntStream.range(0, separatorCharCount)
                .mapToObj((i) -> Character.toString(sep))
                .collect(Collectors.joining());

        return a -> {
            final StringBuilder b = new StringBuilder();
            final StringBuilder html = new StringBuilder()
                    .append("<html>");
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
            b.append(sdf.format(c.getTime())).append("\n\n");
            html.append("<h3>").append("Summary Copied in Clipboard").append("</h3><br/>");
            final Switch first = new Switch(true);
            Consumer<Note> gen = note -> {
                NoteUtility nUtil = NoteUtility.getInstance(note);
                String paragraph = nUtil.getLastParagraph();
                String[] lines = paragraph.split("\n");
                if (!first.is()) {
                    b.append(separator).append("\n");
                    html.append("<hr/>");
                } else {
                    first.toggle();
                }
                b.append(note.getTitle()).append("\n");
                html.append("<h4>").append(note.getTitle()).append("</h4>");
                html.append("<ul>");
                for (String line : lines) {
                    b.append("• ").append(line).append("\n");
                    html.append("<li>").append(line).append("</li>");
                }
                html.append("</ul>");
            };

            MANAGER.getSavedNotes()
                    .stream()
                    .filter(n -> summaryFilters.contains(n.getColorScheme().getLabel()))
                    .forEach(gen);
            html.append("</html>");

            String data = b.toString();
            String preview = html.toString();
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(data);
            cb.setContents(selection, null);
            JOptionPane.showMessageDialog(null, preview, "Summary: " + sdf.format(c.getTime()), JOptionPane.INFORMATION_MESSAGE);

        };
    }

    public Thread shutdownHook() {
        return new Thread(() -> {
            LOG.log(Level.INFO, "saving preferences..");
            Preference.getInstance().serialize();
            LOG.log(Level.INFO, "saving data..{0}", MANAGER.getSavedNotes().size());
            NotesManager.serialize();
            LOG.log(Level.INFO, "saving reminders..{0}", ReminderManager.getSavedNotifications());
            ReminderManager.serialize();
        });
    }

    private PreferenceListener settingsListener() {
        return new PreferenceListener() {
            @Override
            public void apply(PreferenceEvent property, Object value) {
                switch (property) {
                    case SUMMARY_FILTER:
                        summaryFilters.clear();
                        summaryFilters.addAll(Arrays.asList(value.toString().split(",")));
                        break;
                    case SEPARATOR_CHAR_COUNT: {
                        separatorCharCount = ((Long) value).intValue();
                        break;
                    }
                    default:
                        break;
                }

            }

            @Override
            public List<PreferenceEvent> subscribedEvents() {
                return Arrays.asList(PreferenceEvent.SUMMARY_FILTER, PreferenceEvent.SEPARATOR_CHAR_COUNT);
            }
        };
    }

    protected void startNotificationService() {

    }

    public static void main(String[] args) throws InterruptedException {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NotesList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        Main app = new Main();
        app.startNotificationService();
        app.restoreSavedNotes();
        Runtime.getRuntime().addShutdownHook(app.shutdownHook());
    }

}
