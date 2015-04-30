import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


class moerstwFormat {

  public static void main(String[] args) throws IOException {
    if (args.length < 1) {
      System.out.println("[filename]");
      System.exit(1);
    }
    BufferedReader br = new BufferedReader(new FileReader(args[0]));
    PrintWriter writer = new PrintWriter(args[0] + ".moerstw");

    try {
      String line = br.readLine();
      String[] splitLine;
      int cid = 0;
      while (line != null) {
        splitLine = line.split(" ");
        writer.printf(cid++ + "\t");
        int counter = 0;
        int numberOfTrans = Integer.parseInt(splitLine[counter++]);
        for (int tid = 0; tid < numberOfTrans; ++tid) {
          int numberOfItems = Integer.parseInt(splitLine[counter++]);
          for (int j = 0; j < numberOfItems; ++j){
            int iid = Integer.valueOf(splitLine[counter++]);
            writer.printf(iid + " ");
          }
          if (tid + 1 == numberOfTrans)  
            writer.printf("-1\n");
          else 
            writer.printf("-1 ");
        } // end of for
        line = br.readLine();
      }
    } finally {
      br.close();
      writer.close();
    }
  }
}
