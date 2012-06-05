<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Results</title>
       
    </head>
    <body>
        <form name="f2" method="get" action="hello.jsp">
        <%@page import="P.Search" %>
        <%!
        String s1[];
        int i=0;
        %>
        <%
        
        String s=request.getParameter("t1");
              Search se=new Search();
              
            s1=se.start(s);
            for(int i=0;i<s1.length;i++)
            {
            out.println("<br><br><br>"+s1[i]);            
            } %>      
            
            <input type="text" name="<%=i++%>" id="t2" value="<%=s1[1]%>" /> 

            <input type="submit" value="open" name="submit" />
 </form>
 </body>
</html>
