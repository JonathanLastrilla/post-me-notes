/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jon.postmenotes.v2;

import com.jon.postmenotes.core.NoteHistory;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 *
 * @author jlastril
 */
public class DefaultNoteInstance extends com.jon.postmenotes.DefaultNoteInstance {

    List<NoteHistory> historyList;

    public DefaultNoteInstance(String data) {
        super(data);
        buildList();
    }

    private final void buildList() {
        String data = getText();
        Scanner s = new Scanner(data);
        while (s.hasNextLine()) {
            String line = s.nextLine().trim();
            char init = line.charAt(0);
            int size = line.length();
            Predicate<Character> isSep = (c) -> c == 'c';
            int ctr = 0;
            for (char c : line.toCharArray()) {
                ctr += isSep.test(c) ? 1 : 0;
            }
            boolean isDataNext = isSep.test(init) && ctr == size;
            if(isDataNext){
                
            }

        }
    }
}
