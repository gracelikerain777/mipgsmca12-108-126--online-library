<%-- 
    Document   : Welcome
    Created on : Jun 8, 2012, 11:01:16 AM
    Author     : Sathish
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ONLINE LIBRARY - Home Page</title>
    </head>
    <%
    HttpSession hs=request.getSession(true);
    if(hs.isNew()|hs.getAttribute("login")==null)
        response.sendRedirect("Login.jsp");
      %>
      <form name="f1" action="Search.jsp">
   <pre>
  <br><h3>Welcome <%=hs.getAttribute("name") %>.</h3>                                                                                                <a href="upload.jsp">Upload file</a>    <a href="Logout">Logout out</a>



     <center> 
         <table>
        <tr>
            <td><img src="Online1.jpg" height="100" width="300" /></td>
            </tr>

        <tr><td><input type="text" name="t1" value="Enter your query here and click on Search button" onclick="if(this.value=='Enter your query here and click on Search button')value=''" onblur="if(this.value=='')this.value='Enter your query here and click on Search button'" size="50" /></td></tr>
        <tr><td><input type="submit" value="Search" name="b1" onclick="display()"></td></tr>
         </table>
</center>
    </pre>
        </form>
    
</html>
