import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;

class asciiTospmfFormat {

  public static void main(String[] args) throws IOException {
    if (args.length < 1) {
      System.out.println("[filename]");
      System.exit(1);
    }
    FileInputStream br = new FileInputStream(args[0]);
    // DataInputStream br = new DataInputStream(new FileInputStream(args[0]));
    FileWriter writer = new FileWriter(args[0] + "aspmf");

    /**
     * cid tid iid no 0
     */
    try {
      int intRead;
      byte[] buf = new byte[4];
      while (br.available() > 0) {
        br.read(buf);
        intRead = convertirOctetEnEntier(buf);
        // intRead = br.readInt();
        if (intRead != -2)
          writer.write(intRead + " ");
        else
          writer.write("-2\n");
      }
    } finally {
      br.close();
      writer.close();
    }
  }
  public static int convertirOctetEnEntier(byte[] b){    
      int MASK = 0xFF;
      int result = 0;   
          result = b[0] & MASK;
          result = result + ((b[1] & MASK) << 8);
          result = result + ((b[2] & MASK) << 16);
          result = result + ((b[3] & MASK) << 24);            
      return result;
  }
}
