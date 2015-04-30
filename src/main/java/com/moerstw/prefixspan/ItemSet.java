package com.moerstw.prefixspan;
import java.io.*;
import java.util.*;


/**
 * N26 = 26000
 * N100 = 100000
 * 16 = 65536
 * 32 = 4294967296
 */
public class ItemSet {
  /**
   * grater
   * so using NavigableSet
   * 1. ConcurrentSkipListSet(no slow than treeset)
   * 2. TreeSet(red-black tree)
   * 3. List(no search slow)
   */
  NavigableSet<Item> itemset;
  public ItemSet() {
    itemset = new TreeSet<Item>();
  }




}
