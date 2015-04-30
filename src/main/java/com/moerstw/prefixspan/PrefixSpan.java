package com.moerstw.prefixspan;
import java.io.*;
import java.util.*;

/**
 *  1: read file and minsup and item counting
 *  2: remove unfre item and  create projection DBs
 *  3: project database recusive
 *  4: recusive mining
 *  out: 
 *    scan time
 *    creating project db time
 *    total time
 *    level pattern
 *    total
 *    projected datasets
 *    memory
 */
public class PrefixSpan {
  public static void main(String[] args) throws IOException {
    /**
     *  arg[0] file name
     *  arg[1] minsup
     *  arg[2] pattern number(N26 = 26000) hash set
     *  3: max length 4: max itemset 5: etc
     */
    // int minSup = Integer.parseInt(args[1]);
    // int[] itemCount = new int[Integer.parseInt(args[2]));
    /**
     *  read file and construct database and counting and remove
     */
    long totalTime = System.currentTimeMillis();
    long startTime = System.currentTimeMillis();

    OD1 oDB = new OD1(args);
    long endTime = System.currentTimeMillis();
    System.out.println("read file: " + (endTime - startTime) / 1000.0 + "sec");
    // oDB.supC = Integer.parseInt(args[1]);
    // oDB.numbI = Integer.parseInt(args[2]);

    startTime = System.currentTimeMillis();
    List<Integer> freItem = oDB.ScanOneFreItem();
    endTime = System.currentTimeMillis();
    System.out.println("scan 1-fre: " + (endTime - startTime) / 1000.0 + "sec");
    System.out.println("fre 1-item: " + freItem.size());
    startTime = System.currentTimeMillis();
    oDB.removeNotFre(freItem);
    endTime = System.currentTimeMillis();
    System.out.println("remove not fre: " + (endTime - startTime) / 1000.0 + "sec");
    startTime = System.currentTimeMillis();
    ProjD1[] pDB = oDB.projectDB(freItem);
    endTime = System.currentTimeMillis();
    System.out.println("projectOriginDB: " + (endTime - startTime) / 1000.0 + "sec");
    startTime = System.currentTimeMillis();
    for (int i = 0; i < pDB.length; ++i) {
      // pDB[i].print();
      // System.out.println("project i:");
      // System.out.println(pDB[i].pattern.toString());
      if (pDB[i].sup >= oDB.supC)
        ProjD1.recusiveProject(oDB, pDB[i]);
    }
    endTime = System.currentTimeMillis();
    System.out.println("recusiveProject: " + (endTime - startTime) / 1000.0 + "sec");

    int total = 0;
    for (int i = 0; i < oDB.level.size(); ++i) {
      System.out.println("level " + (i + 1) + " : " + oDB.level.get(i));
      total += oDB.level.get(i);
    }
    // System.out.println(oDB.toString());
    System.out.println("totalTime: " + (System.currentTimeMillis() - totalTime) / 1000.0 + "sec");
    System.out.println("total: " + total);
  }
}
