/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu2005.framework.servlet;

import etu2005.framework.AnnotationFonction;
import etu2005.framework.Mapping;
import etu2005.framework.ModelView;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Best
 */
public class FrontServlet extends HttpServlet {
    Map<String, Mapping> MappingUrls = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        String p = getInitParameter("test");
        try {
            List<Class<?>> annoted_classes = AnnotationFonction.getClassesWithAnnotation2(AnnotationController.class,
            p);
            for (Class<?> c : annoted_classes) {
                Method[] methods = c.getDeclaredMethods();
                for (Method m : methods) {
                    if (m.isAnnotationPresent(etu2005.framework.Url.class)) {
                        Mapping mapping = new Mapping();
                        mapping.setClassName(c.getName());
                        mapping.setMethod(m.getName());
                        etu2005.framework.Url app = m.getAnnotation(etu2005.framework.Url.class);
                        String url = app.nom();
                        this.MappingUrls.put(url, mapping);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("eto tompoko = " + e.getMessage());
            e.printStackTrace();
        } // To change body of generated methods, choose Tools | Templates.
    }

    public String getSetter(String argument) {
        String setter = "";
        String temp = argument.substring(0, 1).toUpperCase();
        setter = temp + argument.substring(1);
        System.out.println(setter);
        return "set"+setter;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            String query = request.getQueryString();
            String urlString = request.getRequestURL().toString();
            String url = request.getRequestURI().substring(request.getContextPath().length() + 1);
            if (this.MappingUrls.containsKey(url)) {
                out.print(urlString);
                String className = this.MappingUrls.get(url).getClassName();
                String method = this.MappingUrls.get(url).getMethod();
                Class<?> c = Class.forName(className);
                Method m = c.getDeclaredMethod(method);
                Field[] field = c.getDeclaredFields();
                Object o = c.getConstructor().newInstance();
                Enumeration<String> enu = request.getParameterNames();
                List<String> liste = Collections.list(enu);
                for (int i = 0; i < field.length; i++) {
                    for (int j = 0; j < liste.size(); j++) {
                        if (field[i].getName().trim().equals(liste.get(j))) {
                            Method me = c.getDeclaredMethod(this.getSetter(liste.get(j)), field[i].getType());
                            String str = request.getParameter(field[i].getName());
                            System.out.println(str);
                            Object obj = field[i].getType().getConstructor(String.class).newInstance(str);
                            me.invoke(o, obj);
                        }
                    }
                }
                Object mv = m.invoke(o);
                if (mv instanceof ModelView) {
                    ModelView model = (ModelView) mv;
                    RequestDispatcher dispat = request.getRequestDispatcher(model.getView());
                    dispat.forward(request, response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}