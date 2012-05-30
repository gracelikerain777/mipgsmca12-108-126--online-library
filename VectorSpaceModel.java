import java.util.*;
import java.io.*;
public class VectorSpaceModel
{
int no_of_doc;
String query;
ArrayList doc_al=new ArrayList();
ArrayList unique_words=new ArrayList();
ArrayList idf_al=new ArrayList();
ArrayList tf_idf_al=new ArrayList();
ArrayList tf_idf_query=new ArrayList();
ArrayList <Double> similarity_al=new ArrayList<Double>();
ArrayList doc_paths=new ArrayList();
void get_Documents_From_Directory(String dir_name)throws IOException
{
fr f=new fr();
File directory = new File(dir_name);
File[] list_of_files = directory.listFiles();
for (int j = 0; j < list_of_files.length; j++)
{
if(list_of_files[j].isDirectory())
{
get_Documents_From_Directory(list_of_files[j].getAbsolutePath());
}
if(list_of_files[j].isFile())
{
File path=list_of_files[j];
String doc_content = "";
doc_content=(f.readFile(path));
doc_al.add(doc_content);
doc_paths.add(list_of_files[j]);
get_Unique_Words(doc_content);
}
}
}
void get_Unique_Words(String doc)
{
StringTokenizer st = new StringTokenizer(doc);
String word;
while(st.hasMoreTokens())
{
                         word = st.nextToken();
                        if(unique_words.contains(word))
                         {
                             continue;
                         }
                         unique_words.add(word);
                 }
    }
    void get_Query_Doc(String s)
   {
      query=s;
        get_Unique_Words(query);
        no_of_doc=doc_al.size();
    }
    void cal_Idf()
    {
        int total_words,count,term_freq;
        double idf,value;
        total_words=unique_words.size();
        for(int i=0;i<total_words;i++)
        {
            count=0;
            for(int j=0;j<no_of_doc;j++)
            {
                boolean found=word_Check((String)unique_words.get(i),(String)doc_al.get(j));
                if(found)
                {
                    count++;
                }     
            }
            if(count==0)
            {
                idf_al.add(new Double(0));
            }
            else
            {
             value=(double)count/(double)no_of_doc;
             idf=Math.log10(value);
             idf_al.add(idf);   
            }
           }
    }
    boolean word_Check(String word,String doc)
    {
        StringTokenizer st=new StringTokenizer(doc);
        String doc_word;
        while(st.hasMoreTokens())
        {
            doc_word=(String) st.nextToken();
            if(word.equalsIgnoreCase(doc_word))
            {
                return true;
            }
        }
        return false;
    }
    void cal_Tf_Idf_Query()
    {
        int total_words;
        double tf_idf,term_freq,idf;
        total_words=unique_words.size();
        for(int j=0;j<total_words;j++)
            {
                term_freq=(double) cal_Tf((String)unique_words.get(j),query);
                idf = ((Double) idf_al.get(j)).doubleValue();
                tf_idf = term_freq * idf;
                tf_idf_query.add(tf_idf);
            }
    }
    int cal_Tf(String word,String doc)
    {
        StringTokenizer st=new StringTokenizer(doc);
        String doc_word;
        int count=0;
        while(st.hasMoreTokens())
        {
            doc_word=st.nextToken();
            if(word.equalsIgnoreCase(doc_word))
            {
                count++;
            }
        }
        return count;
    }
    void cal_Tf_Idf_Doc()
    {
        int total_words;
        double tf_idf,term_freq,idf,sc,query_tf_idf;
        total_words=unique_words.size();
        for(int i=0;i<no_of_doc;i++)
        {
            sc=0;
            for(int j=0;j<total_words;j++)
            {
                term_freq=(double) cal_Tf((String)unique_words.get(j),(String)doc_al.get(i));
                idf = ((Double) idf_al.get(j)).doubleValue();
                tf_idf = term_freq * idf;
                query_tf_idf = ((Double) tf_idf_query.get(j)).doubleValue();
                sc=sc + ( tf_idf * query_tf_idf);
            }
            similarity_al.add(sc);
        }
    }
    String[][] display_Sc()
    {
        Double [] dval = (Double[]) similarity_al.toArray(new Double[0]);
        String[][] s=new String[dval.length][2];
        int index,count=0,n=0;
          for(int i = 0;;)
            {
            index=i;  
            for(int j =1; j < dval.length; j++)
                {
                if(dval[index] < dval[j])
                    {
                    index=j;
                    }
                }
           s[n][1]=""+dval[index];
           s[n][0]=""+doc_paths.get(index);
             n++;
            dval[index]=new Double(-1.0);
            count++;
             if(count>=dval.length)
                {
                break;
                }
            }
return s;  
}
    public String[][] start(String q,String d)throws Exception
    {
        Vsm v=new Vsm();
         v.get_Documents_From_Directory(d);
         v.get_Query_Doc(q);
         v.cal_Idf();
         v.cal_Tf_Idf_Query();
         v.cal_Tf_Idf_Doc();
        return v.display_Sc();   
                 
    }
public static void main(String[] s1)throws Exception
{
String s[][]=new Vsm().start("World cup","D:/Online Library/Docs");
for(int n=0;n<s.length;n++)
System.out.println(s[n][0]+"----"+s[n][1]); 
for(int n=0;n<s.length;n++)
{
if(Double.parseDouble(s[n][1])>0.1)
s[n][1]=""+1;
else
s[n][1]=""+0;
System.out.println(s[n][0]+"----"+s[n][1]); 
}
Probability p=new Probability();
p.start("World cup",s);
}
}