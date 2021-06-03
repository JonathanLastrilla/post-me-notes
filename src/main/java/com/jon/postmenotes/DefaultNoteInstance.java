/*
 * JONATHAN LASTRILLA 
 */
package com.jon.postmenotes;

import com.jon.postmenotes.core.ColorScheme;
import com.jon.postmenotes.core.Note;
import java.awt.Point;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jlastril
 */
public class DefaultNoteInstance implements Note {

    private static int TRACKER;
    private final int id;
    private String data;
    private boolean locked;
    private String issueRecordUrl;
    private String codeReviewUrl;
    private Point locationOnScreen;
    private long lastEdited;
    private int w;
    private int h;
    private boolean hidden;
    private ColorScheme scheme;

    static {
        TRACKER = 0;
    }

    {
        TRACKER++;
        id = TRACKER;
        locked = true;
        issueRecordUrl = "not set";
        codeReviewUrl = "not set";
        hidden = false;
        scheme = ColorScheme.SCHEME1_TEST;
    }

    public DefaultNoteInstance(String data) {
        this.data = data;
    }

    public DefaultNoteInstance() {
        this("");
        
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getText() {
        return data;
    }

    @Override
    public void setText(String newText) {        
        this.data = newText;
        setTimeEdited(Instant.now());
    }

    @Override
    public boolean toggleLock() {
        locked = !locked;
        return locked;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public String getIssueRecord() {
        return issueRecordUrl;
    }

    @Override
    public void setIssueRecord(String url) {
        issueRecordUrl = url;
    }

    @Override
    public String getCodeReviewURL() {
        return codeReviewUrl;
    }

    @Override
    public void setCodeReview(String url) {
        codeReviewUrl = url;
    }

    @Override
    public String toString() {
        return String.format("%s - %s", getTitle(), getTimeLastEdited().toString());
    }

    private String matchFirstLine(String data) {
        if (data == null || data.isBlank()) {
            return "";
        }
        Pattern p = Pattern.compile("^(.*)$");

        Matcher m = p.matcher(data);

        return m.find() ? m.group(1) : "";
    }
    
    private String getFirstLine(String data){
        return data.isBlank() ? "" : data.split("\n")[0];        
    }

    @Override
    public String getTitle() {
        return getFirstLine(data);
    }

    @Override
    public Point getScreenLocation() {
        return locationOnScreen;
    }

    @Override
    public void setScreenLocation(Point p) {
        this.locationOnScreen = p;
    }

    @Override
    public void setTimeEdited(Instant time) {
        this.lastEdited = time.toEpochMilli();
    }

    @Override
    public Instant getTimeLastEdited() {
        return Instant.ofEpochMilli(lastEdited);
    }

    @Override
    public void setSize(int width, int height) {
        w = width;
        h = height;
    }

    @Override
    public int getWidth() {
        return w;
    }

    @Override
    public int getHeight() {
        return h;
    }

    @Override
    public void setHidden(boolean b) {
        hidden = b;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public void setColorScheme(ColorScheme scheme) {
        this.scheme = scheme;
    }

    @Override
    public ColorScheme getColorScheme() {
        return scheme;
    }

}
