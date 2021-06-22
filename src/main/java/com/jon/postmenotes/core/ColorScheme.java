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

import com.jon.postmenotes.Main;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jlastril
 */
public final class ColorScheme implements Serializable {
    private static final Logger LOG = Logger.getLogger(ColorScheme.class.getName());
    public static long serialVersionUID = 3254532535L;
    private Color bg;
    private Color fg;
    private String label;

    public static ColorScheme SCHEME1_TEST = new ColorScheme(hex2Rgb("#43291F"), hex2Rgb("#FFFFFF"));
    public static final List<ColorScheme> SCHEMES = new ArrayList<>();

    private static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }

    static {
        if (Main.COLOR_SCHEMES_FILE.exists()) {
            try {
                Scanner s = new Scanner(Main.COLOR_SCHEMES_FILE);
                while (s.hasNext()) {
                    
                    String[] line = s.nextLine().split(",");
                    String name = line[0];
                    String fg = line[1];
                    String bg = line[2];                    
                    SCHEMES.add(new ColorScheme(name, hex2Rgb(bg), hex2Rgb(fg)));
                }
                LOG.info("loaded color schemes "+SCHEMES.size());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ColorScheme.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        SCHEME1_TEST.setLabel("default");
    }

    private ColorScheme(Color bg, Color fg) {
        this.bg = bg;
        this.fg = fg;
    }

    private ColorScheme(String label, Color bg, Color fg) {
        this(bg, fg);
        this.label = label;
    }

    public Color getFg() {
        return fg;
    }

    public void setFg(Color fg) {
        this.fg = fg;
    }

    public Color getBg() {
        return bg;
    }

    public void setBg(Color bg) {
        this.bg = bg;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

}
