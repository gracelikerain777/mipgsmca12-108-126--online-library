package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class Registration_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.Vector _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Registration Page</title>\n");
      out.write("    </head>\n");
      out.write("    <form name=\"f1\" action=\"Registration1.jsp\">\n");
      out.write("    <td class=\"nm\"><span style=\"font-family: arial,verdana; font-size: 10pt;\">\n");
      out.write("                      <center>                    \n");
      out.write("                    <br><h1>Please Fill The Registration Form</h1><br><br>\n");
      out.write("                        <table border=\"0\" >\n");
      out.write("                        <tr><td class=\"nm\">Enter Your Name</td><td ><input type=\"text\" size=50 value=\"\" name=\"t1\" id=\"t1\"/></td></tr>\n");
      out.write("                        <tr><td class=\"nm\">Enter Your User ID</td><td><input type=\"text\" size=50 value=\"\" name=\"t2\"/></td></tr>\n");
      out.write("                        <tr><td class=\"nm\">Enter Password</td><td><input type=\"text\" size=50 value=\"\" name=\"t3\"/></td></tr>\n");
      out.write("                        <tr><td class=\"nm\">Re-Enter Password</td><td><input type=\"text\" size=50 value=\"\" name=\"t4\"/></td></tr>\n");
      out.write("                        <tr><td class=\"nm\">Enter Your Email Address</td><td><input type=\"text\" size=50 value=\"\" name=\"t5\"/></td></tr>\n");
      out.write("                        <tr><td class=\"nm\">Enter Your Mobile Number</td><td><input type=\"text\" size=50 value=\"\" name=\"t6\"/></td></tr>\n");
      out.write("                        <tr><td class=\"nm\">Select Your Gender</td><td><select name=\"s1\"><option>Male</option><option>Female</option></select></td></tr>\n");
      out.write("                    </table>\n");
      out.write("                    <br><br><input type=\"submit\" value=\"Register\" name=\"b1\"/>\n");
      out.write("                    </from>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
