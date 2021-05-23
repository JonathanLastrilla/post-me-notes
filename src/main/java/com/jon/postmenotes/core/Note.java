/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jon.postmenotes.core;

import java.awt.Point;
import java.io.Serializable;
import java.time.Instant;

/**
 *
 * @author jlastril
 */
public interface Note extends Serializable {

    public static long serialVersionUID = 23292l;

    int getId();

    String getText();

    void setText(String newText);

    boolean toggleLock();

    boolean isLocked();

    String getIssueRecord();

    String getCodeReviewURL();

    void setIssueRecord(String url);

    void setCodeReview(String url);

    String getTitle();

    Point getScreenLocation();

    void setScreenLocation(Point p);

    void setTimeEdited(Instant time);

    Instant getTimeLastEdited();

    void setSize(int width, int height);

    public int getWidth();

    public int getHeight();

    void setHidden(boolean b);

    boolean isHidden();

    void setColorScheme(ColorScheme scheme);

    ColorScheme getColorScheme();
}
