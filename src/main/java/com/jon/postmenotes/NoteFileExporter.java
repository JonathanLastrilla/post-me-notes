/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jon.postmenotes;

import com.jon.postmenotes.core.Note;
import com.jon.postmenotes.core.NoteExporter;
import com.jon.postmenotes.core.NotesManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author jlastril
 */
public class NoteFileExporter extends NoteExporter {

    private final static Logger LOG = Logger.getLogger(NoteFileExporter.class.getName());
    private File dest = new File(Main.HOME_DIR, "export");

    public NoteFileExporter() {
        super("File Export");
        if (!dest.exists()) {
            dest.mkdirs();
        }
    }

    @Override
    public void exportAllNotes(NotesManager manager) {
        manager.getSavedNotes()
                .forEach(note -> saveNoteToFile(note));

    }

    private void saveNoteToFile(Note note) {
        String name = String.format("%s_%s.txt", note.getColorScheme().getLabel(), note.getTitle().split(" ")[0]);
        File destfile = new File(dest, name);
        if (!destfile.exists()) {
            w(destfile, note);
        } else if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Overwrite " + name, "File Export", JOptionPane.YES_NO_OPTION)) {
            w(destfile, note);
        }

    }

    private void w(File destfile, Note note) {
        try ( FileWriter w = new FileWriter(destfile)) {
            LOG.log(Level.INFO, () -> String.format("saved %s ", destfile.getName()));
            w.write(note.getText());
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

}
