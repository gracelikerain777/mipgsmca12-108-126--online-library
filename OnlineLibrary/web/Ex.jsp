<%-- 
    Document   : Ex
    Created on : Jun 23, 2012, 12:17:05 AM
    Author     : Sathish
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exception Page</title>
    </head>
    <body style='color: #ff0000'>
        <h1>Exception:<br></h1>
        <h3><%out.print(request.getParameter("hidden"));%></h3>
    <center><img src="stop.png" /></center>
    </body>
</html>
