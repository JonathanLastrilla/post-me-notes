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

import java.util.Arrays;
import java.util.List;
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

    private NoteUtility(Note model) {
        this.model = model;
    }

    public String getLastParagraph() {
        String raw = model.getText();
        List<String> paragraphs = Arrays.asList(raw.split("[-]+[\r]*\n"))
                .stream()                
                .map(String::strip)
                .collect(Collectors.toList());
        return !paragraphs.isEmpty() ? paragraphs.get(paragraphs.size() - 1) : "";
    }

//    public static void main(String[] args) {
//        String data = "test data\n"
//                + "-----------------\n"
//                + "nugget\n"
//                + "-----------------\n"
//                + "I have here a sample text";
//        DefaultNoteInstance note = new DefaultNoteInstance(data);
//        NoteUtility util = getInstance(note);
//        System.out.println("last:{" + util.getLastParagraph() + "}");
//    }
}
