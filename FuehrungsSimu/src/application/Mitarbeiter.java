package application;

import java.util.LinkedList;

public class Mitarbeiter {
	private String name; //Kann gebrucht werden
	private LinkedList<Eigenschaft> attri = new LinkedList<Eigenschaft>();
	/*
	 * Andere Attribute können hier geschrieben werden
	 * private int alt;
	 * private String adresse;
	 */
	
	public Mitarbeiter (String name) {
		super();
		this.name = name;
	}
	
	
	//gibt den Index des gewünschten Attributs wieder oder halt -1
	public int getIndex(String attribut) {
		
		for(int i=0;i<attri.size();i++) {
			if(this.attri.get(i).getName().equals(attribut))
				return i;
			
		}
		
		return -1;
	}
	
	
	//Hier wird Festgestellt dass, die Attribute von min 0 bix max 100 gehen.
	public void addWert(int valueIndex, int wert) {
				int ogWert = this.attri.get(valueIndex).getWert();
				if((ogWert+wert)>100) {
					this.attri.get(valueIndex).setWert(100);
				}else if ((ogWert+wert)<0) {
					this.attri.get(valueIndex).setWert(0);
				}else {
					this.attri.get(valueIndex).setWert(ogWert+wert);
				}
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
