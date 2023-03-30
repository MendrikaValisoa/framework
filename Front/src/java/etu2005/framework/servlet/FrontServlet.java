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
import java.lang.reflect.Method;
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
    Map<String,Mapping> MappingUrls = new HashMap<>();

    @Override
    public void init() throws ServletException {
    //    super.init(); 
    ServletContext context= getServletContext();
            String p = getInitParameter("test");
            try{
                System.out.println(p);
                 List<Class<?>> annoted_classes =  AnnotationFonction.getClassesWithAnnotation2(AnnotationController.class , p );
             for (Class<?> c : annoted_classes) {
                
                 Method[] methods = c.getMethods();
            for (Method m : methods) {
                 if (m.isAnnotationPresent(Url.class)) {
                    Mapping mapping = new Mapping();
                    mapping.setClassName(c.getName());
                    mapping.setMethod(m.getName());
                    Url app = m.getAnnotation(Url.class);
                    String url = app.nom();
                    this.MappingUrls.put(url,mapping);
                    System.out.println(url);
                }
            }
            }
            }catch(Exception e){
                e.printStackTrace();
            } //To change body of generated methods, choose Tools | Templates.
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
         PrintWriter out = response.getWriter();
        try  {  
             String query = request.getQueryString();
             String urlString = request.getRequestURL().toString();
             String url = request.getRequestURI().substring(request.getContextPath().length()+1);
             out.println(url);
             //out.println(this.MappingUrls.containsKey(url));
             if(this.MappingUrls.containsKey(url)){
                 out.print(urlString);
                 String className = this.MappingUrls.get(url).getClassName();
                 String method = this.MappingUrls.get(url).getMethod();
                 Class<?> c= Class.forName(className);
                 Method m= c.getDeclaredMethod(method);
                 Object o= c.newInstance();
                 ModelView mv= (ModelView) m.invoke(o);
                 if( mv.getClass() == ModelView.class ){
                     RequestDispatcher dispat = request.getRequestDispatcher(mv.getView());
                     dispat.forward(request,response);   
                 }
             }
        }catch(Exception e){
            e.printStackTrace(out);
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
