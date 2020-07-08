/*
 * Demo Class
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

import Huffman.Huffman;

public class Demo {

	public static void main(String args[]) throws IOException {
		System.out
				.println("\t\t---Awesome Encoder/Decoder--\n\nLoading poke.txt");
		// making new huffman object
		Huffman huff = new Huffman();
		// displaying tree
		gui g = new gui(huff.getHuffmanTree());

	}

}
