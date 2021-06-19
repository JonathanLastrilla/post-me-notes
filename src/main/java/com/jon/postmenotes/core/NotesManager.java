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
package com.jon.postmenotes.core;

import com.jon.postmenotes.DefaultNoteInstance;
import com.jon.postmenotes.Main;
import com.jon.postmenotes.PostMeNoteDialog;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jlastril
 */
public class NotesManager {

    static final Logger LOG = Logger.getLogger(NotesManager.class.getName());

    static int tracker = 0;

    private final static List<Note> notes;

    private static final NotesManager instance;

    public static NotesManager getInstance() {
        return instance;
    }

    static {
        notes = new ArrayList<>();
        deSerialize();
        instance = new NotesManager();
    }

    public static void deSerialize() {
        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Main.DATA_FILE))) {
            List<Note> read = (List<Note>) ois.readObject();
            LOG.log(Level.INFO, "loading notes {0}", read.size());
            notes.addAll(read);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e, () -> e.getMessage());            
        }
    }

    public static void serialize() {
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Main.DATA_FILE))) {
            LOG.log(Level.INFO, "saving to {0} {1}", new Object[]{Main.DATA_FILE.getAbsolutePath(), notes.size()});
            oos.writeObject(notes);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e, () -> e.getMessage());
        }
    }

    private NotesManager() {
    }

    public PostMeNoteDialog newNoteInstance() {
        Note nn = new DefaultNoteInstance();
        PostMeNoteDialog dialog = new PostMeNoteDialog(nn);
        nn.setSize(dialog.getWidth(), dialog.getHeight());
        notes.add(nn);
        return dialog;
    }

    public List<Note> getSavedNotes() {
        return notes;
    }

    public void deleteForever(int id) {
        throw new UnsupportedOperationException("not supported delete");
    }

}
