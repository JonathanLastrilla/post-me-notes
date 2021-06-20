/*
 * The MIT License
 *
 * Copyright 2021 bogarts.
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
    private boolean ow = false;

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

    @Override
    public void exportAllNotes(NotesManager manager, boolean overwrite) {
        ow = overwrite;
        exportAllNotes(manager);
    }
    
    

    private void saveNoteToFile(Note note) {
        String name = String.format("%s_%s.txt", note.getColorScheme().getLabel(), note.getTitle().split(" ")[0]);
        File destfile = new File(dest, name);
        if (!destfile.exists()) {
            w(destfile, note);
        } else if (ow || JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Overwrite " + name, "File Export", JOptionPane.YES_NO_OPTION)) {
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
