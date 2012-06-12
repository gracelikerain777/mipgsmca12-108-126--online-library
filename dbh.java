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
void insertQuery(String s)throws Exception
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
for(i=0;i<qrs.length;i=i+1)
{
d=isMatch(s,qrs[i]);
p(qrs[i]+" "+i+" "+d);
if(d==1)
break;
}
if(d==0)
st.executeUpdate("insert into QUERY1 values("+(n+1)+",'"+s+"')");
}
else
st.executeUpdate("insert into QUERY1 values(1,'"+s+"')");
}
catch (SQLException e)
{
String es=e.toString();
if(eh(e).equalsIgnoreCase("ORA-00942"))
{
st.executeUpdate("create table QUERY1(QID number(6),QRY varchar2(50))");
insertQuery(s);
}
else
System.out.println(e.toString());
}
}
String eh(SQLException e)
{
String es=e.toString();
return (es.substring(es.indexOf(":")+1,es.lastIndexOf(":"))).trim();
}
void insertDocument(String s)throws Exception
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
st.executeUpdate("insert into Document1 values("+(n+1)+",'"+s+"')");
}
else
st.executeUpdate("insert into Document1 values(1,'"+s+"')");
}
catch (SQLException e)
{
String es=e.toString();
if(eh(e).equalsIgnoreCase("ORA-00942"))
{
st.executeUpdate("create table Document1(DID number(6),DNAME varchar2(50))");
insertDocument(s);
}
else
System.out.println(e.toString());
}
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
d.insertQuery(b.readLine());
d.p("\nEnter Documnent name:");
d.insertDocument(b.readLine());
}
catch(Exception e)
{
e.printStackTrace();
}
}
};