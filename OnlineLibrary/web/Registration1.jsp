<%-- 
    Document   : Registration1
    Created on : Jul 22, 2012, 8:52:41 PM
    Author     : Sathish
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration For Online Library</title>
    </head>
    <%@page import="P.DBHandler" %>
    <%
        DBHandler d = new DBHandler();
        int i;
        String s[] = new String[6], st1, st2, st3, st4, st5, st6, st7;
        s[1] = request.getParameter("t1");
        st1 = s[1];
        s[0] = request.getParameter("t2");
        st2 = s[0];
        s[2] = request.getParameter("t3");
        st3 = s[2];
        s[3] = request.getParameter("t4");
        st4 = s[3];
        s[4] = request.getParameter("t5");
        st5 = s[4];
        s[5] = request.getParameter("t6");
        st6 = s[5];
        i = d.validateDetails(s);
        st7 = request.getParameter("s1");
        if (i == 5 & !d.isUserExist(s[0])) {
            s[3] = s[4];
            s[4] = s[5];
            s[5] = request.getParameter("s1");
            d.insertDetails(s);
            out.println("<h1>Congratulations,<br>");
            if (s[5].equalsIgnoreCase("male")) {
                out.println("Mr.");
            } else {
                out.println("Miss.");
            }
            out.println(st1+", Your Registration Successfully Completed.</h1>");
            out.println("<a href=\"Login.jsp\">Click here to go to Home.</a>");
            } else {
    %>

    <form name="f1" action="Registration1.jsp">
        <td class="nm"><span style="font-family: arial,verdana; font-size: 10pt;">
                <center>                    
                    <br><h1>Please Fill The Registration Form</h1><br><br>
                    <table border="0" >
                        <tr><td class="nm">Enter Your Name</td><td ><input type="text" size=50 value="<%=st1%>" name="t1" id="t1"/></td></tr>
                        <tr><td class="nm">Enter Your User ID</td><td><input type="text" size=50 value="<%=st2%>" name="t2"/></td></tr>
                        <tr><td class="nm">Enter Password</td><td><input type="text" size=50 value="<%=st3%>" name="t3"/></td></tr>
                        <tr><td class="nm">Re-Enter Password</td><td><input type="text" size=50 value="<%=st4%>" name="t4"/></td></tr>
                        <tr><td class="nm">Enter Your Email Address</td><td><input type="text" size=50 value="<%=st5%>" name="t5"/></td></tr>
                        <tr><td class="nm">Enter Your Mobile Number</td><td><input type="text" size=50 value="<%=st6%>" name="t6"/></td></tr>
                        <tr><td class="nm">Select Your Gender</td><td><select name="s1" value="<%=st7%>"><option>Male</option><option>Female</option></select></td></tr>
                    </table>
                    <br><br><input type="submit" value="Register" name="b1"/>
                    </from>
                    <%
                            out.println("<br>");
                            if (i == 1) {
                                out.println("<b>Invalid Name.");
                            } else if (i == 2) {
                                out.println("<b>Password do not match.");
                            } else if (i == 3) {
                                out.println("<b>Invalid Electronic mail");
                            } else if (i == 4) {
                                out.println("<b>Invalid mobile no.");
                            } else {
                                out.println("<b>User id alreaady exist.");
                            }
                        }
                    %>
                    </html>

