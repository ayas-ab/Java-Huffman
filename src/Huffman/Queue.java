/*
 * Queue class
 */

package Huffman;

import java.util.ArrayList;

public class Queue {
	// variables
	private ArrayList<BinaryTree<Pair>> trees = new ArrayList<BinaryTree<Pair>>();

	// enqueues a tree into the arraylist of trees
	public void enqueue(BinaryTree<Pair> p) {
		this.trees.add(p);
	}

	
	//removes the tree at the beginning of
	//the list if there is any and returns it
	//if the tree is empty, it just returns null
	public BinaryTree<Pair> dequeue() {
		if (trees.size() == 0) {
			return null;
		}
		BinaryTree<Pair> p = trees.get(0);
		this.trees.remove(0);
		return p;
	}

	//returns a String of enumerated objects
	public String enumerate() {
		String s = "";
		for (int i = 0; i < trees.size(); i++) {
			s += trees.get(i).getData().toString();
		}
		return s;
	}

	//returns the size
	public int getSize() {
		return this.trees.size();
	}

	//return the final huffman tree, used after all
	//operations are done
	public BinaryTree<Pair> getTree() {
		return this.trees.get(0);
	}

}
