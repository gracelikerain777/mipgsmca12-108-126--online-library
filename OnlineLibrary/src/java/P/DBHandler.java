/*
Document   : DBHandler.java
Created on : Jun 15, 2012, 11:03:45 PM
Author     : Sathish
 */
package P;

import java.io.File;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Iterator;

public class DBHandler {

    Connection con;
    Statement st;
    ResultSet rs;
    String query, qrs[], dcs[], path = "D:\\t\\OnlineLibrary\\web\\srcdoc";

    public DBHandler() {
        try {
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "system", "S");
            st = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName(String s) throws Exception {
        rs = st.executeQuery("select Name from USERDETAILS where id='" + s + "'");
        while (rs.next()) {
            return rs.getString(1);
        }
        return "";
    }

    public int validateDetails(String s1[]) {
        int i, r, j;
        boolean f = false;
        String s = s1[1];
        for (i = 0; i < s.length(); i++) {
            r = (int) s.charAt(i);
            if (!((r >= 'A' & r <= 'Z') | (r >= 'a' & r <= 'z'))) {
                return 1;
            }
        }
        s = s1[4];
        for (i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '@') {
                for (j = 0; j < s.length(); j++) {
                    if (s.charAt(j) == '.') {
                        f = true;
                        break;
                    }
                }
                break;
            }
        }
        if (!f) {
            return 3;
        }
        s = s1[5];
        if (s.length() != 10) {
            return 4;
        }
        for (i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' | s.charAt(i) > '9') {
                return 4;
            }
        }
        if (!s1[3].equals(s1[2])) {
            return 2;
        }

