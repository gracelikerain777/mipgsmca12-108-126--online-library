import java.sql.*;
import java.io.*;
class dbh
{
Connection con;
Statement st;
ResultSet rs;
String query,qrs[],dcs[];
dbh()
{
try
{
DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver ());
p("DRIVERS LOADED...");
con=DriverManager.getConnection ("jdbc:oracle:thin:@localhost:1521:XE","system","SATHISH");
p("CONNECTION OBTAINED...");
st=con.createStatement ();
}
catch(Exception e)
{
e.printStackTrace();
}
}
public void p(String s)
{
System.out.println ("\n"+s);
}
int insertQuery(String s)throws Exception
{
int n,i,j,d;
try
{
d=0;
n=1;
rs=st.executeQuery("select count(*) from QUERY1");
while(rs.next())
n=rs.getInt(1);
p("n value:"+n);
if(n!=0)
{
qrs=new String[n];
rs=st.executeQuery("select QRY from QUERY1");
i=0;
while(rs.next())
{
qrs[i]=rs.getString(1);
p(qrs[i]);
i++;
}
for(i=0;i<qrs.length;i++)
{
d=isMatch(s,qrs[i]);
p(qrs[i]+" "+i+" "+d);
if(d==1)
break;
}
if(d==0)
st.executeUpdate("insert into QUERY1 values("+(i+1)+",'"+s+"')");
return i+1;
}
else
st.executeUpdate("insert into QUERY1 values(1,'"+s+"')");
return 1;
}
catch (SQLException e)
{
String es=e.toString();
if(eh(e).equalsIgnoreCase("ORA-00942"))
{
st.executeUpdate("CREATE TABLE QUERY1(QID NUMBER(6) PRIMARY KEY,QRY VARCHAR2(50))");
st.executeUpdate("commit");
return insertQuery(s);
}
}
return -1;
}
String eh(SQLException e)
{
String es=e.toString();
return (es.substring(es.indexOf(":")+1,es.lastIndexOf(":"))).trim();
}
int insertDocument(String s)throws Exception
{
int n,i,j,d;
try
{
d=0;
n=1;
rs=st.executeQuery("select count(*) from Document1");
while(rs.next())
n=rs.getInt(1);
p("n value:"+n);
if(n!=0)
{
dcs=new String[n];
rs=st.executeQuery("select DNAME from Document1");
i=0;
while(rs.next())
{
dcs[i]=rs.getString(1);
p(dcs[i]);
i++;
}
for(i=0;i<dcs.length;i=i+1)
{
d=isMatch(s,dcs[i]);
p(dcs[i]+" "+i+" "+d);
if(d==1)
break;
}
if(d==0)
st.executeUpdate("insert into Document1 values("+(i+1)+",'"+s+"')");
return i+1;
}
else
st.executeUpdate("insert into Document1 values(1,'"+s+"')");
return 1;
}
catch (SQLException e)
{
String es=e.toString();
if(eh(e).equalsIgnoreCase("ORA-00942"))
{
st.executeUpdate("CREATE TABLE DOCUMET1(DID NUMBER(6) PRIMARY KEY,DNAME VARCHAR2(50))");
st.executeUpdate("commit");
return insertDocument(s);
}
}
return -1;
}
int getRank(String qs,String ds)throws Exception
{
int q,n,d=insertDocument(ds);
n=1;
q=insertQuery(qs);
try
{
rs=st.executeQuery("select count(*) from Rank1 where qid="+q+" and did="+d);
while(rs.next())
n=rs.getInt(1);
p("---------->n value in getrank:try"+n);
if((n==0))
{
st.executeUpdate("insert into Rank1 values("+q+","+d+","+"0)");
p("---------->n value in getrank:if"+n);
return 0;
}
else
{
p("---------->n value in getrank:else:"+n);
rs=st.executeQuery("select rnk from Rank1 where qid="+q+" and did="+d);
while(rs.next())
n=rs.getInt(1);
return n;
}
}
catch(SQLException e)
{
String es=e.toString();
if(eh(e).equalsIgnoreCase("ORA-00942"))
{
st.executeUpdate("CREATE TABLE RANK1(QID NUMBER(6) REFERENCES QUERY1(QID),DID NUMBER(6) REFERENCES DOCUMENT1(DID),RNK NUMBER(6) PRIMARY KEY(QID,DID))");
st.executeUpdate("commit");
return getRank(qs,ds);
}
}
p("---------->n value in getrank:"+n);
return -1;
}
int isMatch(String s,String s1)
{
int i,j;
String q[],d[]=s1.split(" ");
q=s.split(" ");
int a[]=new int[q.length];
for(i=0;i<q.length;i++)
a[i]=0;
for(i=0;i<q.length;i++)
for(j=0;j<d.length;j++)
if(q[i].equalsIgnoreCase(d[j]))
a[i]=1;
for(i=0;i<a.length;i++)
if(a[i]!=1)
break;
if(i==a.length)
return 1;
else
return 0;
} 
public static void main(String[] args)throws Exception
{
dbh d=new dbh();
String s,s1;
BufferedReader b=new BufferedReader(new InputStreamReader(System.in));
d.p("\nEnter Query");
try
{
s=b.readLine();
d.p("Query id is:"+d.insertQuery(s));
d.p("\nEnter Documnent name:");
s1=b.readLine();
d.p("\nDocument id is:"+d.insertDocument(s1));
d.p("\nRank is:"+d.getRank(s,s1));
}
catch(Exception e)
{
e.printStackTrace();
}
}
};