/*
 * 
 * 
 * 
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
        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Main.dataFile))) {
            List<Note> read = (List<Note>) ois.readObject();
            LOG.info("loading notes " + read.size());
            notes.addAll(read);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e, () -> e.getMessage());
            System.exit(2);
        }
    }

    public static void serialize() {
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Main.dataFile))) {
            LOG.info("saving to " + Main.dataFile.getAbsolutePath() + " " + notes.size());
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
