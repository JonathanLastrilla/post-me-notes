/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jon.postmenotes;

import com.jon.postmenotes.core.ColorScheme;
import com.jon.postmenotes.core.NoteExporter;
import com.jon.postmenotes.core.NotesManager;
import com.jon.postmenotes.core.Preference;
import com.jon.postmenotes.core.PreferenceEvent;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author jlastril
 */
public class PreferenceUI extends javax.swing.JFrame {

    static class ListItem {

        Font w;

        public ListItem(Font w) {
            this.w = w;
        }

        Font getFont() {
            return w;
        }

        @Override
        public String toString() {
            return w.getName();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ListItem) {
                return ((ListItem) obj).w.getFontName().equals(w.getFontName());
            } else {
                return super.equals(obj);
            }
        }

        @Override
        public int hashCode() {
            return w.hashCode();
        }

    }

    /**
     * Creates new form Preferences
     */
    public PreferenceUI() {
        initComponents();
        setIconImage(Main.createImageIcon(Main.ICON_NAME, "").getImage());
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        DefaultComboBoxModel model = new DefaultComboBoxModel(Stream.of(ge.getAllFonts())
                .map(ListItem::new)
                .toArray());

        int fIdx = -1;
        for (int i = 0; i < model.getSize(); i++) {
            ListItem li = (ListItem) model.getElementAt(i);
            if (li.getFont().equals(((Font) pref.get(PreferenceEvent.FONT)))) {
                model.setSelectedItem(li);
                break;
            }

        }
        fontListJCB.setModel(model);
        fontSizeJFTF.setText("" + pref.get(PreferenceEvent.FONT_SIZE));

        DefaultComboBoxModel<String> exporters = new DefaultComboBoxModel<>(
                new String[]{this.exporters[0].getExporterName()}
        );
        exportersJCB.setModel(exporters);
        String[] labels = ColorScheme.SCHEMES.stream()
                .map(ColorScheme::getLabel)
                .toArray(String[]::new);
        DefaultComboBoxModel<String> labelsModel = new DefaultComboBoxModel<>(labels);
        labelJL.setModel(labelsModel);

        String summaryFilters = (String) pref.get(PreferenceEvent.SUMMARY_FILTER);
        if (summaryFilters != null) {
            for (String f : summaryFilters.split(",")) {
                summaryModel.addElement(f);
            }
        }

        summaryModel.addListDataListener(new ListDataListener() {

            Supplier<String> a = () -> {
                return IntStream.range(0, summaryModel.getSize())
                        .mapToObj(summaryModel::get)
                        .collect(Collectors.joining(","));
            };

            @Override
            public void intervalAdded(ListDataEvent arg0) {
                System.out.println("add "+a.get());
                preferencePropertyPublisher.publish(PreferenceEvent.SUMMARY_FILTER, a.get());
            }

            @Override
            public void intervalRemoved(ListDataEvent arg0) {
                System.out.println("rem "+a.get());
                preferencePropertyPublisher.publish(PreferenceEvent.SUMMARY_FILTER, a.get());
            }

            @Override
            public void contentsChanged(ListDataEvent arg0) {
                
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        fontListJCB = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        fontSizeJFTF = new javax.swing.JFormattedTextField();
        exportJB = new javax.swing.JButton();
        exportersJCB = new javax.swing.JComboBox<>();
        statusJL = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        summaryFilterJL = new javax.swing.JList<>();
        summaryFilterJL.setModel(summaryModel);
        jLabel4 = new javax.swing.JLabel();
        labelJL = new javax.swing.JComboBox<>();
        addFilterJB = new javax.swing.JButton();
        removeFilterJB = new javax.swing.JButton();
        overwriteAllJCB = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Font");

        fontListJCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        fontListJCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fontListJCBActionPerformed(evt);
            }
        });

        jLabel2.setText("Size");

        fontSizeJFTF.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        fontSizeJFTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                fontSizeJFTFPropertyChange(evt);
            }
        });

        exportJB.setText("Export");
        exportJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportJBActionPerformed(evt);
            }
        });

        exportersJCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        statusJL.setText("jLabel3");

        summaryFilterJL.setToolTipText("Labels of notes to be included in report");
        jScrollPane1.setViewportView(summaryFilterJL);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Summary");

        addFilterJB.setText("add");
        addFilterJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFilterJBActionPerformed(evt);
            }
        });

        removeFilterJB.setText("remove selected");
        removeFilterJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeFilterJBActionPerformed(evt);
            }
        });

        overwriteAllJCB.setText("overwrite all");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statusJL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addFilterJB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelJL, 0, 162, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fontListJCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fontSizeJFTF, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(exportJB)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(exportersJCB, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(overwriteAllJCB))
                            .addComponent(jLabel4)
                            .addComponent(removeFilterJB))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fontListJCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fontSizeJFTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(removeFilterJB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(labelJL, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addFilterJB)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exportJB)
                    .addComponent(exportersJCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(overwriteAllJCB))
                .addGap(18, 18, 18)
                .addComponent(statusJL)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fontListJCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fontListJCBActionPerformed
        preferencePropertyPublisher.publish(PreferenceEvent.FONT, ((ListItem) fontListJCB.getModel().getSelectedItem()).getFont());
    }//GEN-LAST:event_fontListJCBActionPerformed

    private void fontSizeJFTFPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_fontSizeJFTFPropertyChange
        if ("value".equals(evt.getPropertyName())) {
            try {
                preferencePropertyPublisher.publish(PreferenceEvent.FONT_SIZE, Integer.parseInt(fontSizeJFTF.getText()));
            } catch (NumberFormatException e) {

            }
        }
    }//GEN-LAST:event_fontSizeJFTFPropertyChange

    private void exportJBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportJBActionPerformed
        NoteExporter selectedExporter = null;
        for (NoteExporter exporter : exporters) {
            if (exporter.getExporterName().equals(exportersJCB.getSelectedItem().toString())) {
                selectedExporter = exporter;
            }
        }
        if (selectedExporter != null) {
            selectedExporter.exportAllNotes(NotesManager.getInstance(), overwriteAllJCB.isSelected());
            publish("all notes exported");
        }
    }//GEN-LAST:event_exportJBActionPerformed

    private void addFilterJBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFilterJBActionPerformed
        String selected = labelJL.getSelectedItem().toString();
        boolean contains = summaryModel.contains(selected);
        if (!contains) {
            summaryModel.addElement(selected);
        }
    }//GEN-LAST:event_addFilterJBActionPerformed

    private void removeFilterJBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeFilterJBActionPerformed
       int tr = summaryFilterJL.getSelectedIndex();
       summaryModel.removeElementAt(tr);
    }//GEN-LAST:event_removeFilterJBActionPerformed

    private void publish(String message) {
        new Thread(() -> {
            try {
                statusJL.setText(message);
                Thread.sleep(3000);
                statusJL.setText("");
            } catch (InterruptedException ex) {

            }
        }).start();
    }

    Preference pref = Preference.getInstance();
    Preference.Publisher preferencePropertyPublisher = new Preference.Publisher(pref);
    private NoteExporter[] exporters = new NoteExporter[]{
        new NoteFileExporter()
    };
    private DefaultListModel<String> summaryModel = new DefaultListModel<>();
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addFilterJB;
    private javax.swing.JButton exportJB;
    private javax.swing.JComboBox<String> exportersJCB;
    private javax.swing.JComboBox<String> fontListJCB;
    private javax.swing.JFormattedTextField fontSizeJFTF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> labelJL;
    private javax.swing.JCheckBox overwriteAllJCB;
    private javax.swing.JButton removeFilterJB;
    private javax.swing.JLabel statusJL;
    private javax.swing.JList<String> summaryFilterJL;
    // End of variables declaration//GEN-END:variables
}
