import java.io.*;
public class feop {
 
	public static void main(String[] args) {
 
	  try {
 
		if ((new File("2.mp3")).exists()) {
 
			Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler 2.mp3");
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