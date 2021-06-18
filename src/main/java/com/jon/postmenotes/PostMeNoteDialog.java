/*
 * 
 * 
 * 
 */
package com.jon.postmenotes;

import com.jon.postmenotes.core.ColorScheme;
import com.jon.postmenotes.core.NotesManager;
import com.jon.postmenotes.core.Note;
import com.jon.postmenotes.core.Preference;
import com.jon.postmenotes.core.PreferenceEvent;
import com.jon.postmenotes.core.PreferenceListener;
import com.jon.postmenotes.core.ReminderManager;
import com.jon.postmenotes.core.labeltasks.LabelTask;
import com.jon.postmenotes.core.labeltasks.LabelTaskManager;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.BadLocationException;

/**
 *
 * @author jlastril
 */
public class PostMeNoteDialog extends javax.swing.JDialog {

    

    /**
     * Creates new form PostMeNoteDialog
     *
     * @param model
     */
    public PostMeNoteDialog(Note model) {
        super();
        initComponents();
        this.model = model;
        initComponents2();
        preference.addListener(prefListener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        editorContext = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane(){
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };
        statusJL = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        newNoteJB = new javax.swing.JButton();
        deleteJB = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        lockedJCB = new javax.swing.JCheckBox();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        addSeparatorJB = new javax.swing.JButton();
        tsJCB = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        addReminder = new javax.swing.JButton();
        colorListJCB = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setOpaque(false);

        jEditorPane1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jEditorPane1.setComponentPopupMenu(editorContext);
        jEditorPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jEditorPane1MouseReleased(evt);
            }
        });
        jEditorPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jEditorPane1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jEditorPane1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jEditorPane1KeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jEditorPane1);

        statusJL.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        statusJL.setText(" ");

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

        newNoteJB.setText("+");
        newNoteJB.setToolTipText("new");
        newNoteJB.setFocusable(false);
        newNoteJB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        newNoteJB.setOpaque(false);
        newNoteJB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        newNoteJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newNoteJBActionPerformed(evt);
            }
        });
        jToolBar1.add(newNoteJB);

        deleteJB.setText(" - ");
        deleteJB.setToolTipText("delete permanently");
        deleteJB.setFocusable(false);
        deleteJB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteJB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteJBActionPerformed(evt);
            }
        });
        jToolBar1.add(deleteJB);
        jToolBar1.add(jSeparator1);

        lockedJCB.setText("edit");
        lockedJCB.setToolTipText("edit, unchecking enables 'copy on select'");
        lockedJCB.setFocusable(false);
        lockedJCB.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lockedJCB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lockedJCB.setOpaque(false);
        lockedJCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lockedJCBActionPerformed(evt);
            }
        });
        jToolBar1.add(lockedJCB);
        jToolBar1.add(jSeparator3);

        addSeparatorJB.setText("(---)");
        addSeparatorJB.setToolTipText("add separator");
        addSeparatorJB.setFocusable(false);
        addSeparatorJB.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addSeparatorJB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addSeparatorJB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSeparatorJBActionPerformed(evt);
            }
        });
        jToolBar1.add(addSeparatorJB);

        tsJCB.setText("timestamp");
        tsJCB.setToolTipText("add timestamp when inserting separator");
        tsJCB.setFocusable(false);
        tsJCB.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        tsJCB.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tsJCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tsJCBActionPerformed(evt);
            }
        });
        jToolBar1.add(tsJCB);
        jToolBar1.add(jSeparator2);

        addReminder.setText("add reminder");
        addReminder.setFocusable(false);
        addReminder.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addReminder.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addReminder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addReminderActionPerformed(evt);
            }
        });
        jToolBar1.add(addReminder);

        colorListJCB.setToolTipText("sticky color");
        colorListJCB.setOpaque(false);
        colorListJCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorListJCBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                    .addComponent(statusJL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(colorListJCB, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(colorListJCB, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusJL, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void initComponents2() {
        setIconImage(Main.createImageIcon(Main.ICON_NAME, "").getImage());
        if (model.getScreenLocation() != null) {
            setLocation(model.getScreenLocation());
        }
        jEditorPane1.setEditable(model.isLocked());
        jEditorPane1.setText(model.getText());
        jEditorPane1.getDocument().addDocumentListener(editorListener());
        lockedJCB.setSelected(model.isLocked());

        DefaultComboBoxModel colorList = new DefaultComboBoxModel(
                ColorScheme.SCHEMES.stream().toArray()
        );
        colorListJCB.setModel(colorList);

        if (this.model.getColorScheme() != null) {
            updateColr(model.getColorScheme());
            colorList.setSelectedItem(model.getColorScheme());
        }
        colorListJCB.setRenderer(schemesRenderer());       
        setTitle(String.format(TITLE_TEMPLATE, model.getColorScheme().getLabel(), model.getTitle()));
        addSeparatorJB.setEnabled(model.isLocked());
        tsJCB.setEnabled(model.isLocked());
        colorListJCB.addActionListener(LabelTaskManager.getInstance(this));
    }

    private void jEditorPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jEditorPane1KeyPressed
        if (!ctrl && evt.isControlDown()) {
            ctrl = true;
        }
    }//GEN-LAST:event_jEditorPane1KeyPressed

    private void jEditorPane1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jEditorPane1KeyTyped

    }//GEN-LAST:event_jEditorPane1KeyTyped

    private void jEditorPane1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jEditorPane1KeyReleased
        if (ctrl) {

            if (KeyEvent.VK_B == evt.getKeyCode()) {

            }
            ctrl = false;
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jEditorPane1KeyReleased

    private void newNoteJBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newNoteJBActionPerformed
        EventQueue.invokeLater(() -> {
            NotesManager.getInstance()
                    .newNoteInstance()
                    .setVisible(true);
        });
    }//GEN-LAST:event_newNoteJBActionPerformed

    private void lockedJCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lockedJCBActionPerformed
        boolean b = model.toggleLock();
        jEditorPane1.setEditable(b);
        publish(b ? "write enabled" : "read only");
        deleteJB.setEnabled(!b);
        addSeparatorJB.setEnabled(b);
        tsJCB.setEnabled(b);
    }//GEN-LAST:event_lockedJCBActionPerformed

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
        Component comp = (Component) evt.getSource();
        try {
            if (this.isVisible()) {
                this.model.setScreenLocation(comp.getLocationOnScreen());
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e, () -> e.getMessage());
        }
    }//GEN-LAST:event_formComponentMoved

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        if (isVisible()) {
            model.setSize(getWidth(), getHeight());
        }
    }//GEN-LAST:event_formComponentResized

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        model.setHidden(true);
    }//GEN-LAST:event_formWindowClosing

    private void colorListJCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorListJCBActionPerformed
        Optional<ColorScheme> selected = ColorScheme.SCHEMES.stream()
                .filter(s -> s.getLabel().equals(colorListJCB.getSelectedItem().toString()))
                .findFirst();

        if (selected.isPresent()) {
            updateColr(selected.get());
        }
        setTitle(String.format(TITLE_TEMPLATE, model.getColorScheme().getLabel(), model.getTitle()));
    }//GEN-LAST:event_colorListJCBActionPerformed

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        posX = evt.getX();
        posY = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void jEditorPane1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jEditorPane1MouseReleased
        String selectedString = jEditorPane1.getSelectedText();
        if (!jEditorPane1.isEditable() && selectedString != null && !selectedString.isBlank()) {
            StringSelection selection = new StringSelection(selectedString);
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            cb.setContents(selection, null);
            publish("text copied");
        }
    }//GEN-LAST:event_jEditorPane1MouseReleased

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }//GEN-LAST:event_formWindowGainedFocus

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        jScrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);        
    }//GEN-LAST:event_formWindowLostFocus

    private void deleteJBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteJBActionPerformed
        int sel = JOptionPane.showConfirmDialog(this, "This will delete your note permanently", "Delete Note", JOptionPane.YES_NO_OPTION);
        flaggedForDeletion = sel == JOptionPane.YES_OPTION;
        if (flaggedForDeletion) {
            this.dispose();
        }
    }//GEN-LAST:event_deleteJBActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if (flaggedForDeletion) {
            boolean removed = NotesManager.getInstance().getSavedNotes().remove(model);
            if (removed) {
                JOptionPane.showMessageDialog(null, String.format("Note '%s' deleted.", model.getTitle()));
            }
        }
    }//GEN-LAST:event_formWindowClosed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        SwingUtilities.invokeLater(() -> {           
            preference.requestUpdate(prefListener);
            JScrollBar scb = jScrollPane2.getVerticalScrollBar();
            scb.setValue(scb.getMaximum());
        });
    }//GEN-LAST:event_formWindowOpened

    private void addSeparatorJBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSeparatorJBActionPerformed
        try {            
            jEditorPane1.getDocument().insertString(jEditorPane1.getDocument().getLength(),
                    "\n-------------------\n", null);
            if(tsJCB.isSelected()){
                jEditorPane1.getDocument().insertString(jEditorPane1.getDocument().getLength(),
                    sdf.format(new Date())+"\n", null);
            }
            jEditorPane1.setCaretPosition(jEditorPane1.getDocument().getLength());
        } catch (BadLocationException ex) {
            Logger.getLogger(PostMeNoteDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addSeparatorJBActionPerformed

    private void tsJCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tsJCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tsJCBActionPerformed

    private void addReminderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addReminderActionPerformed
        String message = JOptionPane.showInputDialog(this, "Format:<hh:mm> <minutes_before> <message>"); 
        Matcher m = p.matcher(message);
        if(m.find()){
            String time = m.group(1);
            int notifyBefore = -Integer.parseInt(m.group(2));
            LocalDateTime now = LocalDateTime.now();
            String inString = now.toString();
            String newDateString = String.format("%sT%s", inString.split("T")[0], time);
            LocalDateTime when = LocalDateTime.parse(newDateString);
            System.out.println(when);
            reminderManager.initializeContext(model)
                .newReminderInContext(message, ChronoUnit.MINUTES.addTo(when, notifyBefore))
                .scheduleReminderInContextNow(icon);
        }else{
            LOG.warning("invalid format!");
        }        
    }//GEN-LAST:event_addReminderActionPerformed

    
    
    

    boolean isUpdating = false;
    private int posX;
    private int posY;
    private final Note model;
    private final PreferenceListener prefListener = listener();
    private String TITLE_TEMPLATE = "%s - %s";
    private boolean flaggedForDeletion = false;
    private final Logger LOG = Logger.getLogger(PostMeNoteDialog.class.getName());
    private Preference preference = Preference.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy hh:mm:ss");
    private Consumer<String> systemTrayNotify;
    private Map<TrayIcon.MessageType, Consumer<String>> notifiers = new HashMap<>();
    private ReminderManager reminderManager = ReminderManager.getInstance();
    private TrayIcon icon;
    private final String reminderPattern = "^([0-9]{2}:[0-9]{2}) ([0-9]{0,2}).*";
    Pattern p = Pattern.compile(reminderPattern);    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addReminder;
    private javax.swing.JButton addSeparatorJB;
    private javax.swing.JComboBox<String> colorListJCB;
    private javax.swing.JButton deleteJB;
    private javax.swing.JPopupMenu editorContext;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JCheckBox lockedJCB;
    private javax.swing.JButton newNoteJB;
    private javax.swing.JLabel statusJL;
    private javax.swing.JCheckBox tsJCB;
    // End of variables declaration//GEN-END:variables

    boolean ctrl;

    private DocumentListener editorListener() {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent arg0) {
                manageUpdate();
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {
                manageUpdate();

            }

            @Override
            public void changedUpdate(DocumentEvent arg0) {
                manageUpdate();
            }
        };
    }

    private void manageUpdate() {
        if (!isUpdating) {
            isUpdating = true;
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    model.setText(jEditorPane1.getText());
                    isUpdating = false;
                    new Thread(() -> {                        
                        statusJL.setText("saved.");
                        try {
                            Thread.sleep(2000);
                            statusJL.setText("");
                            setTitle(String.format(TITLE_TEMPLATE, model.getColorScheme().getLabel(), model.getTitle()));
                        } catch (InterruptedException ex) {
                        }
                    }).start();
                } catch (Exception e) {
                }
            }).start();
        }
    }

    public void setSizeExternal() {
        this.setSize(model.getWidth(), model.getHeight());
    }

    private void updateColr(ColorScheme scheme) {
        Consumer<JComponent> fgSetter = f -> f.setForeground(scheme.getFg());
        Consumer<JComponent> bgSetter = f -> f.setBackground(scheme.getBg());

        Arrays.asList(
                newNoteJB,
                jPanel1,
                jEditorPane1,
                jScrollPane2,
                colorListJCB,
                deleteJB,
                statusJL,
                addSeparatorJB,
                tsJCB,
                lockedJCB,
                addReminder
                )
                .stream()
                .forEach(fgSetter);

        Arrays.asList(
                newNoteJB,
                jPanel1,
                jEditorPane1,
                jScrollPane2,
                colorListJCB,
                deleteJB,
                statusJL,
                addSeparatorJB,
                tsJCB,
                lockedJCB,
                addReminder
                )
                .stream()
                .forEach(bgSetter);
        UIManager.put("TextField.caretForeground", new ColorUIResource(scheme.getFg()));
        model.setColorScheme(scheme);
    }

    private DefaultListCellRenderer schemesRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JComponent result = (JComponent) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                ColorScheme selected = (ColorScheme) value;
                result.setBackground(selected.getBg());
                result.setForeground(selected.getFg());
                return result;
            }
        };
    }

    private void publish(String message) {
        new Thread(() -> {
            try {
                statusJL.setText(message);
                Thread.sleep(3000);
                statusJL.setText("");
            } catch (InterruptedException ex) {
                Logger.getLogger(PostMeNoteDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }    
    
    private PreferenceListener listener(){
        return new PreferenceListener() {
            @Override
            public void apply(PreferenceEvent property, Object value) {
                switch(property){
                    case FONT:{
                        Font f = (Font) value;                        
                        jEditorPane1.setFont(new Font(f.getName(), 0, 16));
                        break;
                    }
                    case FONT_SIZE:{                        
                        Font f = jEditorPane1.getFont();
                        jEditorPane1.setFont(new Font(f.getName(), 0, (Integer)value));
                        break;
                    }
                    default :{
                        LOG.log(Level.FINE,property.name());
                    }
                }
            }

            @Override
            public List<PreferenceEvent> subscribedEvents() {
                return Arrays.asList(PreferenceEvent.FONT, PreferenceEvent.FONT_SIZE);
            }
        };
    }
    
    @LabelTask(label = "NOTE")
    public void disableEdit(){
        if(lockedJCB.isSelected()){
            lockedJCB.doClick();
        }
    }
    
    public Note getModel() {
        return model;
    }
    public void addTrayIconNotifier(TrayIcon.MessageType type, Consumer<String> consumer){
        notifiers.put(type, consumer);
    }
    
    private void notifyDefault(String message){
        if(notifiers.containsKey(TrayIcon.MessageType.INFO)){
            notifiers.get(TrayIcon.MessageType.INFO).accept(message);
        }else {
            LOG.severe("missing system tray notifier for INFO");
        }        
    }
    
    public void setTrayIcon(TrayIcon icon){
        this.icon = icon;
    }
    
    private void initMyReminders(){
        
    }
    
}
