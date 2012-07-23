<%-- 
    Document   : Registration
    Created on : Jul 22, 2012, 8:35:04 PM
    Author     : Sathish
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration Page</title>
    </head>
    <form name="f1" action="Registration1.jsp">
    <td class="nm"><span style="font-family: arial,verdana; font-size: 10pt;">
                      <center>                    
                    <br><h1>Please Fill The Registration Form</h1><br><br>
                        <table border="0" >
                        <tr><td class="nm">Enter Your Name</td><td ><input type="text" size=50 value="" name="t1" id="t1"/></td></tr>
                        <tr><td class="nm">Enter Your User ID</td><td><input type="text" size=50 value="" name="t2"/></td></tr>
                        <tr><td class="nm">Enter Password</td><td><input type="text" size=50 value="" name="t3"/></td></tr>
                        <tr><td class="nm">Re-Enter Password</td><td><input type="text" size=50 value="" name="t4"/></td></tr>
                        <tr><td class="nm">Enter Your Email Address</td><td><input type="text" size=50 value="" name="t5"/></td></tr>
                        <tr><td class="nm">Enter Your Mobile Number</td><td><input type="text" size=50 value="" name="t6"/></td></tr>
                        <tr><td class="nm">Select Your Gender</td><td><select name="s1"><option>Male</option><option>Female</option></select></td></tr>
                    </table>
                    <br><br><input type="submit" value="Register" name="b1"/>
                    </from>
</html>
