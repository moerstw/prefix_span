package com.moerstw.prefixspan;

import java.util.*;
import java.io.*;

public class OD2 {
  int minSup;
  /**
   * ArrayList sequential memory acess faster =- for origin
   * LinkList random delete insert =- for psedo?
   * id trans itemset
   */
  public List<List<Set<Integer>>> oD; 
  public List<List<Integer>> oB; 
  public static int supC;
  public static int numbI;
  public OD2(){

  }
  public OD2(String fileName) throws IOException {
    // oD = new ArrayList<List<Set<Integer>>>();
    // ReadFileMoerstw(fileName);
    // int[] itemCount = new int[Integer.parseInt(args[1])];
    // SequenceCountingMoerstw(args, itemCount);
    oB = new ArrayList<List<Integer>>();
    Read2(fileName);
  
  }
  public int Read2(String args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(args));
    /**
     *  read  file and into list
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
        seq.add(-2);
        oB.add(seq);
      } // end of while 
    } finally {
      br.close();
    }
    return oB.size();
  } // end of void read file
  public int ReadFileMoerstw(String args) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(args));
    /**
     *  read  file and into list
     */
    try {
      String line;
      while ((line = br.readLine()) != null) {
        List<Set<Integer>> seq = new ArrayList<Set<Integer>>();
        String[] splitLine;
        splitLine = line.split("\t");
        splitLine = splitLine[1].split(" ");
        int counter = 0;
        int numberOfTrans = Integer.parseInt(splitLine[counter++]);
        for (int i = 0; i < numberOfTrans; ++i) {
          Set<Integer> tempL = new TreeSet<Integer>();
          int numberOfItems = Integer.parseInt(splitLine[counter++]);
          for (int j = 0; j < numberOfItems; ++j){
              tempL.add(Integer.valueOf(splitLine[counter++]));
          }
          seq.add(tempL);
        } // end of a line handle
        oD.add(seq);
      } // end of while 
    } finally {
      br.close();
    }
    return oD.size();
  } // end of void read file

  public ArrayList<Integer> ScanOneFreItem2() {
    // int[] coutedId = new int[this.numbI];
    int[] maxSup = new int[this.numbI];
    // int[] notLast = new int[this.numbI];
    // List<aList<Integer>> seq;
    Set<Integer> set = new HashSet<Integer>();
    for (List<Integer> seq : oB) {
      for (Integer item : seq) {
        if(item >= 0)
          set.add(item);
      }
      for (Integer item : set) {
        ++maxSup[item.intValue()];
      }
      set.clear();
    }
    ArrayList<Integer> freItem = new ArrayList<Integer>();
    for (int i = 0; i < this.numbI; ++i) {
      if (maxSup[i] >= this.supC) {
        freItem.add(new Integer(i));
      }
    }
    return freItem;
  }
  public ArrayList<Integer> ScanOneFreItem() throws IOException {
    // int[] coutedId = new int[this.numbI];
    int[] maxSup = new int[this.numbI];
    // int[] notLast = new int[this.numbI];
    // List<aList<Integer>> seq;
    Set<Integer> set = new TreeSet<Integer>();
    for (List<Set<Integer>> seq : oD) {
      for (Set<Integer> itemset : seq) {
        set.addAll(itemset);
      }
      for (Integer item : set) {
        ++maxSup[item.intValue()];
      }
      set.clear();
    }
    ArrayList<Integer> freItem = new ArrayList<Integer>();
    for (int i = 0; i < this.numbI; ++i) {
      if (maxSup[i] >= this.supC) {
        freItem.add(new Integer(i));
      }
    }
    return freItem;
  }


  public void removeNotFre(List<Integer> freItem) {
    Set<Integer> list = new TreeSet<Integer>(freItem);
    list.add(-1);
    list.add(-2);
    for (List<Integer> seq : oB) {
      seq.retainAll(list);
    }
    
  }


  /**
   * write db content
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (List<Set<Integer>> tSeq : oD) {
      for (Set<Integer> itemSet : tSeq) {
        for (Integer item : itemSet) {
          sb.append(item.toString() + " ");
        }
        sb.append("-1 ");
      }
      sb.append("-2\n");
    }
    return sb.toString();
  }


}
