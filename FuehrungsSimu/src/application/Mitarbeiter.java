package application;

import java.util.LinkedList;

public class Mitarbeiter {
	private String name; //Kann gebrucht werden
	private LinkedList<Eigenschaft> attri = new LinkedList<Eigenschaft>();
	/*
	 * Andere Attribute k�nnen hier geschrieben werden
	 * private int alt;
	 * private String adresse;
	 */
	
	public Mitarbeiter (String name) {
		super();
		this.name = name;
	}
	
	
	//gibt den Index des gew�nschten Attributs wieder oder halt -1
	public int getIndex(String attribut) {
		
		for(int i=0;i<attri.size();i++) {
			if(this.attri.get(i).getName().equals(attribut))
				return i;
			
		}
		
		return -1;
	}
	
	
	
	public void addWert(int valueIndex, int wert) {
				int ogWert = this.attri.get(valueIndex).getWert();
				this.attri.get(valueIndex).setWert(ogWert+wert);
				
	}
	
	
	public void setAttri(String name, int wert) {
		Eigenschaft neueEigen = new Eigenschaft(name, wert);
		attri.add(neueEigen);
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public LinkedList<Eigenschaft> getEigen(){
		return this.attri;
	}
}
