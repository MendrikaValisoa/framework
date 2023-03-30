/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etu2005.framework.servlet;

import etu2005.framework.FonctionUrl;
import etu2005.framework.Mapping;
import etu2005.framework.ModelView;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Best
 */
public class FrontServlet extends HttpServlet {
    Map<String,Mapping> MappingUrls = new HashMap<>();

    @Override
    public void init() throws ServletException {
    //    super.init(); //To change body of generated methods, choose Tools | Templates.
    }
        
     /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         try {
            String current = request.getRequestURI().replace(request.getContextPath(), "");
            response.getWriter().println("Current URI: " + current); // Débogage

            if (MappingUrls.containsKey(current)) {
                Mapping mapp = MappingUrls.get(current);
                String className = mapp.getClassName();
                String packageName = "test";
                String fullClassName = packageName + "." + className;

                response.getWriter().println("Class name: " + className); // Débogage

                Object obj = Class.forName(fullClassName).getConstructor().newInstance();
                System.out.println("Class name: " + obj.getClass().getName()); // Afficher le nom de la classe
                System.out.println("Method name: " + mapp.getMethod()); // Afficher le nom de la méthode
                ModelView model = (ModelView) obj.getClass().getMethod(mapp.getMethod()).invoke(obj);
                System.out.println("View name: " + model.getView()); // Afficher le nom de la vue
                RequestDispatcher disp = request.getRequestDispatcher(model.getView());
                disp.forward(request, response);
            }
        } catch (

        Exception e) {
            e.printStackTrace();
            // Afficher un message d'erreur à l'utilisateur
            response.getWriter().println("Une erreur est survenue : " + e.getMessage());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
