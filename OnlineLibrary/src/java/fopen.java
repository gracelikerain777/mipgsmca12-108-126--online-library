
import java.io.*;

public class fopen {

    fopen(String s) {
        try {
            if ((new File(s)).exists()) {
                Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + s);
                p.waitFor();
            } else {
                System.out.println("File is not exists");
            }
            System.out.println("Done");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}