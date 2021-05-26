/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jon.postmenotes.core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jlastril
 */
public class Preference {

    private final List<PreferenceListener> listeners = new ArrayList<>();

    private static final Preference INSTANCE;

    public static Preference getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE = new Preference();
    }

    public void addListener(PreferenceListener l) {
        listeners.add(l);
    }

    protected void pushEvent(PreferenceEvent id, Object newValue) {
        listeners.stream()
                .filter(l -> l.subscribedEvents().contains(id))
                .forEach(lis -> lis.apply(id, newValue));
    }

    public static class Publisher {

        protected Preference pref;

        public Publisher(Preference pref) {
            this.pref = pref;
        }

        public void publish(PreferenceEvent id, Object newValue) {
            pref.pushEvent(id, newValue);
        }

    }
}
