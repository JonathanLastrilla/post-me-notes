/*
 * 
 * 
 * 
 */
package com.jon.postmenotes;

import com.jon.postmenotes.core.NotesManager;
import com.jon.postmenotes.core.Note;
import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author jlastril
 */
public class Main {

    static final Logger LOG = Logger.getLogger(Main.class.getName());
    public static final String homeName = ".postMeNotes";
    private static final String dataFileName = "d.data";
    private static final String schemesFileName = "s.chemes";
    private static final String versionDirectory = "v.ersion";
    public static final String iconName = "/images/icon.jpg";
    public static final File homeDir = new File(System.getProperty("user.home"), homeName);
    public static final File dataFile = new File(homeDir, dataFileName);
    public static final File schemeFile = new File(homeDir, schemesFileName);
    private NotesManager manager = NotesManager.getInstance();

    Note dummy;

    static {
        try {
            if (!homeDir.exists()) {
                homeDir.mkdirs();
            }
            final Properties properties = new Properties();
            properties.load(Main.class.getClassLoader().getResourceAsStream("project.properties"));
            File versionFile = new File(homeDir, "v.ersion");
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
                        System.out.println("Detected version: " + currentVersion);
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
                popUp.add(showNotesList());
                popUp.add(exit());

                TrayIcon icon = new TrayIcon(
                        createImageIcon(iconName, "")
                                .getImage()
                                .getScaledInstance(tray.getTrayIconSize().height,
                                        tray.getTrayIconSize().height,
                                        Image.SCALE_DEFAULT),
                        "MeNotes",
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

    private MenuItem exit() {
        MenuItem exit = createItem("Exit");
        exit.addActionListener(a -> System.exit(0));
        return exit;
    }

    private ActionListener createNote() {
        return a -> {
            JDialog dialog = manager.newNoteInstance();
            java.awt.EventQueue.invokeLater(() -> {
                dialog.setVisible(true);
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
        manager.getSavedNotes()
                .stream()
                //                .filter(n -> !n.isHidden())
                .map(PostMeNoteDialog::new)
                .forEach(dialog -> {
                    EventQueue.invokeLater(() -> {
                        dialog.setSizeExternal();
                        dialog.setVisible(true);
                    });
                });
        ;

    }

    public Thread shutdownHook() {
        return new Thread(() -> {
            NotesManager.serialize();
            System.out.println("saving data.." + manager.getSavedNotes().size());
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
