package application;

import java.util.LinkedList;

public class Simulator {
	
	Vorgesetzer vorgesetzer;
	LinkedList<Mitarbeiter> worker = new LinkedList<Mitarbeiter>();
	
	public Simulator(String [][] tabelle) {
		
		int indexMitarbeiter = 0;
		for (int i = 1; i < tabelle[0].length; i++) {
			
			if(tabelle[0][i].contentEquals("Vorgesetzter")) {
				this.vorgesetzer = new Vorgesetzer("v1");
				for (int j = 1; j < tabelle.length; j++) {
					vorgesetzer.setEigen(tabelle[j][0], Integer.parseInt(tabelle[j][i]));
				}
			}
			
			else if (tabelle[0][i].contentEquals("Mitarbeiter")) {
				indexMitarbeiter++;
				Mitarbeiter neueMitarbeiter = new Mitarbeiter("M"+indexMitarbeiter);
				for (int j = 1; j < tabelle.length; j++) {
					neueMitarbeiter.setAttri(tabelle[j][0], Integer.parseInt(tabelle[j][i]));
				}
				worker.add(neueMitarbeiter);
			}
		}
	}
	

}
