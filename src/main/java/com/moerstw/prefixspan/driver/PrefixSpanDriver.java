/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// package org.apache.hadoop.examples;
package com.moerstw.prefixspan.driver;

// import org.apache.hadoop.examples.dancing.DistributedPentomino;
// import org.apache.hadoop.examples.dancing.Sudoku;
// import org.apache.hadoop.examples.pi.DistBbp;
// import org.apache.hadoop.examples.terasort.TeraGen;
// import org.apache.hadoop.examples.terasort.TeraSort;
// import org.apache.hadoop.examples.terasort.TeraValidate;
// import org.apache.hadoop.util.ProgramDriver;
import dm.myprefixspan.test.MyPre;
import ca.pfv.spmf.gui.SPMF;
import com.moerstw.prefixspan.PrefixSpan;

/**
 * A description of an example program based on its class and a 
 * human-readable description.
 */
public class PrefixSpanDriver {
  
  public static void main(String argv[]){
    int exitCode = -1;
    ProgramDriver pgd = new ProgramDriver();
    try {
      //pgd.addClass("wordcount", WordCount.class, 
      //             "A map/reduce program that counts the words in the input files.");
      pgd.addClass("prefixspan", PrefixSpan.class, 
                   "A prefixspan implement for input and minsup.");

      pgd.addClass("mypre", MyPre.class, 
                   "(4): file_name min_support(0~1) max_gap(-1) max_pattern_length(-1).");
      pgd.addClass("spmf", SPMF.class, 
                   "(4+): run algoName input output 1)PrefixSpan 2)PrefixSpan_AGP 3)SPAM minsup");
      exitCode = pgd.run(argv);
    }
    catch(Throwable e){
      e.printStackTrace();
    }
    
    System.exit(exitCode);
  }
}
