package application;

import java.util.LinkedList;

public class Situation {
	
	LinkedList<Fragen> a = new LinkedList<Fragen>();
	LinkedList<Fragen> b = new LinkedList<Fragen>();
	LinkedList<Fragen> c = new LinkedList<Fragen>();
	
	public Situation(String []tabelle) {
		
		 int mitarbeiter= Integer.parseInt(tabelle[2]);
		 String variable = tabelle[3];
		 double deltaFactor = Double.parseDouble(tabelle[4].replace(",", "."));
		 double deltaAbs = Double.parseDouble(tabelle[5].replace(",", "."));
		 int randomizer= Integer.parseInt(tabelle[6]);
		
		if (tabelle[1].contains("A")) {
			a.add(new Fragen(mitarbeiter,variable,deltaFactor,deltaAbs,randomizer));
		}
		else if (tabelle[1].contains("B")) {
			b.add(new Fragen(mitarbeiter,variable,deltaFactor,deltaAbs,randomizer));
		}
		else {
			c.add(new Fragen(mitarbeiter,variable,deltaFactor,deltaAbs,randomizer));
		}
	}
	
	public void addFrage(String [] tabelle) {
		int mitarbeiter= Integer.parseInt(tabelle[2]);
		 String variable = tabelle[3];
		 double deltaFactor = Double.parseDouble(tabelle[4].replace(",", "."));
		 double deltaAbs = Double.parseDouble(tabelle[5].replace(",", "."));
		 int randomizer= Integer.parseInt(tabelle[6]);
		 
		if (tabelle[1].contains("A")) {
			this.a.add(new Fragen(mitarbeiter,variable,deltaFactor,deltaAbs,randomizer));
		}
		else if (tabelle[1].contains("B")) {
			this.b.add(new Fragen(mitarbeiter,variable,deltaFactor,deltaAbs,randomizer));
		}
		else {
			this.c.add(new Fragen(mitarbeiter,variable,deltaFactor,deltaAbs,randomizer));
		}
	}
	
	public LinkedList<Fragen> getA(){
		return a;
	}
	public LinkedList<Fragen> getB(){
		return b;
	}
	public LinkedList<Fragen> getC(){
		return c;
	}
	

}
