import java.io.*;
import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hwpf.*;
import org.apache.poi.hwpf.extractor.*;
class fr
{
public static void main(String[] args)
{
fr f=new fr();
String e="";
String s=args[0];
e=s.substring(s.lastIndexOf(".")+1,s.length());
if(e.equalsIgnoreCase("doc"))
System.out.print(f.readDoc(s));
else if(e.equalsIgnoreCase("txt"))
System.out.print("txty");
else if(e.equalsIgnoreCase("pdf"))
System.out.print("pdfy");
else if(e.equalsIgnoreCase("xls"))
System.out.print("xls");
else
System.out.print("Format does not support.");
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
}
catch(Exception e)
{
e.printStackTrace();
}
return s;
}
}