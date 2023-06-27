/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 package test;

import etu2005.framework.ModelView;

import etu2005.framework.AnnotationController;
import etu2005.framework.Url;

/**
 *
 * @author Best
 */
@AnnotationController
public class Classtest {
    String nom;
    Integer phone;
    java.util.Date utilDate;
    java.sql.Date sqlDate;

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPhone(Integer phone) {
        this.phone = phone;
    }
    public void setUtilDate(java.util.Date utilDate) {
        this.utilDate = utilDate;
    }
    public void setSqlDate(java.sql.Date sqlDate){
        this.sqlDate = sqlDate;
    }
    public String getNom() {
        return nom;
    }
    public Integer getPhone() {
        return phone;
    }
    public java.util.Date getUtilDate() {
        return utilDate;
    }
    public java.sql.Date getSqlDate(){
        return sqlDate;
    }

    @Url(nom="test")
    public ModelView view(){
        ModelView model = new ModelView();
        model.setView("index.jsp");
        return model;
    } 

    @Url(nom="getValues")
    public ModelView getValues() {
        ModelView model = new ModelView();
        System.out.println(getUtilDate());
        System.out.println(getSqlDate());
        model.setView("Test.jsp");
        return model;
    }
  
}
