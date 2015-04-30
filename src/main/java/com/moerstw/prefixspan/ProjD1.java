package com.moerstw.prefixspan;

import java.util.*;

public class ProjD1 {

  public List<Integer> pD;
  public int maxID;
  public List<Integer> pattern;
  public int sup;
  public int sItem;
  public int level;
  // public int iItem;

  public ProjD1 () {
    pD = new ArrayList<Integer>();
    pattern = new ArrayList<Integer>();
    sup = 0;
    maxID = -1;
    level = 1;
    
  }
  public ProjD1 (ProjD1 projDB) {
    pD = new ArrayList<Integer>();
    pattern = new ArrayList<Integer>(projDB.pattern);
    sup = 0;
    maxID = -1;
    level = projDB.level + 1;
  }
  public void addI (int i) {
    add(maxID);
    add(i);
  }

  public void add (int i) {
    pD.add(i);
  }
  public void appS (int i) {
    pattern.add(-1);
    pattern.add(i);
  }
  public void appI (int i) {
    pattern.add(i);
  }
  /**
   * same as oDB
   * scan and project recusive
   */
  public static void recusiveProject (OD1 oD, ProjD1 pDB) {
    List<Integer> freItem = PScanOneFreItem(oD, pDB);
    ProjD1[] ppDB = PprojectDB(oD, pDB, freItem);
    for (int i = 0; i < ppDB.length; ++i) {
      // System.out.println("pseudo pro:");
      // ppDB[i].print();
      // System.out.println(ppDB[i].pattern.toString());
      if (ppDB[i].sup >= oD.supC) 
        ProjD1.recusiveProject(oD, ppDB[i]);
    }
  }

  public static ProjD1[] PprojectDB(OD1 oD, ProjD1 pDB, List<Integer> freItem) {
    Arrays.fill(oD.iMax, -1);
    Arrays.fill(oD.sMax, -1);
    ProjD1[] pd = new ProjD1[freItem.size()];
    Set<Integer> list = new TreeSet<Integer>(freItem);
    for (int i = 0; i < freItem.size(); ++i) {
      if (i < pDB.sItem) {
        pd[i] = new ProjD1(pDB);
        oD.sMax[freItem.get(i)] = i;
        pd[i].appS(freItem.get(i));
      }
      else {
        pd[i] = new ProjD1(pDB);
        oD.iMax[freItem.get(i)] = i;
        pd[i].appI(freItem.get(i));
      }
    }
    List<Integer> seq;
    List<Integer> insertIndex = new ArrayList<Integer>();
    boolean sstep;
    int j, inSeq, id, af;
    List<Integer> proDB = pDB.pD;
    for (inSeq = 0; inSeq < proDB.size() - 1; ++inSeq) {
      id = proDB.get(inSeq++);
      seq = oD.oB.get(id);
      sstep = false;
      for (; proDB.get(inSeq) != -1; ++inSeq) {
        af = proDB.get(inSeq) + 1;
        // while (seq.get(aff) != -1) ++aff;
        /**
         * first istep
         * af -> first -1
         */
        for (;seq.get(af) >= 0 && af < seq.size() - 2; ++af) {
          j = oD.iMax[seq.get(af)];
          if(j < 0) continue;
          if(pd[j].maxID < id) {
            insertIndex.add(j);
            pd[j].maxID = id;
            pd[j].addI(af);
            ++pd[j].sup;
          } else {
            pd[j].add(af);
          }
        } //end istep
        if(sstep)
          continue;
        sstep = true;
        /**
         * sstep
         * aff ->
         * !=-1 & >=0
         */
        ++af;
        for (;af < seq.size() - 2; ++af) {
          if (seq.get(af) < 0) continue;
          j = oD.sMax[seq.get(af)];
          if(j < 0) continue;
          if(pd[j].maxID < id) {
            insertIndex.add(j);
            pd[j].maxID = id;
            pd[j].addI(af);
            ++pd[j].sup;
          } else {
            pd[j].add(af);
          }
        } // end sstep
      } // end seq
      for (Integer jj : insertIndex) {
        pd[jj].add(-1);
      }
      insertIndex.clear();
    } // end for every seq
    return pd;
  }

  public static List<Integer> PScanOneFreItem(OD1 oD, ProjD1 pDB) {
    Arrays.fill(oD.iMax, 0);
    Arrays.fill(oD.sMax, 0);
    Set<Integer> iset = new HashSet<Integer>();
    Set<Integer> sset = new HashSet<Integer>();
    List<Integer> proDB = pDB.pD;
    List<Integer> seq;
    int inSeq, id, af;
    boolean sstep;
    for (inSeq = 0; inSeq < proDB.size() - 1; ++inSeq) {
      id = proDB.get(inSeq++);
      seq = oD.oB.get(id);
      sstep = false;
      for (; proDB.get(inSeq) != -1; ++inSeq) {
        af = proDB.get(inSeq) + 1;
        /**
         * istep
         * find before -1
         */
        for (;seq.get(af) >= 0; ++af) {
          iset.add(seq.get(af));
        } //end istep
        /**
         * s-step only do onece
         * find next index after -1
         */
        if(sstep) continue;
        sstep = true;
        ++af;
        if (af != seq.size()) 
          sset.addAll(seq.subList(af, seq.size() - 1));
        else
          continue;
        sset.remove(-1);
        if(!sset.isEmpty()) {
          for (Integer item : sset) {
            ++oD.sMax[item];
          }
          sset.clear();
        }
      } // end seq
      if(!iset.isEmpty()) {
        for (Integer item : iset) {
          ++oD.iMax[item];
        }
        iset.clear();
      }
    } // end db
    List<Integer> freIS = new ArrayList<Integer>();
    int count = 0;
    for (int i = 0; i < oD.numbI; ++i) {
      if (oD.sMax[i] >= oD.supC) {
        freIS.add(new Integer(i));
        ++count;
      }
    }
    pDB.sItem = count; 
    count = 0;
    for (int i = 0; i < oD.numbI; ++i) {
      if (oD.iMax[i] >= oD.supC) {
        freIS.add(new Integer(i));
        ++count;
      }
    }
    // pDB.iItem = count;
    if (freIS.size() > 0) {
      // oD.total += freIS.size();
      if(pDB.level + 1 > oD.level.size())
        oD.level.add(freIS.size());
      else 
        oD.level.set(pDB.level, oD.level.get(pDB.level) + freIS.size());
    
    }
    return freIS;
  }
  public void print() {
    System.out.println(pattern.toString());
    System.out.println(pD.toString());
  }

}
