<%-- 
    Document   : upoad
    Created on : Jun 8, 2012, 11:01:16 AM
    Author     : Sathish
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <div align="center">
        <img src="DisplayImg" style="height: 150px;width: 150px;" />
        <form action="updatepic" method="POST" ENCTYPE="multipart/form-data" >
        <table style="text-align: center;">
        <tr><td>Profile Picture </td> <td><input class="n" type="file" name="file"/></td></tr>
                        <tr> <td> </td><td> </td></tr>
                
                        <tr> <td><input type="submit" value="NEXT  >>" /> </td><td> <a href="showprofile.jsp"  >skip this step</a> </td> </tr>
        </table>
        </form>
        </div>
        
    </body>
</html>
