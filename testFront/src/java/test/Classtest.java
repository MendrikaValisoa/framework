/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import etu2005.framework.ModelView;
import etu2005.framework.servlet.AnnotationController;
import etu2005.framework.servlet.Url;

/**
 *
 * @author Best
 */
@AnnotationController
public class Classtest {
     @Url(nom="test")
 public ModelView view()
  {
      ModelView model = new ModelView();
      model.setView("List.jsp");
      return model;
  } 
}
