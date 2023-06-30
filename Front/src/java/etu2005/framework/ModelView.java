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
    HashMap<String, Object> data = new HashMap<>();
    Boolean gson = false;
    
    public void setGson(Boolean gson) {
        this.gson = gson;
    }
    public Boolean isGson(){
        return this.gson;
    }    
    public String getView() {
        return view;
    }    
    public void setView(String view) {
        this.view = view;
    }
    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }
    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
    public HashMap<String, Object> getSession() {
        return session;
    }
    public HashMap<String, Object> getData() {
        return data;
    }
    public void addSession(String key,Object object) {
        this.session.put(key,object);
    }
    public void addItem(String key , Object object) {
        this.getData().put(key, object);
    }
    
}
