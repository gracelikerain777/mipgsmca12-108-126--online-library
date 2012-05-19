import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.pdf.PdfImportedPage;
//import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.File;
public class pdf
{
    private static String INPUTFILE = "2.pdf"; //Specifying the file location.
   public static void main(String[] args) {
        try {
           
        PdfReader reader = new PdfReader(INPUTFILE);
        int i=1,n = reader.getNumberOfPages();
for(;i<=n;i++)
{    
  String str=PdfTextExtractor.getTextFromPage(reader, i); //Extracting the content from a particular page.
            System.out.println(str);
 }       //document.close();

        }
        catch (Exception e) {
            System.out.println(e);
        }
       
    }

}