/*
 * Pair class
 */

package Huffman;
public class Pair {

	//instance vars
	private String Character;
	private int Occurance;
	private double Probability;

	//main constructor
	public Pair(String c) {
		this.Character = c;
		this.Occurance = 0;
		this.Probability = 0.0;
	}

	//getters and setters
	public void addOccurance() {
		this.Occurance++;
	}

	public void setProbability(double v) {
		this.Probability = v;
	}

	public String getChar() {
		return this.Character;
	}

	public double getProb() {
		return this.Probability;
	}

	public int getOccur() {
		return this.Occurance;
	}
	
	private void setOcurance(int d){
		this.Occurance = d;
	}
	
	//method that returns a duplicated object
	public Pair duplicate(){
		Pair p = new Pair(Character);
		p.setProbability(this.Probability);
		p.setOcurance(this.Occurance);
		return p;
	}

	public String toString() {
		return "Character: " + this.getChar() + " Occurance: "
				+ this.getOccur() + " Probability: " + this.getProb() + "\n";
	}
}
