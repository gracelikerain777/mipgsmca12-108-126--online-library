<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Results</title>

    </head>
    <body>
        <%!            String s1[][], s2, q[], d[];
            int a[], x;
            int i = 0;

        %>
        <form name="f2" action="Search.jsp">
            <img src="Online1.jpg" height="50" width="150" />
            <%String s = request.getParameter("t1");%>
            <input type="text" name="t1" value="<%=s%>"  size="50" />
            <input type="submit" value="Search" name="b1">
            <%@page import="P.Search,P.Read" %>
            <%
                if (s.equalsIgnoreCase("Enter your query here and click on Search button")) {
                    response.sendRedirect("Welcome.jsp");
                } else {
                    Search se = new Search();
                    s1 = se.start(s);
                   if(s1.length==0)
                                             {
                       out.println("<center><br><br><br><br><b>Sorry,<br><br>Your query did not generate any results.<br><br>That means none of documents which are stored in server match with your query.</center>");
                                             }
                    else
                                               {
                   
                    for (int i = 0; i < s1.length; i++) {
                        Read R = new Read();
                        s2 = R.readFile(s1[i][0]);
                        s1[i][0] = s1[i][0].substring(s1[i][0].lastIndexOf("\\") + 1, s1[i][0].length());
                        out.println("<pre><b><br><br><a href=\"rankpage?page=" + "srcdoc/" + s1[i][0] + "&&query=" + s + "\">" + s1[i][0] + "</a>    Rank:" + s1[i][1] + "          Weight:" + s1[i][2]+"</b><br></pre>");
                        q = s.split(" ");
                        d = s2.split(" ");
                        if (d.length < 200) {
                            x = d.length;
                        } else {
                            x = 200;
                        }
                        a = new int[x];
                        for (int j = 0; j < x; j++) {
                            a[j] = 0;
                        }
                        for (int j = 0; j < x; j++) {
                            for (int k = 0; k < q.length; k++) {
                                if (q[k].equalsIgnoreCase(d[j])) {
                                    a[j] = 1;
                                }
                            }
                        }
                        out.print("<br><b style='color: #aa55ff'>CONTENT TEXT: </b>");
                        for (int j = 0; j < x; j++) {
                            if (a[j] == 0) {
                                out.print(d[j] + " ");
                            } else {
                                out.print("<b><u style='color: #ff0000'>" + d[j] + "</u></b> ");
                            }
                        }

                    }
                }
                                       }
//out.println("<br> CASE:"+s1[s1.length-1][0]);
            %>  
        </form>  
    </body>
</html>
