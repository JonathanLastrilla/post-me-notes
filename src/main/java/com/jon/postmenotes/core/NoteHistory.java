package com.jon.postmenotes.core;


import java.io.Serializable;
import java.time.Instant;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jlastril
 */
public class NoteHistory implements Serializable{
    private static final long serialVersionUID = 3223525L;
    private int row;
    private String data;
    private Instant updated;

    {
        updated = Instant.now();
    }

    public NoteHistory(int row, String data) {
        this.data = data;
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

}
