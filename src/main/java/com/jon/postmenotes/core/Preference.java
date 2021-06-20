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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jlastril
 */
public class Preference {
    private static final Logger LOG = Logger.getLogger(Preference.class.getSimpleName());
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
        if(!prefData.containsKey(arg0)){
            LOG.log(Level.SEVERE, "no data for {0}", arg0);
        }
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
