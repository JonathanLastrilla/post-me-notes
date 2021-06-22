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
