/* 
    Document   : Search.java
    Created on : Jun 8, 2012, 12:08:54 AM
    Author     : Sathish
*/
package P;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.util.*;
import org.apache.poi.hssf.usermodel.*;
import java.io.*;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hwpf.*;
import org.apache.poi.hwpf.extractor.*;

class fr {

    public String readFile(File f1) {
        fr f = new fr();
        String e = "";
        String s = f1 + "";
        e = s.substring(s.lastIndexOf(".") + 1, s.length());
        if (e.equalsIgnoreCase("doc")) {
            return f.readDoc(s);
        } else if (e.equalsIgnoreCase("txt")) {
            return f.readTxt(s);
        } else if (e.equalsIgnoreCase("pdf")) {
            return f.readPdf(s);
        } else if (e.equalsIgnoreCase("xls")) {
            return f.readXl(s);
        } else if (e.equalsIgnoreCase("html")) {
            return f.readHtml(s);
        } else {
            return "Format does not support.";
        }
    }

    public String readDoc(String s) {
        POIFSFileSystem fs = null;
        try {
            fs = new POIFSFileSystem(new FileInputStream(s));
            HWPFDocument doc = new HWPFDocument(fs);
            WordExtractor we = new WordExtractor(doc);
            s = we.getText();
            s = cleanText(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public String readXl(String s) {
        Vector dataHolder = ReadCSV(s);
        return printCellDataToConsole(dataHolder);
    }

    public static Vector ReadCSV(String s) {
        Vector cellVectorHolder = new Vector();
        try {
            FileInputStream myInput = new FileInputStream(s);
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator rowIter = mySheet.rowIterator();
            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                Vector cellStoreVector = new Vector();
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    cellStoreVector.addElement(myCell);
                }
                cellVectorHolder.addElement(cellStoreVector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellVectorHolder;
    }

    private String printCellDataToConsole(Vector dataHolder) {
        String s = "";
        for (int i = 0; i < dataHolder.size(); i++) {
            Vector cellStoreVector = (Vector) dataHolder.elementAt(i);
            for (int j = 0; j < cellStoreVector.size(); j++) {
                HSSFCell myCell = (HSSFCell) cellStoreVector.elementAt(j);
                s += myCell.toString();
            }
            s += "\n";
            s = cleanText(s);
        }
        return s;
    }

    public String readPdf(String s1) {
        String s = "";
        try {
            PdfReader reader = new PdfReader(s1);
            int i = 1, n = reader.getNumberOfPages();
            for (; i <= n; i++) {
                s += PdfTextExtractor.getTextFromPage(reader, i);
                s = cleanText(s);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return s;
    }

    public String readTxt(String s) {
        String s1 = "";
        int i, n;
        InputStream f;
        try {
            f = new FileInputStream(s);
            n = f.available();
            for (i = 1; i <= n; i++) {
                s1 += (char) f.read();
            }
            s1 = cleanText(s1);
            f.close();
        } catch (Exception e) {
            System.out.print("\nFile not found in specified location.");
        }
        return s1;
    }

    public String cleanText(String s) {
        int n, i = 0, r;
        String s1 = "";
        n = s.length();
        for (; i < n; i++) {
            r = (int) s.charAt(i);
            if (!((r >= 'A' & r <= 'Z') | (r >= 'a' & r <= 'z') | (r >= '0' & r <= '9'))) {
                s1 += s.charAt(i);
            }
        }
        StringTokenizer st = new StringTokenizer(s, s1);
        s = "";
        while (st.hasMoreTokens()) {
            s += " " + st.nextToken();
        }
        return s;
    }

    public String readHtml(String s) {
        try {
            InputStream f = new FileInputStream(s);
            char c;
            s = "";
            int i = 0, n = f.available();
            for (i = 1; i <= n; i++) {
                c = (char) f.read();
                if (c == '<') {
                    do {
                        c = (char) f.read();
                    } while (c != '>');
                }
                if (c != '>') {
                    s += c;
                }
            }
            f.close();
            s = cleanText(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}

class p2 {

    public static double w1(double N, double n, double R, double r) {
        return Math.log10(((r + 0.5) / (R + 1)) / ((n + 1) / (N + 2)));
    }

    public static double w2(double N, double n, double R, double r) {
        return Math.log10(((r + 0.5) / (R + 1)) / ((n - r + 0.5) / (N - R + 1)));
    }

    public static double w3(double N, double n, double R, double r) {
        return Math.log10(((r + 0.5) / (R - r + 0.5)) / ((n + 1) / (N - n + 1)));
    }

    public static double w4(double N, double n, double R, double r) {
        return Math.log10(((r + 0.5) / (R - r + 0.5)) / ((n - r + 0.5) / (N - n - (R - r) + 0.5)));
    }

    public static int Next_Max_Index(double a[][], int k, int N) {
        int i = 0, j = 0;
        double m = 0;
        for (i = 0; i < N; i++) {
            if (a[0][i] != 1.0) {
                m = a[1][i];
                break;
            }
        }
        for (i = 0; i < N; i++) {
            if (k == 0) {
                if (a[1][i] >= m) {
                    m = a[1][i];
                    j = i;
                }
            } else {
                if (a[1][i] >= m && a[0][i] != 1.0) {
                    m = a[1][i];
                    j = i;
                }
            }
        }
        return j;
    }

    public static int ntf(String s, String s1[]) {
        for (int i = 0; i < s1.length; i++) {
            if (s.equalsIgnoreCase(s1[i])) {
                return 1;
            }
        }
        return 0;
    }

    public static int rtf(String s, String s1[]) {
        for (int i = 0; i < s1.length; i++) {
            if (s.equalsIgnoreCase(s1[i]) && s1[s1.length - 1].equals("1")) {
                return 1;
            }
        }
        return 0;
    }

    public String[] start(String q, String d[][]) throws Exception {
        int i, j = 0, N, R = 0, n[], r[], k;
        BufferedReader b, fp1;
        String query[], documents[], s[], s1[];
        File fp[];
        fr f = new fr();
        query = q.split(" ");
        n = new int[query.length];
        r = new int[query.length];
        double w[][], D[][], v[][];
        w = new double[4][query.length];
        N = d.length;
        fp = new File[N];
        for (i = 0; i < N; i++) {
            fp[i] = new File(d[i][0]);
        }
        v = new double[query.length][N];
        D = new double[4][N];
        documents = new String[N];
        for (i = 0; i < N; i++) {
            documents[i] = f.readFile(fp[i]);
            documents[i] = documents[i] + " " + d[i][1];
            s = documents[i].split(" ");
            if (s[s.length - 1].equals("1")) {
                R++;
            }
        }
        for (i = 0; i < n.length; i++) {
            n[i] = 0;
            r[i] = 0;
            for (j = 0; j < documents.length; j++) {
                n[i] += ntf(query[i], documents[j].split(" "));
                r[i] += rtf(query[i], documents[j].split(" "));
            }
        }
        for (i = 0; i < query.length; ++i) {
            w[0][i] = w1(N, n[i], R, r[i]);
            w[1][i] = w2(N, n[i], R, r[i]);
            w[2][i] = w3(N, n[i], R, r[i]);
            w[3][i] = w4(N, n[i], R, r[i]);
        }
        for (i = 0; i < n.length; i++) {
            for (j = 0; j < N; j++) {
                v[i][j] = (double) ntf(query[i], documents[j].split(" "));
            }
        }
        for (j = 0; j < N; j++) {
            D[0][j] = (D[1][j] = (D[2][j] = (D[3][j] = 0.0)));
            for (i = 0; i < query.length; i++) {
                D[0][j] += w[0][i] * v[i][j];
                D[1][j] += w[1][i] * v[i][j];
                D[2][j] += w[2][i] * v[i][j];
                D[3][j] += w[3][i] * v[i][j];
            }
        }
        for (j = 0; j < N; j++) {
            documents[j] = documents[j].replace('1', ' ');
            documents[j] = documents[j].replace('0', ' ');
        }
        v = new double[2][N];
        String sd[] = new String[N];
        for (i = 0; i < 1; i++) {
            for (j = 0; j < N; j++) {
                v[0][j] = 0;
                v[1][j] = D[i][j];
            }
            for (j = 0; j < N; j++) {
                k = Next_Max_Index(v, j, N);
                v[0][k] = 1;
                sd[j] = fp[k].getAbsolutePath();
            }
        }
        return sd;
    }
}

class Vsm {

    int no_of_doc;
    String query;
    ArrayList doc_al = new ArrayList();
    ArrayList unique_words = new ArrayList();
    ArrayList idf_al = new ArrayList();
    ArrayList tf_idf_al = new ArrayList();
    ArrayList tf_idf_query = new ArrayList();
    ArrayList<Double> similarity_al = new ArrayList<Double>();
    ArrayList doc_paths = new ArrayList();

    void get_Documents_From_Directory(String dir_name) throws IOException {
        fr f = new fr();
        File directory = new File(dir_name);
        File[] list_of_files = directory.listFiles();
        for (int j = 0; j < list_of_files.length; j++) {
            if (list_of_files[j].isDirectory()) {
                get_Documents_From_Directory(list_of_files[j].getAbsolutePath());
            }
            if (list_of_files[j].isFile()) {
                File path = list_of_files[j];
                String doc_content = "";
                doc_content = (f.readFile(path));
                doc_al.add(doc_content);
                doc_paths.add(list_of_files[j]);
                get_Unique_Words(doc_content);
            }
        }
    }

    void get_Unique_Words(String doc) {
        StringTokenizer st = new StringTokenizer(doc);
        String word;
        while (st.hasMoreTokens()) {
            word = st.nextToken();
            if (unique_words.contains(word)) {
                continue;
            }
            unique_words.add(word);
        }
    }

    void get_Query_Doc(String s) {
        query = s;
        get_Unique_Words(query);
        no_of_doc = doc_al.size();
    }

    void cal_Idf() {
        int total_words, count, term_freq;
        double idf, value;
        total_words = unique_words.size();
        for (int i = 0; i < total_words; i++) {
            count = 0;
            for (int j = 0; j < no_of_doc; j++) {
                boolean found = word_Check((String) unique_words.get(i), (String) doc_al.get(j));
                if (found) {
                    count++;
                }
            }
            if (count == 0) {
                idf_al.add(new Double(0));
            } else {
                value = (double) count / (double) no_of_doc;
                idf = Math.log10(value);
                idf_al.add(idf);
            }
        }
    }

    boolean word_Check(String word, String doc) {
        StringTokenizer st = new StringTokenizer(doc);
        String doc_word;
        while (st.hasMoreTokens()) {
            doc_word = (String) st.nextToken();
            if (word.equalsIgnoreCase(doc_word)) {
                return true;
            }
        }
        return false;
    }

    void cal_Tf_Idf_Query() {
        int total_words;
        double tf_idf, term_freq, idf;
        total_words = unique_words.size();
        for (int j = 0; j < total_words; j++) {
            term_freq = (double) cal_Tf((String) unique_words.get(j), query);
            idf = ((Double) idf_al.get(j)).doubleValue();
            tf_idf = term_freq * idf;
            tf_idf_query.add(tf_idf);
        }
    }

    int cal_Tf(String word, String doc) {
        StringTokenizer st = new StringTokenizer(doc);
        String doc_word;
        int count = 0;
        while (st.hasMoreTokens()) {
            doc_word = st.nextToken();
            if (word.equalsIgnoreCase(doc_word)) {
                count++;
            }
        }
        return count;
    }

    void cal_Tf_Idf_Doc() {
        int total_words;
        double tf_idf, term_freq, idf, sc, query_tf_idf;
        total_words = unique_words.size();
        for (int i = 0; i < no_of_doc; i++) {
            sc = 0;
            for (int j = 0; j < total_words; j++) {
                term_freq = (double) cal_Tf((String) unique_words.get(j), (String) doc_al.get(i));
                idf = ((Double) idf_al.get(j)).doubleValue();
                tf_idf = term_freq * idf;
                query_tf_idf = ((Double) tf_idf_query.get(j)).doubleValue();
                sc = sc + (tf_idf * query_tf_idf);
            }
            similarity_al.add(sc);
        }
    }

    String[][] display_Sc() {
        Double[] dval = (Double[]) similarity_al.toArray(new Double[0]);
        String[][] s = new String[dval.length][2];
        int index, count = 0, n = 0;
        for (int i = 0;;) {
            index = i;
            for (int j = 1; j < dval.length; j++) {
                if (dval[index] < dval[j]) {
                    index = j;
                }
            }
            s[n][1] = "" + dval[index];
            s[n][0] = "" + doc_paths.get(index);
            n++;
            dval[index] = new Double(-1.0);
            count++;
            if (count >= dval.length) {
                break;
            }
        }
        return s;
    }

    public String[][] start(String q, String d) throws Exception {
        Vsm v = new Vsm();
        v.get_Documents_From_Directory(d);
        v.get_Query_Doc(q);
        v.cal_Idf();
        v.cal_Tf_Idf_Query();
        v.cal_Tf_Idf_Doc();
        return v.display_Sc();
    }
}

public class Search {

    public String[] start(String s1) throws Exception {
        String s[][] = new Vsm().start(s1, "D:\\t\\OnlineLibrary\\web\\srcdoc");
        for (int n = 0; n < s.length; n++) {
            if (Double.parseDouble(s[n][1]) > 0.1) {
                s[n][1] = "" + 1;
            } else {
                s[n][1] = "" + 0;
            }
        }
        p2 p = new p2();
        return p.start(s1, s);
    }
}