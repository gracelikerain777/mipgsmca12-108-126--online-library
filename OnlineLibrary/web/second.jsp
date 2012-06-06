<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Results</title>
       
    </head>
    <body>
        
        <form name="f2" action="second.jsp">
          <img src="Online1.jpg" height="50" width="150" />
          <input type="text" name="t1" value=""  size="50" />
          <input type="submit" value="Search" name="b1">
        <%@page import="P.Search,P.Read" %>
        <%!
        String s1[],s2,q[],d[];
        int a[],x;
        int i=0;
        %>
        <%
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
                
             %>  
        </form>  
    </body>
</html>
