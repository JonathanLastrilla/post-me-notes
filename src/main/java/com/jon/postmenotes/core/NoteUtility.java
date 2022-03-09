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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author Jonathan Lastrilla
 */
public class NoteUtility {

    public static NoteUtility getInstance(Note note) {
        return new NoteUtility(note);
    }

    private final Note model;
    final Pattern pattern = Pattern.compile("^[-]+$");
    private final Pattern MSUrlPattern = Pattern.compile("^(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\\\+&amp;%\\$#_]*)?$");
    private final Pattern ORAPattern = Pattern.compile("^(ht|f)tp(s?)\\:\\/\\/([\\.\\w\\~-]+\\/)+([\\w+]\\.*)*([\\?])*(\\w+=\\w*&*)*$");
    private final Pattern allGoesAfterHttpsPattern = Pattern.compile("^(ht|f)tp(s?)\\:\\/\\/.*");
    private NoteUtility(Note model) {
        this.model = model;
    }

    public String getLastParagraph() {
        String raw = model.getText();
        List<String> paragraphs = Arrays.asList(raw.split("[-]+[\r]*\n"))
                .stream()
                .map(mapToParagraphLineIgnoreAware())
                .collect(Collectors.toList());
        return !paragraphs.isEmpty() ? paragraphs.get(paragraphs.size() - 1) : "";
    }

    public List<String> getUrls() {
        List<String> list = new ArrayList<>();
        for (String line : model.getText().split("[\r]*\n")) {
            Matcher m = ORAPattern.matcher(line);
            Matcher m2 = MSUrlPattern.matcher(line);
            Matcher m3 = allGoesAfterHttpsPattern.matcher(line);
            if (m.matches() || m2.matches() || m3.matches()) {                
                list.add(line);
            }
        }
        return list;
    }

    private Function<String, String> mapToParagraphLineIgnoreAware() {
        return paragraph -> {
            StringBuilder b = new StringBuilder();
            paragraph = paragraph.strip();
            for (String pline : paragraph.split("\n")) {
                if (!pline.startsWith("//")) {
                    b.append(pline).append("\n");
                }
            }
            return b.toString();
        };
    }

//    public static void main(String[] args) {
//        String data = "test data\n"
//                + "-----------------\n"
//                + ""
//                + "-----------------\n"
//                + "I have here a sample text";
//        com.jon.postmenotes.DefaultNoteInstance note = new com.jon.postmenotes.DefaultNoteInstance(data);
//        NoteUtility util = getInstance(note);
//        javax.swing.JOptionPane.showMessageDialog(null, "<html><p>"+util.getLastParagraph()+"</p><br/>test</html>");
//        util.getUrls();
//    }
}
