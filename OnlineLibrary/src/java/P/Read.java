/*
    Document   : Read.java
    Created on : Jun 6, 2012, 3:17:45 AM
    Author     : Sathish
*/
package P;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.util.*;
import org.apache.poi.hssf.usermodel.*;
import java.io.*;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hwpf.*;
import org.apache.poi.hwpf.extractor.*;
public class Read
{
public String readFile(String s)
{
Read f=new Read();
String e="";
e=s.substring(s.lastIndexOf(".")+1,s.length());
if(e.equalsIgnoreCase("doc"))
return f.readDoc(s);
else if(e.equalsIgnoreCase("txt"))
return f.readTxt(s);
else if(e.equalsIgnoreCase("pdf"))
return f.readPdf(s);
else if(e.equalsIgnoreCase("xls"))
return f.readXl(s);
else if(e.equalsIgnoreCase("html"))
return f.readHtml(s);
else
return "Format does not support.";
}
public String readDoc(String s)
{
POIFSFileSystem fs = null;
try
{
fs = new POIFSFileSystem(new FileInputStream(s));
HWPFDocument doc = new HWPFDocument(fs);
WordExtractor we = new WordExtractor(doc);
s=we.getText();
s=cleanText(s);
}
catch(Exception e)
{
e.printStackTrace();
}
return s;
}

public String readXl(String s)
{
Vector dataHolder = ReadCSV(s);
return printCellDataToConsole(dataHolder);
}
public static Vector ReadCSV(String s)
{
Vector cellVectorHolder = new Vector();
try
{
FileInputStream myInput = new FileInputStream(s);
POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
HSSFSheet mySheet = myWorkBook.getSheetAt(0);
Iterator rowIter = mySheet.rowIterator();
while (rowIter.hasNext())
{
HSSFRow myRow = (HSSFRow) rowIter.next();
Iterator cellIter = myRow.cellIterator();
Vector cellStoreVector = new Vector();
while (cellIter.hasNext())
{
HSSFCell myCell = (HSSFCell) cellIter.next();
cellStoreVector.addElement(myCell);
}
cellVectorHolder.addElement(cellStoreVector);
}
}
catch (Exception e)
{
e.printStackTrace();
}
return cellVectorHolder;
}
private String printCellDataToConsole(Vector dataHolder)
{
String s="";
for (int i = 0; i < dataHolder.size(); i++)
{
Vector cellStoreVector = (Vector) dataHolder.elementAt(i);
for (int j = 0; j < cellStoreVector.size(); j++)
{
HSSFCell myCell = (HSSFCell) cellStoreVector.elementAt(j);
s+=myCell.toString();
}
s+="\n";                
s=cleanText(s);
}
return s;       
}
public String readPdf(String s1)
{
String s="";
try
{
PdfReader reader = new PdfReader(s1);
int i=1,n = reader.getNumberOfPages();
for(;i<=n;i++)
{    
s+=PdfTextExtractor.getTextFromPage(reader, i);
s=cleanText(s);
}
}
catch (Exception e)
{
System.out.println(e);
}
return s;
}
public String readTxt(String s)
{
String s1="";
int i,n;
InputStream f;
try
{
f=new FileInputStream(s);
n=f.available();
for(i=1;i<=n;i++)
s1+=(char)f.read();
s1=cleanText(s1);
f.close();
}
catch(Exception e)
{
System.out.print("\nFile not found in specified location.");
}
return s1;
}
public String cleanText(String s)
{
int n,i=0,r;
String s1="";
n=s.length();
for(;i<n;i++)
{
r=(int)s.charAt(i);
if(!((r>='A'&r<='Z')|(r>='a'&r<='z')|(r>='0'&r<='9')))
s1+=s.charAt(i);
}
StringTokenizer st =new StringTokenizer(s,s1);
s="";
while(st.hasMoreTokens())
s+=" "+st.nextToken();
return s;
}
public String readHtml(String s)
{
try
{
InputStream f=new FileInputStream(s);
char c;
s="";
int i=0,n=f.available();
for(i=1;i<=n;i++)
{
c=(char)f.read();
if(c=='<')
do
{
c=(char)f.read();
}while(c!='>');
if(c!='>')
s+=c;
}
f.close();
s=cleanText(s);
}
catch(Exception e)
{
e.printStackTrace();
}
return s;
}
}