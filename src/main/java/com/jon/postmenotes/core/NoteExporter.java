/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jon.postmenotes.core;

/**
 *
 * @author jlastril
 */
public abstract class NoteExporter {
    private String exporterName;

    public NoteExporter(String exporterName) {
        this.exporterName = exporterName;
    }
    
    public abstract void exportAllNotes(NotesManager manager);
    
    public void exportAllNotes(NotesManager manager, boolean overwrite){
    }

    public String getExporterName() {
        return exporterName;
    }

    public void setExporterName(String exporterName) {
        this.exporterName = exporterName;
    }
}
