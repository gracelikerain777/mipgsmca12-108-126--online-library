/*
    Document   : rankpage.java
    Created on : Jun 9, 2012, 10:40:45 AM
    Author     : Sathish
*/
import P.DBHandler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name = "rankpage", urlPatterns = {"/rankpage"})
public class rankpage extends HttpServlet {
String s,s1;
 protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     DBHandler od=new DBHandler();
        response.setContentType("text/html;charset=UTF-8");
 PrintWriter out = response.getWriter();
        try {
            s1=request.getParameter("query");
            s=request.getParameter("page");
            od.increaseRank(s1,s.substring(s.lastIndexOf("\\") + 1, s.length()));
           System.out.println("t1=>"+s1);
            
            response.sendRedirect(s);
           }
        catch(Exception e)
        {
            e.printStackTrace();
        }finally {            
            out.close();
        }    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}