package application;

import java.util.LinkedList;

public class Vorgesetzer {
	
	private String name;// kann gebraucht werden
	private LinkedList<Eigenschaft> attri = new LinkedList<Eigenschaft>();
	/*
	 * Andere Attribute können hier geschrieben werden
	 * private int alt;
	 * private String adresse;
	 */
	
	public Vorgesetzer(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setEigen(String name, int wert) {
		Eigenschaft neueEigen = new Eigenschaft(name, wert);
		attri.add(neueEigen);
	}
	
}
