/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        if (Main.schemeFile.exists()) {
            try {
                Scanner s = new Scanner(Main.schemeFile);
                while (s.hasNext()) {
                    
                    String[] line = s.nextLine().split(",");
                    String name = line[0];
                    String fg = line[1];
                    String bg = line[2];                    
                    SCHEMES.add(new ColorScheme(name, hex2Rgb(bg), hex2Rgb(fg)));
                }
                System.out.println("loaded "+SCHEMES.size());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ColorScheme.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
