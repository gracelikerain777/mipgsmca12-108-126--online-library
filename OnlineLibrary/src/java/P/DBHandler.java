/*
Document   : DBHandler.java
Created on : Jun 15, 2012, 11:03:45 PM
Author     : Sathish
 */
package P;

import java.io.File;
import java.sql.*;

public class DBHandler {

    Connection con;
    Statement st;
    ResultSet rs;
    String query, qrs[], dcs[];

    public DBHandler() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "SATHISH");
            st = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int isExists(String ds) throws Exception {
        String s[];
        int n, i, j, d;
        try {
            d = 0;
            n = 1;
            rs = st.executeQuery("select count(*) from DocumentS");
            while (rs.next()) {
                n = rs.getInt(1);
            }
            if (n != 0) {
                s = new String[n];
                rs = st.executeQuery("select DNAME from Documents");
                i = 0;
                while (rs.next()) {
                    s[i] = rs.getString(1);
                    i++;
                }
                for (i = 0; i < s.length; i = i + 1) {
                    d = isMatch(ds, s[i]);
                    if (d == 1) {
                        break;
                    }
                }
                return d;
            }
        } catch (SQLException e) {
            String es = e.toString();
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE DOCUMENTS(DID NUMBER(6) PRIMARY KEY,DNAME VARCHAR2(50))");
                st.executeUpdate("commit");
                return 0;
            }
        }
        return -1;
    }

    private void adjust() throws Exception {
        int i, j, a[][], n = 1;
        String s[] = new File("D:\\t\\OnlineLibrary\\web\\srcdoc").list();
        try {
            rs = st.executeQuery("select count(*) from documents");
            while (rs.next()) {
                n = rs.getInt(1);
            }
            a = new int[n][2];
            rs = st.executeQuery("select DID from documents");
            j = 0;
            while (rs.next()) {
                a[j][0] = rs.getInt(1);
                a[j][1] = 0;
                j++;
            }
            for (i = 0; i < s.length; i++) {
                rs = st.executeQuery("select DID from Documents where DNAME='" + s[i] + "'");
                while (rs.next()) {
                    for (j = 0; j < a.length; j++) {
                        if (a[j][0] == rs.getInt(1)) {
                            a[j][1] = 1;
                        }
                    }
                }
            }
            for (j = 0; j < a.length; j++) {
                if (a[j][1] == 0) {
                    st.executeUpdate("delete from Ranks where DID=" + a[j][0]);
                    st.executeUpdate("delete from Documents where  DID=" + a[j][0]);
                }
            }
        } catch (SQLException e) {
            String es = e.toString();
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE DOCUMENTS(DID NUMBER(6) PRIMARY KEY,DNAME VARCHAR2(50))");
                st.executeUpdate("commit");
            }
        }
    }

    public int insertQuery(String s) throws Exception {
        int n, i, j, d;
        try {
            d = 0;
            n = 1;
            rs = st.executeQuery("select count(*) from QUERRIES");
            while (rs.next()) {
                n = rs.getInt(1);
            }
            if (n != 0) {
                qrs = new String[n];
                rs = st.executeQuery("select QRY from QUERRIES");
                i = 0;
                while (rs.next()) {
                    qrs[i] = rs.getString(1);
                    i++;
                }
                for (i = 0; i < qrs.length; i++) {
                    d = isMatch(s, qrs[i]);
                    if (d == 1) {
                        break;
                    }
                }
                if (d == 0) {
                    st.executeUpdate("insert into QUERRIES values(" + (i + 1) + ",'" + s + "')");
                }
                return i + 1;
            } else {
                st.executeUpdate("insert into QUERRIES values(1,'" + s + "')");
            }
            return 1;
        } catch (SQLException e) {
            String es = e.toString();
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE QUERRIES(QID NUMBER(6) PRIMARY KEY,QRY VARCHAR2(50))");
                st.executeUpdate("commit");
                return insertQuery(s);
            }
        }
        return -1;
    }

    private String eh(SQLException e) {
        String es = e.toString();
        return (es.substring(es.indexOf(":") + 1, es.lastIndexOf(":"))).trim();
    }

    public int insertDocument(String s) throws Exception {
        int n, i, j, d;
        try {
            d = 0;
            n = 1;
            rs = st.executeQuery("select count(*) from Documents");
            while (rs.next()) {
                n = rs.getInt(1);
            }
            if (n != 0) {
                dcs = new String[n];
                rs = st.executeQuery("select DNAME from Documents");
                i = 0;
                while (rs.next()) {
                    dcs[i] = rs.getString(1);
                    i++;
                }
                for (i = 0; i < dcs.length; i = i + 1) {
                    d = isMatch(s, dcs[i]);
                    if (d == 1) {
                        break;
                    }
                }
                if (d == 0) {
                    st.executeUpdate("insert into Documents values(" + (i + 1) + ",'" + s + "')");
                }
                return i + 1;
            } else {
                st.executeUpdate("insert into Documents values(1,'" + s + "')");
            }
            return 1;
        } catch (SQLException e) {
            String es = e.toString();
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE DOCUMENTS(DID NUMBER(6) PRIMARY KEY,DNAME VARCHAR2(50))");
                st.executeUpdate("commit");
                return insertDocument(s);
            }
        }
        return -1;
    }

    public int getRank(String qs, String ds) throws Exception {
        int q, n, d = insertDocument(ds);
        n = 1;
        q = insertQuery(qs);
        try {
            rs = st.executeQuery("select count(*) from Ranks where qid=" + q + " and did=" + d);
            while (rs.next()) {
                n = rs.getInt(1);
            }
            if ((n == 0)) {
                st.executeUpdate("insert into Ranks values(" + q + "," + d + "," + "0)");
                return 0;
            } else {
                rs = st.executeQuery("select rnk from Ranks where qid=" + q + " and did=" + d);
                while (rs.next()) {
                    n = rs.getInt(1);
                }
                return n;
            }
        } catch (SQLException e) {
            String es = e.toString();
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE RANKS(QID NUMBER(6) REFERENCES QUERRIES(QID),DID NUMBER(6) REFERENCES DOCUMENTS(DID),RNK NUMBER(6), PRIMARY KEY(QID,DID))");
                st.executeUpdate("commit");
                return getRank(qs, ds);
            }
        }
        return -1;
    }

    public int increaseRank(String qs, String ds) throws Exception {
        int q, n, d = insertDocument(ds);
        n = 1;
        q = insertQuery(qs);
        try {
            rs = st.executeQuery("select count(*) from Ranks where qid=" + q + " and did=" + d);
            while (rs.next()) {
                n = rs.getInt(1);
            }
            if ((n == 0)) {
                st.executeUpdate("insert into Ranks values(" + q + "," + d + "," + "0)");
                return 0;
            } else {
                n = st.executeUpdate("update Ranks set rnk=rnk+1 where qid=" + q + " and did=" + d);
                rs = st.executeQuery("select rnk from Ranks where qid=" + q + " and did=" + d);
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            String es = e.toString();
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE RANKS(QID NUMBER(6) REFERENCES QUERY1(QID),DID NUMBER(6) REFERENCES DOCUMENT1(DID),RNK NUMBER(6), PRIMARY KEY(QID,DID))");
                st.executeUpdate("commit");
                return getRank(qs, ds);
            }
        }
        return -1;
    }

    private int isMatch(String s, String s1) {
        int i, j;
        String q[], d[];
        d = s1.split(" ");
        q = s.split(" ");
        if (q.length < d.length) {
            d = s.split(" ");
            q = s1.split(" ");
        }
        int a[] = new int[q.length];
        for (i = 0; i < q.length; i++) {
            a[i] = 0;
        }
        for (i = 0; i < q.length; i++) {
            for (j = 0; j < d.length; j++) {
                if (q[i].equalsIgnoreCase(d[j])) {
                    a[i] = 1;
                }
            }
        }
        for (i = 0; i < a.length; i++) {
            if (a[i] != 1) {
                break;
            }
        }
        if (i == a.length) {
            return 1;
        } else {
            return 0;
        }
    }
};