        return 5;
    }

    public boolean isUserExist(String s) throws Exception {
        try {
            rs = st.executeQuery("select count(*) from USERDETAILS where id='" + s + "'");
            while (rs.next()) {
                if (rs.getInt(1) == 0) {
                    return false;
                } else {
                    return true;
                }
            }
        } catch (SQLException e) {
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE USERDETAILS(ID VARCHAR2(50) PRIMARY KEY,NAME VARCHAR2(50),PWD VARCHAR2(50),EMAIL VARCHAR2(50),MOBILE VARCHAR2(10),GENDER VARCHAR2(6))");
                st.executeUpdate("commit");
                return false;
            }
        }
        return true;
    }

    public int insertDetails(String s[]) throws Exception {
        try {
            st.executeUpdate("insert into USERDETAILS values('" + s[0] + "','" + s[1] + "','" + s[2] + "','" + s[3] + "','" + s[4] + "','" + s[5] + "')");
            return 1;
        } catch (SQLException e) {
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE USERDETAILS(ID VARCHAR2(50) PRIMARY KEY,NAME VARCHAR2(50),PWD VARCHAR2(50),EMAIL VARCHAR2(50),MOBILE VARCHAR2(10),GENDER VARCHAR2(6))");
                st.executeUpdate("commit");
                return insertDetails(s);
            }
            return 0;
        }
    }

    public boolean validate(String us, String ps) throws Exception {
        int n = 0;
        try {
            rs = st.executeQuery("select count(*) from USERDETAILS where ID='" + us + "' and PWD='" + ps + "'");
            while (rs.next()) {
                n = rs.getInt(1);
            }
            if (n == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE USERDETAILS(ID VARCHAR2(50) PRIMARY KEY,NAME VARCHAR2(50),PWD VARCHAR2(50),EMAIL VARCHAR2(50),MOBILE VARCHAR2(10),GENDER VARCHAR2(6))");
                st.executeUpdate("commit");
                return false;
            }
            return false;
        }
    }

    private int isQueryExist(String qs) throws Exception {
        String s[];
        int n, i, j, d;
        try {
            d = 0;
            n = 1;
            rs = st.executeQuery("select count(*) from QUERRIES");
            while (rs.next()) {
                n = rs.getInt(1);
            }
            if (n != 0) {
                s = new String[n];
                rs = st.executeQuery("select QRY from QUERRIES");
                i = 0;
                while (rs.next()) {
                    s[i] = rs.getString(1);
                    i++;
                }
                for (i = 0; i < s.length; i++) {
                    d = isMatch(qs, s[i]);
                    if (d == 1) {
                        break;
                    }
                }
                return d;
            }
        } catch (SQLException e) {
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE QUERRIES(QID NUMBER(6) PRIMARY KEY,QRY VARCHAR2(50) NOT NULL)");
                st.executeUpdate("commit");
            }
        }
        return -1;
    }

    public int isDocumentExist(String ds) throws Exception {
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
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE DOCUMENTS(DID NUMBER(6) PRIMARY KEY,DNAME VARCHAR2(50) NOT NULL)");
                st.executeUpdate("commit");
                return 0;
            }
        }
        return -1;
    }

    private void adjustDatabase() throws Exception {
        int i, j, a[][], n = 1;
        String s[] = new File(path).list();
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
                    st.executeUpdate("delete from Indexses where DID=" + a[j][0]);
                    st.executeUpdate("delete from Ranks where DID=" + a[j][0]);
                    st.executeUpdate("delete from Documents where  DID=" + a[j][0]);
                }
            }
        } catch (SQLException e) {
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
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE QUERRIES(QID NUMBER(6) PRIMARY KEY,QRY VARCHAR2(50)NOT NULL)");
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
        int n, i, j, d, a[];
        try {
            d = 0;
            n = 1;
            rs = st.executeQuery("select count(*) from Documents");
            while (rs.next()) {
                n = rs.getInt(1);
            }
            if (n != 0) {
                a = new int[n];
                dcs = new String[n];
                rs = st.executeQuery("select DNAME from Documents");
                i = 0;
                while (rs.next()) {
                    dcs[i] = rs.getString(1);
                    i++;
                }
                for (i = 0; i < dcs.length; i++) {
                    d = isMatch(s, dcs[i]);
                    if (d == 1) {
                        break;
                    }
                }
                if (d == 0) {
                    rs = st.executeQuery("select DID from Documents order by DID");
                    i = 0;
                    while (rs.next()) {
                        a[i] = rs.getInt(1);
                        i++;
                    }
                    for (i = 0; i < a.length; i++) {
                        if ((i + 1) != a[i]) {
                            break;
                        }
                    }
                    st.executeUpdate("insert into Documents values(" + (i + 1) + ",'" + s + "')");
                    return i + 1;
                } else {
                    rs = st.executeQuery("select * from documents where dname='" + s + "'");
                    while (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            } else {
                st.executeUpdate("insert into Documents values(1,'" + s + "')");
            }
            return 1;
        } catch (SQLException e) {
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE DOCUMENTS(DID NUMBER(6) PRIMARY KEY,DNAME VARCHAR2(50) NOT NULL)");
                st.executeUpdate("commit");
                return insertDocument(s);
            }
        }
        return -1;
    }

    int insertWord(String s) throws Exception {
        int n, i = 0;
        n = 0;
        s = s.toLowerCase();
        try {
            int a[];
            rs = st.executeQuery("select count(*) from WORDS where wrd='" + s + "'");
            while (rs.next()) {
                n = rs.getInt(1);
            }
            if (n == 0) {
                rs = st.executeQuery("select count(*) from WORDS");
                while (rs.next()) {
                    n = rs.getInt(1);
                }
                a = new int[n];
                rs = st.executeQuery("select WID from WORDS order by WID");
                i = 0;
                while (rs.next()) {
                    a[i] = rs.getInt(1);
                    i++;
                }
                for (i = 0; i < a.length; i++) {
                    if ((i + 1) != a[i]) {
                        break;
                    }
                }
                st.executeQuery("insert into WORDS values(" + (i + 1) + ",'" + s + "')");
                return i + 1;
            } else {
                rs = st.executeQuery("select WID from WORDS where WRD='" + s + "'");
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE WORDS(WID NUMBER(6) PRIMARY KEY,WRD VARCHAR2(50) NOT NULL)");
                st.executeUpdate("commit");
                return insertWord(s);
            }
        }
        return -1;
    }

    void insertIndex(String ws, String ds) throws Exception {
        int w, d, n = 0;
        w = insertWord(ws);
        d = insertDocument(ds);
        try {
            rs = st.executeQuery("select count(*) from INDEXSES where WID=" + w + " and DID=" + d);
            while (rs.next()) {
                n = rs.getInt(1);
            }
            if (n == 0) {
                rs = st.executeQuery("insert into INDEXSES values(" + w + "," + d + ")");
            }
        } catch (SQLException e) {
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE INDEXSES(WID NUMBER(6) REFERENCES WORDS(WID),DID NUMBER(6) REFERENCES DOCUMENTS(DID))");
                st.executeUpdate("commit");
                insertIndex(ws, ds);
            }
        }
    }

    public void createIndex(String s1) throws Exception {
        Read r = new Read();
        String s2[], s = r.readFile(s1);
        s2 = s.split(" ");
        for (int i = 0; i < s2.length; i++) {
            if (!s2[i].equalsIgnoreCase("")) {
                insertIndex(s2[i], s1.substring(s1.lastIndexOf("\\") + 1, s1.length()));
            }
        }
    }

    void generateIndex() throws Exception {
        File f = new File(path);
        String s[] = f.list();
        for (int i = 0; i < s.length; i++) {
            createIndex(path + "\\" + s[i]);
        }
    }

    public String[][] getValues(String qs) throws Exception {
        int q, n, i = 0, j = 0;
        String s[][], s1[];
        n = 1;
        q = 0;
        if (isQueryExist(qs) == 0) {
            s = new String[1][1];
            s[0][0] = "-1";
            return s;
        } else {
            try {
                q = insertQuery(qs);
                if (isAnyChange(new File(path).list())) {
                    s = new String[1][1];
                    s[0][0] = "-1";
                    adjustDatabase();
                    return s;
                } else {
                    rs = st.executeQuery("select count(*) from Ranks where qid=" + q);
                    while (rs.next()) {
                        n = rs.getInt(1);
                    }
                    s = new String[n][3];
                    rs = st.executeQuery("select did,rnk,wght from Ranks where qid=" + q);
                    i = 0;
                    while (rs.next()) {
                        s[i][0] = rs.getString(1);
                        s[i][1] = rs.getString(2);
                        s[i][2] = rs.getString(3);
                        i++;
                    }
                    for (i = 0; i < s.length; i++) {
                        rs = st.executeQuery("select DNAME from Documents where did=" + s[i][0]);
                        while (rs.next()) {
                            s[i][0] = rs.getString(1);
                        }
                    }
                    return s;
                }
            } catch (SQLException e) {
                try {
                    if (eh(e).equalsIgnoreCase("ORA-00942")) {
                        st.executeUpdate("CREATE TABLE RANKS(QID NUMBER(6) REFERENCES QUERRIES(QID),DID NUMBER(6) REFERENCES DOCUMENTS(DID),RNK NUMBER(6) NOT NULL,WGHT VARCHAR2(20) NOT NULL,PRIMARY KEY(QID,DID))");
                        st.executeUpdate("commit");
                        s = new String[1][1];
                        s[0][0] = "-1";
                        return s;
                    }
                } catch (SQLException e1) {
                    if (eh(e1).equalsIgnoreCase("ORA-00942")) {
                        st.executeUpdate("CREATE TABLE DOCUMENTS(DID NUMBER(6) PRIMARY KEY,DNAME VARCHAR2(50) NOT NULL)");
                        st.executeUpdate("commit");
                        s = new String[1][1];
                        s[0][0] = "-1";
                        return s;
                    }
                }

            }
        }
        s = new String[1][1];
        s[0][0] = "-1";
        return s;
    }

    private boolean isAnyChange(String f[]) throws Exception {
        int n, i, j;
        n = 0;
        i = 0;
        j = 0;
        String s1[];
        try {
            rs = st.executeQuery("select count(*) from Documents");
            while (rs.next()) {
                n = rs.getInt(1);
            }
            s1 = new String[n];
            rs = st.executeQuery("select dname from Documents");
        } catch (Exception e) {
            return true;
        }
        while (rs.next()) {
            s1[i] = rs.getString(1);
            i++;
        }
        if (s1.length != f.length) {
            if (s1.length >= f.length) {
                return true;
            }
        }
        for (i = 0; i < s1.length; i++) {
            for (j = 0; j < f.length; j++) {
                if (s1[i].equalsIgnoreCase(f[j])) {
                    break;
                }
            }
            if (j == f.length) {
                break;
            }
        }
        if (i < s1.length) {
            return true;
        } else {
            return false;
        }
    }

    public File[] getDocuments(String q) throws Exception {
        String s[] = new File(path).list();
        if (isAnyChange(s)) {
            adjustDatabase();
            generateIndex();
        }
        File f[] = new File[1];
        f[0] = new File("");
        Iterator it;
        q = q.toLowerCase();
        LinkedHashSet v = new LinkedHashSet();
        int i, a[];
        String qs[], dc[];
        try {
            qs = q.split(" ");
            for (i = 0; i < qs.length; i++) {
                rs = st.executeQuery("select wid from words where wrd='" + qs[i] + "'");
                while (rs.next()) {
                    v.add(rs.getInt(1));
                }
            }
            i = 0;
            a = new int[v.size()];
            it = v.iterator();
            while (it.hasNext()) {
                a[i] = Integer.parseInt("" + it.next());
                i++;
            }
            v = new LinkedHashSet();
            for (i = 0; i < a.length; i++) {
                rs = st.executeQuery("select did from Indexses where wid=" + a[i]);
                while (rs.next()) {
                    v.add(rs.getInt(1));
                }
            }
            a = new int[v.size()];
            it = v.iterator();
            i = 0;
            while (it.hasNext()) {
                a[i] = Integer.parseInt("" + it.next());
                i++;
            }
            dc = new String[a.length];
            for (i = 0; i < a.length; i++) {
                rs = st.executeQuery("select dname from documents where did=" + a[i]);
                while (rs.next()) {
                    dc[i] = path + "\\" + rs.getString(1);
                }
            }
            f = new File[dc.length];
            for (i = 0; i < dc.length; i++) {
                f[i] = new File(dc[i]);
            }
            return f;
        } catch (SQLException e) {
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                generateIndex();
                return getDocuments(q);
            }
        }
        return f;
    }

    public int getRank(String qs, String ds, double w1) throws Exception {
        int q, n, d = insertDocument(ds);
        n = 1;
        String w = "" + w1;
        q = insertQuery(qs);
        try {
            rs = st.executeQuery("select count(*) from Ranks where qid=" + q + " and did=" + d);
            while (rs.next()) {
                n = rs.getInt(1);
            }
            if ((n == 0)) {
                st.executeUpdate("insert into Ranks values(" + q + "," + d + "," + "0," + w + ")");
                return 0;
            } else {
                rs = st.executeQuery("select rnk from Ranks where qid=" + q + " and did=" + d);
                while (rs.next()) {
                    n = rs.getInt(1);
                }
                return n;
            }
        } catch (SQLException e) {
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE RANKS(QID NUMBER(6) REFERENCES QUERRIES(QID),DID NUMBER(6) REFERENCES DOCUMENTS(DID),RNK NUMBER(6) NOT NULL,WGHT VARCHAR2(20) NOT NULL,PRIMARY KEY(QID,DID))");
                st.executeUpdate("commit");
                return getRank(qs, ds, w1);
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
            if (eh(e).equalsIgnoreCase("ORA-00942")) {
                st.executeUpdate("CREATE TABLE RANKS(QID NUMBER(6) REFERENCES QUERRIES(QID),DID NUMBER(6) REFERENCES DOCUMENTS(DID),RNK NUMBER(6) NOT NULL,WGHT VARCHAR2(20) NOT NULL,PRIMARY KEY(QID,DID))");
                st.executeUpdate("commit");
                return increaseRank(qs, ds);
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