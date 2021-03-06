import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


class spamFormat {

  public static void main(String[] args) throws IOException {
    if (args.length < 1) {
      System.out.println("[filename]");
      System.exit(1);
    }
    BufferedReader br = new BufferedReader(new FileReader(args[0]));
    PrintWriter writer = new PrintWriter("sf" + args[0]);

    /**
     * cid tid iid no 0
     */
    try {
      String line = br.readLine();
      String[] splitLine;
      int cid = 1;
      while (line != null) {
        splitLine = line.split(" ");
        // writer.println(uniqID++ + "\t" + line);
        int counter = 0;
        int numberOfTrans = Integer.parseInt(splitLine[counter++]);
        for (int tid = 0; tid < numberOfTrans; ++tid) {
          int numberOfItems = Integer.parseInt(splitLine[counter++]);
          for (int j = 0; j < numberOfItems; ++j){
            int iid = Integer.valueOf(splitLine[counter++]);
            writer.println(cid + " " + (tid + 1) + " " + iid);
          }
        } // end of for
        cid++;
        line = br.readLine();
      }
    } finally {
      br.close();
      writer.close();
    }
  }
}
