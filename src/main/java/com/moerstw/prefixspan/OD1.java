package com.moerstw.prefixspan;

import java.util.*;
import java.io.*;

/**
 * originDB
 */
public class OD1 {
  int minSup;
  /**
   * ArrayList sequential memory acess faster =- for origin
   * LinkList random delete insert =- for psedo?
   * id trans itemset
   */
  public List<List<Integer>> oB; 
  public static int supC;
  public static int numbI;
  public static int[] iMax;
  public static int[] sMax;
  public static List<Integer> level;
  // public static int total;
  public OD1(){

  }
  public OD1(String[] args) throws IOException {
    // oD = new ArrayList<List<Set<Integer>>>();
    // ReadFileMoerstw(fileName);
    // int[] itemCount = new int[Integer.parseInt(args[1])];
    // SequenceCountingMoerstw(args, itemCount);
    oB = new ArrayList<List<Integer>>();
    level = new ArrayList<Integer>();
    supC = Integer.parseInt(args[1]);
    numbI = Integer.parseInt(args[2]);
    this.iMax = new int[numbI];
    this.sMax = new int[numbI];
    Read2(args[0]);
  
  }
  public int Read2(String args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(args));
    /**
     *  read  file and into list
     *  -1 -2
     */
    try {
      String line;
      while ((line = br.readLine()) != null) {
        List<Integer> seq = new ArrayList<Integer>();
        String[] splitLine;
        splitLine = line.split("\t");
        splitLine = splitLine[1].split(" ");
        int counter = 0;
        for (int i = 0; i < splitLine.length - 1; ++i) {
            seq.add(Integer.valueOf(splitLine[counter++]));
        } // end of a line handle
        oB.add(seq);
      } // end of while 
    } finally {
      br.close();
    }
    return oB.size();
  }

  public int Read(String args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(args));
    /**
     *  read  file and into list
     *  -1
     */
    try {
      String line;
      while ((line = br.readLine()) != null) {
        List<Integer> seq = new ArrayList<Integer>();
        String[] splitLine;
        splitLine = line.split("\t");
        splitLine = splitLine[1].split(" ");
        int counter = 0;
        int numberOfTrans = Integer.parseInt(splitLine[counter++]);
        for (int i = 0; i < numberOfTrans; ++i) {
          int numberOfItems = Integer.parseInt(splitLine[counter++]);
          for (int j = 0; j < numberOfItems; ++j){
            seq.add(Integer.valueOf(splitLine[counter++]));
          }
          seq.add(-1);
        } // end of a line handle
        oB.add(seq);
      } // end of while 
    } finally {
      br.close();
    }
    return oB.size();
  } // end of void read file

  public ArrayList<Integer> ScanOneFreItem() {
    // int[] coutedId = new int[this.numbI];
    // int[] maxSup = new int[this.numbI];
    Arrays.fill(this.sMax, 0);
    // int[] notLast = new int[this.numbI];
    // List<aList<Integer>> seq;
    Set<Integer> set = new HashSet<Integer>();
    for (List<Integer> seq : oB) {
      //for (Integer item : seq) {
        // if(item >= 0)
        //  set.add(item);
      // }
      set.addAll(seq.subList(0, seq.size()-1));
      set.remove(-1);
      if(!set.isEmpty()) {
        for (Integer item : set) {
          ++this.sMax[item];
        }
        set.clear();
      }
    }
    ArrayList<Integer> freItem = new ArrayList<Integer>();
    for (int i = 0; i < this.numbI; ++i) {
      if (this.sMax[i] >= this.supC) {
        freItem.add(new Integer(i));
      }
    }
    if (freItem.size() > 0) {
      // this.total += freItem.size();
      this.level.add(freItem.size());
    }
    return freItem;
  }


  public void removeNotFre(List<Integer> freItem) {
    Set<Integer> list = new TreeSet<Integer>(freItem);
    list.add(-1);
    for (List<Integer> seq : oB) {
      seq.retainAll(list);
    }
    int cc = 0;
    /**
     * remove -1 repeat
     */
    List<Integer> seq;
    for (int j = 0; j < oB.size(); ++j) {
      seq = oB.get(j);
      for (int i = 0; i < seq.size() - 1; ++i) {
        if (seq.get(i) == seq.get(i + 1)) {
          // System.out.println(seq.get(i));
          seq.remove(i--);
          ++cc;
        }
      }
      if (seq.get(0) == -1)
        seq.remove(0);
      if (seq.size() <= 1)
        oB.remove(j--);
      // if (seq.size() == 1) {
      // System.out.println("remove seq");
      // oB.remove(seq);
      // }
    }
    System.out.println("delete:" + cc);
  }

  public ProjD1[] projectDB(List<Integer> freItem) {
    int[] hashIndex = new int[this.numbI];
    ProjD1[] pd = new ProjD1[freItem.size()];
    Set<Integer> list = new TreeSet<Integer>(freItem);
    for (int i = 0; i < freItem.size(); ++i) {
      hashIndex[freItem.get(i)] = i;
      pd[i] = new ProjD1();
      pd[i].appI(freItem.get(i));
    }

    /**
     * constructure no nonfreq
     */
    List<Integer> seq;
    List<Integer> insertIndex = new ArrayList<Integer>();
    for (int id = 0; id < oB.size(); ++id) {
      seq = oB.get(id); 
      for (int inSeq = 0; inSeq < seq.size() - 2; ++inSeq) {
        if (seq.get(inSeq) != -1) {
          int j = hashIndex[seq.get(inSeq)];
          if (pd[j].maxID < id) {
            insertIndex.add(j);
            pd[j].maxID = id;
            pd[j].addI(inSeq);
            ++pd[j].sup;
          } else
            pd[j].add(inSeq);
        }
      }
      for (Integer j : insertIndex) {
        pd[j].add(-1);
      }
      insertIndex.clear();
    } // end of sequence
    return pd;
  }


  /**
   * write db content
   */
 // @Override
 // public String toString() {
 //   return "";
 // }


}
