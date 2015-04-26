package ca.pfv.spmf.input.sequence_database_array_integers;
/* Copyright (c) 2008-2013 Philippe Fournier-Viger
* 
* This file is part of the SPMF DATA MINING SOFTWARE
* (http://www.philippe-fournier-viger.com/spmf).
* 
* SPMF is free software: you can redistribute it and/or modify it under the
* terms of the GNU General Public License as published by the Free Software
* Foundation, either version 3 of the License, or (at your option) any later
* version.
* 
* SPMF is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
* A PARTICULAR PURPOSE. See the GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License along with
* SPMF. If not, see <http://www.gnu.org/licenses/>.
*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a sequence database, where each sequence is implemented
 * as an array of integers and should have a unique id.
 *
 * @see Sequence
 * @author Philipe-Fournier-Viger
 */
public class SequenceDatabase {

	/** smallest item in this database*/
	public int minItem = Integer.MAX_VALUE; 
	/** largest item in this database */
	public int maxItem = 0; 
	/** the number of sequences in this database */
	public int tidsCount =0; 

	/** variable that contains the sequences of this database */
	private final List<Sequence> sequences = new ArrayList<Sequence>();

	/**
	 * Method to load a sequence database from a text file in SPMF format.
	 * @param path  the input file path.
	 * @throws IOException exception if error while reading the file.
	 */
	public void loadFile(String path) throws IOException {
		String thisLine; // variable to read each line.
		BufferedReader myInput = null;
		try {
			FileInputStream fin = new FileInputStream(new File(path));
			myInput = new BufferedReader(new InputStreamReader(fin));
			// for each line until the end of the file
			while ((thisLine = myInput.readLine()) != null) {
				// if the line is not a comment, is not empty or is not other
				// kind of metadata
				if (thisLine.isEmpty() == false &&
						thisLine.charAt(0) != '#' && thisLine.charAt(0) != '%'
						&& thisLine.charAt(0) != '@') {
					// split this line according to spaces and process the line
					addSequence(thisLine.split(" "));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (myInput != null) {
				myInput.close();
			}
		}
	}
	
//	

	/**
	 * Method to process a line from the input file
	 * @param tokens A list of tokens from the line (which were separated by spaces in the original file).
	 */
	public void addSequence(String[] tokens) { 
		// create a new Sequence to store the sequence
		Sequence sequence = new Sequence();
		// create a list of strings for the first itemset.
		List<Integer> itemset = new ArrayList<Integer>();
		
		// for each token in this line
		for (String token : tokens) {
			// if the token start with "<", it indicates a timestamp. 
			// We just ignore it because algorithms that use this class
			// don't need it.
			if (token.codePointAt(0) == '<') { 
				// we just ignore
			} 
			// if the token is -1, it means that we reached the end of an itemset.
			else if (token.equals("-1")) { 
				// add the current itemset to the sequence
				sequence.addItemset(itemset.toArray());
				// create a new itemset
				itemset = new ArrayList<Integer>();
			} 
			// if the token is -2, it means that we reached the end of 
						// the sequence.
			else if (token.equals("-2")) { 
				// we add it to the list of sequences
				sequences.add(sequence);
			} else { 
				// Otherwise it is an item.
				// We parse it as an integer.
				Integer item = Integer.parseInt(token);
				// we update the maximum item for statistics
				if(item >= maxItem){
					maxItem = item;
				}
				// we update the minimum item for statistics
				if(item < minItem){
					minItem = item;
				}
				// we add the item to the current itemset
				itemset.add(item);
			}
		}
//		tidsCount++;
	}

	/**
	 * Method to add a sequence to this sequence database
	 * @param sequence A sequence of type "Sequence".
	 */
	public void addSequence(Sequence sequence) {
		sequences.add(sequence);
	}

	/**
	 * Print this sequence database to System.out.
	 */
	public void print() {
		System.out.println("============  CONTEXTE ==========");
		for (int i=0 ; i < sequences.size(); i++) { // pour chaque objet
			System.out.print(i + ":  ");
			sequences.get(i).print();
			System.out.println("");
		}
	}
	
	/**
	 * Print statistics about this database.
	 */
	public void printDatabaseStats() {
		System.out.println("============  STATS ==========");
		System.out.println("Number of sequences : " + sequences.size());
		System.out.println("Min item:" + minItem);
		System.out.println("Max item:" + maxItem);
		
		// Calculate the average size of sequences in this database
		long size = 0;
		for(Sequence sequence : sequences){
			size += sequence.size();
		}
		double meansize = ((float)size) / ((float)sequences.size());
		System.out.println("mean size" + meansize);
	}
	
	/**
	 * Return a string representation of this sequence database.
	 */
	public String toString() {
		StringBuilder r = new StringBuilder();
		// for each sequence
		for (int i=0 ; i < sequences.size(); i++) { 
			r.append(i);
			r.append(":  ");
			r.append(sequences.get(i).toString());
			r.append('\n');
		}
		return r.toString();
	}
	
	/**
	 * Get the sequence count in this database.
	 * @return the sequence count.
	 */
	public int size() {
		return sequences.size();
	}
	
	/**
	 * Get the sequences from this sequence database.
	 * @return A list of sequences (Sequence).
	 */
	public List<Sequence> getSequences() {
		return sequences;
	}
}
