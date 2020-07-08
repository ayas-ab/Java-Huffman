/*
 * Huffman.java
 */

package Huffman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Huffman {

	// Queues
	private Queue S = new Queue();
	private Queue T = new Queue();

	// some instance Variables
	private ArrayList<Pair> Queue = new ArrayList<Pair>();
	private Scanner scan = new Scanner(System.in);
	private String TempString;
	private DecimalFormat df = new DecimalFormat("0.000");
	private String wholetext;

	public Huffman() throws FileNotFoundException {
		// String that will hold the huffman code
		String huffmanCode = "";
		// String that will hold the whole text
		wholetext = "";
		// taking input form the files
		Scanner inputFile = new Scanner(new File("text_to_encode.txt"));
		while (inputFile.hasNextLine()) {

			String line = inputFile.nextLine();
			wholetext += line + "\n";
			// recursive method for adding Queue to the arrayList.
			addChars(line, line.length() - 1);

		}
		inputFile.close();
		// getting the total number of Queue in the file.
		int totalCharNum = findNumChars(Queue.size() - 1);

		// finding the probability with recursive method.
		findProbability(Queue.size() - 1, totalCharNum);

		// sorts the array of pairs
		Sort();

		// enqueues the pairs into the Queue S
		this.enQueueS(Queue.size() - 1);

		// getting the enumeration of the S queue before we deQueue it
		// into T
		huffmanCode += S.enumerate();

		// printing number of chars in the textfile
		System.out.println("\nTotal chars in the file: " + totalCharNum);

		// building the huffman tree
		BuildHuffman();

		// finding the encodings
		String[] codes = this.findEncoding(this.T.getTree());
		for (int i = 0; i < codes.length; i++) {
			if (codes[i] != null)
				huffmanCode += (char) i + " = " + codes[i] + "\n";
		}

		// encoding poke.txt
		String encoded = wholetext;
		for (int i = 0; i < this.wholetext.length(); i++) {
			if (codes[wholetext.charAt(i)] != null) {

				encoded = encoded.replace(wholetext.charAt(i) + "",
						codes[wholetext.charAt(i)]);
			}
		}

		// writing the encoded poke.txt into encoded_file.txt
		this.WriteFile(encoded, "encoded_file.txt");

		String decoded = "";

		// loading encoded file and decoding it
		inputFile = new Scanner(new File("encoded_file.txt"));
		while (inputFile.hasNextLine()) {

			String[] line = inputFile.nextLine().split(" ");
			for (int i = 0; i < line.length; i++) {
				decoded += this.decode(line[i], codes) + " ";
			}
			decoded += "\n";

		}
		inputFile.close();

		// writing the decoded encoded file into decoded_file.txt
		this.WriteFile(decoded, "decoded_file.txt");
		// writing huffman.txt
		this.WriteFile(huffmanCode, "huffman.txt");

		// printing result
		System.out.println("HuffmanCode:\n" + huffmanCode + "Whole Text :\n"
				+ this.wholetext + "\n\nEncoded: " + "\n" + encoded
				+ "\nDecoded:\n" + decoded);

	}

	// decode method, accepts a Stirng and decodes it and returns it back
	public String decode(String encoded, String[] codes) {
		String temp = "", CharAt, decoded = "";

		for (int i = 0; i < encoded.length(); i++) {
			temp += encoded.charAt(i);
			for (int i2 = 0; i2 < codes.length; i2++) {

				if (codes[i2] != null) {
					if (codes[i2].replaceAll(" ", "").equals(
							temp.replace(" ", ""))) {
						decoded += (char) i2 + "";
						temp = "";
					}
				}
			}

		}

		return decoded;
	}

	// A method that writes files
	public void WriteFile(String text, String filename) {
		try {
			File logFile = new File(filename);
			BufferedWriter writer = new BufferedWriter(new FileWriter(logFile));
			String[] lines = new String[] { text };
			for (int i = 0; i < lines.length; i++) {
				writer.write(text);
				writer.newLine();
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// a method that encodes files, taken from handout
	public static void findEncoding(BinaryTree<Pair> t, String[] a,
			String prefix) {
		if (t.getLeft() == null && t.getRight() == null) {
			// System.out.println((t.getData().getChar().charAt(0)));
			a[(byte) (t.getData().getChar().charAt(0))] = prefix;
		} else {
			findEncoding(t.getLeft(), a, prefix + "0");
			findEncoding(t.getRight(), a, prefix + "1");
		}

	}

	// a method that encodes files, taken from handout
	public static String[] findEncoding(BinaryTree<Pair> t) {
		String[] result = new String[256];
		findEncoding(t, result, "");
		return result;
	}

	// Building huffman tree, 2 steps
	public void BuildHuffman() {
		BuildHuffmanStep1();
		BuildHuffmanStep2();
	}

	// Step one of building the tree.
	// This step takes the ordered trees in Queue S
	// and pairs them up in two by two in a new node
	// then enqueues it into T
	// if the one node is left, then it will just enqueue it
	public void BuildHuffmanStep1() {
		BinaryTree<Pair> p = new BinaryTree<Pair>();
		Pair newP;
		double prob;
		BinaryTree<Pair> p1;
		if (S.getSize() > 1) {
			p1 = S.dequeue();
			BinaryTree<Pair> p2 = S.dequeue();
			if (p1 != null && p2 != null) {
				p.attachLeft(p1);
				p.attachRight(p2);
				prob = (p1.getData().getProb() + p2.getData().getProb());
				newP = new Pair("0");
				newP.setProbability(prob);
				p.setData(newP);
				T.enqueue(p);
				BuildHuffmanStep1();

			}

		} else if (S.getSize() == 1) {
			p1 = S.dequeue();
			newP = new Pair(p1.getData().getChar() + "");
			newP.setProbability(p1.getData().getProb());
			p.setData(newP);
			T.enqueue(p);

		}

	}

	// huffman step 2
	// this method will deal with the Queue T
	// it will dequeue nodes 2 by 2
	// and pair them up into a new node
	// then enqueue them to T again, keeps
	// going till 1 node is left, which is
	// the final huffman tree
	public void BuildHuffmanStep2() {
		BinaryTree<Pair> p = new BinaryTree<Pair>();
		Pair newP;
		double prob;
		BinaryTree<Pair> p1;
		if (T.getSize() > 1) {
			p1 = T.dequeue();
			BinaryTree<Pair> p2 = T.dequeue();
			if (p1 != null && p2 != null) {
				p.attachLeft(p1);
				p.attachRight(p2);
				prob = (p1.getData().getProb() + p2.getData().getProb());
				newP = new Pair("0");
				newP.setProbability(prob);
				p.setData(newP);
				T.enqueue(p);
				BuildHuffmanStep2();
			}

		}

	}

	// recursive method that finds the number of characters in an array list
	public int findNumChars(int size) {
		if (size > -1) {

			return Queue.get(size).getOccur() + findNumChars(size - 1);
		} else
			return 0;

	}

	// recursive method that adds all probabilities together and returns value
	public double CountProb(int size) {
		if (size > -1) {

			return Queue.get(size).getProb() + CountProb(size - 1);
		} else
			return 0.0;

	}

	// recursive method that finds and sets the probability for
	// each item in the arrayList
	public void findProbability(int size, int totalCharNum) {
		if (size > -1) {
			Queue.get(size).setProbability(
					Double.parseDouble(df
							.format((Queue.get(size).getOccur() * 1.0)
									/ (totalCharNum * 1.0))));
			findProbability(size - 1, totalCharNum);
		}
	}

	// recursive method that adds chars to the arrayList.
	// and increments the charatcer's occurance.
	public void addChars(String line, int at) {

		if (at > -1) {
			if (line.charAt(at) != ' '
					&& ArrayContains(line.charAt(at) + "", Queue.size() - 1) == 0) {

				Queue.add(new Pair(line.charAt(at) + ""));
				getPair(line.charAt(at) + "").addOccurance();

			}

			addChars(line, at - 1);
		}

	}

	// recursive method that returns 1 if a character is already in
	// the array list
	// uppercase letter and a lower case letter of same type do not count as the
	// same
	// if it finds that the character occurs, it will increment its occurance
	// by one and exit.
	public int ArrayContains(String Char, int exit) {
		if (exit > -1) {
			// checking uppercases
			TempString = Queue.get(exit).getChar();
			if (!(TempString.equals(TempString.toLowerCase()))) {
				TempString = TempString.toUpperCase();
			}

			if (TempString.equals(Char)) {
				getPair(TempString).addOccurance();
				return 1 + ArrayContains(Char, -1);
			} else {
				return ArrayContains(Char, exit - 1);
			}
		}
		return 0;
	}

	// method that gets a object form the arraylist
	// with the same letter
	public Pair getPair(String c) {

		if (!(c.equals(c.toLowerCase()))) {
			c = c.toUpperCase();
		}
		for (int i = 0; i < Queue.size(); i++) {
			if (Queue.get(i).getChar().equals(c)) {
				return Queue.get(i);
			}
		}
		return null;
	}

	// method that sorts an arrayList of pairs
	public void Sort() {

		for (int pass = 1; pass < Queue.size(); pass++) {
			for (int i = 0; i < Queue.size() - 1; i++) {
				if (Queue.get(i).getProb() > Queue.get(i + 1).getProb()) {
					Pair tempPlusone = Queue.get(i + 1).duplicate();
					Queue.set(i + 1, Queue.get(i).duplicate());
					Queue.set(i, tempPlusone);
				}
			}
		}
	}

	// method that enqueues an arrayList of pairs to queue S
	public void enQueueS(int size) {
		if (size > -1) {
			BinaryTree<Pair> p = new BinaryTree<Pair>();
			p.makeRoot(Queue.get(0));
			Queue.remove(0);
			this.S.enqueue(p);
			enQueueS(size - 1);
		}
	}

	// returns the huffman queue

	public Queue getHuffmanTree() {
		return this.T;
	}

}
