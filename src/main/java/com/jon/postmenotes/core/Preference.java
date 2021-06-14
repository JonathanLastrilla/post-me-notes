/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jon.postmenotes.core;

import com.jon.postmenotes.Main;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jlastril
 */
public class Preference {

    static final Map<PreferenceEvent, Object> prefData;
    private final List<PreferenceListener> listeners = new ArrayList<>();

    private static final Preference INSTANCE;

    public static Preference getInstance() {
        return INSTANCE;
    }

    static {
        prefData = new HashMap<>();
        INSTANCE = new Preference();
        deSerialize();
    }

    private static void deSerialize() {
        try ( ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Main.PREFS_FILE))) {
            Map<PreferenceEvent, Object> e = (Map<PreferenceEvent, Object>) ois.readObject();
            prefData.clear();
            prefData.putAll(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void serialize() {
        try ( ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Main.PREFS_FILE))) {
            oos.writeObject(prefData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addListener(PreferenceListener l) {
        listeners.add(l);
    }

    protected void pushEvent(PreferenceEvent id, Object newValue) {
        prefData.put(id, newValue);
        listeners.stream()
                .filter(l -> l.subscribedEvents().contains(id))
                .forEach(lis -> lis.apply(id, prefData.get(id)));
    }

    /**
     * use this to preload broadcast the properties to the registered listeners.
     * @param l 
     */
    public void requestUpdate(PreferenceListener l) {        
        prefData.forEach((k, v) -> {
                l.apply(k, v);
            });
    }

    public Object get(PreferenceEvent arg0) {
        return prefData.get(arg0);
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
