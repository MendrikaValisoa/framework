/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu2005.framework;

import java.util.HashMap;

/**
 *
 * @author Best
 */
public class ModelView {
    String view;
    HashMap<String, Object> session = new HashMap<>();

    public String getView() {
        return view;
    }
    
    public void setView(String view) {
        this.view = view;
    }
    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }
    public HashMap<String, Object> getSession() {
        return session;
    }
    public void addSession(String key,Object object) {
        this.session.put(key,object);
    }
    
}
