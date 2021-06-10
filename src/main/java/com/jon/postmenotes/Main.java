/*
 * 
 * 
 * 
 */
package com.jon.postmenotes;

import com.jon.postmenotes.core.NotesManager;
import com.jon.postmenotes.core.Note;
import com.jon.postmenotes.core.Preference;
import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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

    private final static Properties properties = new Properties();

    Note dummy;

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

                TrayIcon icon = new TrayIcon(
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
                    EventQueue.invokeLater(() -> {
                        dialog.setSizeExternal();
                        dialog.setVisible(true);
                    });
                });
    }

    private ActionListener showPref() {
        return a -> {
            final JFrame prefUI = new PreferenceUI();
            prefUI.pack();
            prefUI.setLocationRelativeTo(null);
            EventQueue.invokeLater(() -> {
                prefUI.setVisible(true);
            });
        };
    }

    private ActionListener genReport() {
        return a -> {
            final StringBuilder b = new StringBuilder();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
            b.append(sdf.format(c.getTime())).append("\n");

            final Pattern pattern = Pattern.compile("^-+");

            Consumer<Note> gen = note -> {
                Stack<String> stack = new Stack<>();
                for (String string : note.getText().split("\n")) {
                    stack.push(string);
                }
                b.append("-------------------")
                        .append("\n")
                        .append(note.getTitle())
                        .append("\n");
                final StringBuilder sub = new StringBuilder();
                while (!stack.isEmpty()) {
                    String top = stack.pop();
                    boolean terminate = pattern.matcher(top).find();
                    if (!terminate) {
                        sub.insert(0, top + "\n");
                    } else {
                        break;
                    }
                }
                b.append(" • ").append(sub.toString())
                        .append("\n");

            };

            MANAGER.getSavedNotes()
                    .stream()
                    .filter(n -> {
                        return n.getColorScheme().getLabel().equals("DEV_WIP") || n.getColorScheme().getLabel().equals("IN_REVIEW");
                    })
                    .forEach(gen);
//            System.out.println(b.toString());
            String data = b.toString();
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(data);
            cb.setContents(selection, null);
            JOptionPane.showMessageDialog(null, data, "Report Copied in Clipboard", JOptionPane.INFORMATION_MESSAGE);

        };
    }

    public Thread shutdownHook() {
        return new Thread(() -> {
            Preference.getInstance().serialize();
            LOG.log(Level.INFO, "saving preferences..");
            NotesManager.serialize();
            LOG.log(Level.INFO, "saving data..{0}", MANAGER.getSavedNotes().size());
        });
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
        app.restoreSavedNotes();
        Runtime.getRuntime().addShutdownHook(app.shutdownHook());
    }

}
