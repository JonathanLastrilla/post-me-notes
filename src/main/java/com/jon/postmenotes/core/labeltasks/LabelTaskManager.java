/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jon.postmenotes.core.labeltasks;

import com.jon.postmenotes.PostMeNoteDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.JComboBox;

/**
 *
 * @author jlastril
 */
public class LabelTaskManager implements ActionListener {

    private static final Logger LOG = Logger.getLogger(LabelTaskManager.class.getSimpleName());

    public static ActionListener getInstance(PostMeNoteDialog dialog) {
        return new LabelTaskManager(dialog);
    }

    private PostMeNoteDialog dialog;

    public LabelTaskManager(PostMeNoteDialog dialog) {
        this.dialog = dialog;
    }   

    private void runTasks(String label) {
        for (final Method dialogMethod : dialog.getClass().getDeclaredMethods()) {
            LabelTask labelTask = dialogMethod.getAnnotation(LabelTask.class);
            if (labelTask != null && label.equals(labelTask.label())) {
                try {
                    dialogMethod.invoke(dialog, (Object[]) null);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        JComboBox<String> cb = (JComboBox<String>) arg0.getSource();
        String selected = cb.getSelectedItem().toString();
        runTasks(selected);
    }

}
