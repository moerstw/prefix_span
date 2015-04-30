import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.URI;
public class sequenceAddID2 {

  public static void main(String[] args) throws IOException {
    if (args.length < 1) {
      System.out.println("[filename]");
      System.exit(1);
    }
    int k = 0;
    // BufferedReader br = new BufferedReader(new FileReader("/home/moerstw/gi/java_test/sequenceAddID/D10C10T8N26ascii.data"));
    BufferedReader br = new BufferedReader(new FileReader((args[0])));
    // PrintWriter writer = new PrintWriter("I" + args[0] + k++);
    PrintWriter writer = new PrintWriter(args[0]+"S");
    try {
      // StringBuilder sb = new StringBuilder();
      String line = br.readLine();
      int uniqID = 0;
      while (line != null) {
        // sb.append(line);
        // sb.append(System.lineSeparator());
        writer.println(uniqID++ + "\t" + line);
        line = br.readLine();
        // if(uniqID % 1000 == 0) {
        //   writer.close();
        //  writer = new PrintWriter("I" + args[0] + k++);
        // }
      }
      // String everything = sb.toString();
    } finally {
      br.close();
      writer.close();
    }
  }
}
