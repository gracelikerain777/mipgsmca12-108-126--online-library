package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import P.Search;
import P.Read;

public final class second_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


        String s1[],s2,q[],d[];
        int a[],x;
        int i=0;
        
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
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Search Results</title>\n");
      out.write("       \n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        \n");
      out.write("        <form name=\"f2\" action=\"second.jsp\">\n");
      out.write("          <img src=\"Online1.jpg\" height=\"50\" width=\"150\" />\n");
      out.write("          <input type=\"text\" name=\"t1\" value=\"\"  size=\"50\" />\n");
      out.write("          <input type=\"submit\" value=\"Search\" name=\"b1\">\n");
      out.write("        \n");
      out.write("        ");
      out.write("\n");
      out.write("        ");

                String s=request.getParameter("t1");
                Search se=new Search();
                s1=se.start(s);
            for(int i=0;i<s1.length;i++)
                {
                Read R=new Read();
                s2=R.readFile(s1[i]);
           s1[i]=s1[i].substring(s1[i].lastIndexOf("\\") + 1, s1[i].length());
            out.println("<br><br><A href=\"srcdoc/"+s1[i]+"\">"+s1[i]+"</A><br>");
            
            
            q=s.split(" ");
d=s2.split(" ");
if(d.length<100)
x=d.length;
else
x=100;
a=new int[x];
for(int j=0;j<x;j++)
a[j]=0;
for(int j=0;j<x;j++)
for(int k=0;k<q.length;k++)
if(q[k].equalsIgnoreCase(d[j]))
a[j]=1;
for(int j=0;j<x;j++)
if(a[j]==0)
out.print(d[j]+" ");
else
out.print("<b>"+d[j]+"</b> ");
            
                }
                
             
      out.write("  \n");
      out.write("        </form>  \n");
      out.write("    </body>\n");
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
