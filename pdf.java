import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.File;
public class pdf
{
    private static String INPUTFILE = "2.pdf";
   public static void main(String[] args)
{
        try {
           
        PdfReader reader = new PdfReader(INPUTFILE);
        int i=1,n = reader.getNumberOfPages();
for(;i<=n;i++)
{    
  String str=PdfTextExtractor.getTextFromPage(reader, i); 
  System.out.println(str);
 }       
        }
        catch (Exception e) {
            System.out.println(e);
        }
       
    }

}