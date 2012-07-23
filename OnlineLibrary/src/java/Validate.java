import P.DBHandler;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sathish
 */
@WebServlet(name = "Validate", urlPatterns = {"/Validate"})
public class Validate extends HttpServlet {
DBHandler d=new DBHandler();
    protected void processRequest(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html;charset=UTF-8");
        PrintWriter out = res.getWriter();
        String s = (String) req.getParameter("t1");
        String s2 = (String) req.getParameter("t2");
        try {
              if(d.validate(s, s2))
              {   
               HttpSession hs=req.getSession(false);
               hs.setMaxInactiveInterval(60);
               hs.setAttribute("name",d.getName(s) );
               hs.setAttribute("login", "true");
               res.sendRedirect("Welcome.jsp");
              }
              else
              {
              throw new Exception("Sorry,Invalid login details.");
              }
        } catch(Exception e)
        {
            res.sendRedirect("Ex.jsp?hidden="+e.toString());
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            processRequest(req, res);
        } catch (Exception e) {
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            doGet(req, res);
        } catch (Exception e) {
        }
    }
}