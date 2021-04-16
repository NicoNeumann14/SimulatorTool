package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class SampleController implements Initializable {
	
		
    	@FXML
	    private BarChart<String, Number> barChart;

	    @FXML
	    private CategoryAxis caAxis;

	    @FXML
	    private NumberAxis nAxis;
	    
	    @FXML
	    private TextField txfRunde;
	    
	    private Simulator s;
	  
	    private LinkedList<Situation> situ;
	    
	    private Alert info;
    	
	    
	    @FXML
	    void loadMitarb(ActionEvent event) {
	    	//Mitarbeiter Tabelle einlesen.
	    	FileChooser fileChooser = new FileChooser();
        	File selectedDirectory = fileChooser.showOpenDialog(null);
        	DatenVerarbeiten(selectedDirectory);
        
        	barChart.getData().clear();
        	for(int i=0; i<s.worker.size(); i++) {
        		addBarCharts(s.worker.get(i), barChart);
        		
        	} 
        }

	    @FXML
	    void runSim(ActionEvent event) {
	    	//Rundenbasierte Simulation der einzelden Situationen
	    	
	    	int runden = Integer.parseInt(txfRunde.getText());
	    	Fragen kons;
	    	
	    	//Konsequenzen durchführen pro Runde
	    	for(int i=0;i<runden;i++) {
	    		
	    		//Anzahl Situationen (Fragen)
	    		for(int k=0;k<situ.size();k++) {
	    			
	    			//Auswahl representiert aktuell die gewählte HandlungsOption
	    			int auswahl = 0;
	    			
	    			switch(auswahl){
	    			case 0:
	    				for(int p=0;p<situ.get(k).getA().size();p++) {
		    				kons = situ.get(k).getA().get(p);
		    				
		    				//index für das Richtige Attribut
		    				int indexValue = s.worker.get(kons.getMitarbeiter()-1).getIndex(kons.getVar());    				
		    				
		    				if(indexValue == -1) {
		    					info = new Alert(AlertType.INFORMATION);
		    					info.setContentText("Mitarbeiter " + + kons.getMitarbeiter() + ", besitzt kein Attribut "+ kons.getVar() + ". \nDie Konsequenz wurde Uebersprungen" );
		    			        info.show();
		    					continue;
		    				}
		    				
		    				double wert = s.worker.get(kons.getMitarbeiter()-1).getEigen().get(indexValue).getWert();
		    				double delta = 0;
		    				double deltaR = 0;
		    				double random = kons.getRan();
		    				
		    				//Entscheidung ob DeltaFac oder DeltaAbs benutzt wird
		    				if(kons.getDeltaFac() != 0) {
		    					
		    					//Unterscheiden ob DeltaFac Summiert oder Reduziert
		    					if(kons.getDeltaFac()>1) {
		    						
		    						delta = ((kons.getDeltaFac()-1)*wert)*((100-random)/100);
		    						deltaR = ((kons.getDeltaFac()-1)*wert)*(random/100);
		    					
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)delta);
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)(Math.random()*(deltaR+1)));
		    					}else {
		    						delta = ((wert -(wert * kons.getDeltaFac()))*((100-random)/100))*(-1);
		    						deltaR = (wert - (wert * kons.getDeltaFac()))*(random/100);
		    						
		    						
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)delta);
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue, ((int)(Math.random()*(deltaR+1)))*(-1));
		    					}
		    					
		    				}else if(kons.getDeltaAbs () != 0) {
		    					
		    					//Unterscheide ob DeltaAbs Summiert oder Reduziert
		    					if(kons.getDeltaAbs()>1) {
		    						delta = kons.getDeltaAbs()*((100-random)/100);
		    						deltaR = kons.getDeltaAbs()*(random/100);
		    					
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)delta);
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)(Math.random()*(deltaR+1)));
		    					}else {
		    						delta = kons.getDeltaAbs()*((100-random)/100);
		    						deltaR = (kons.getDeltaAbs()*(random/100))*(-1);
		    						
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)delta);
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue,((int)(Math.random()*(deltaR+1))*(-1)));
		    						
		    					}
		    				}
		    				
		    				
		    			}
	    				break;
	    				
	    			case 1:
	    				for(int p=0;p<situ.get(k).getB().size();p++) {
		    				kons = situ.get(k).getB().get(p);
		    				
		    				//index für das Richtige Attribut
		    				int indexValue = s.worker.get(kons.getMitarbeiter()-1).getIndex(kons.getVar());    				
		    				
		    				if(indexValue == -1) {
		    					info = new Alert(AlertType.INFORMATION);
		    					info.setContentText("Mitarbeiter " + + kons.getMitarbeiter() + ", besitzt kein Attribut "+ kons.getVar() + ". \nDie Konsequenz wurde Uebersprungen" );
		    			        info.show();
		    					continue;
		    				}
		    				
		    				double wert = s.worker.get(kons.getMitarbeiter()-1).getEigen().get(indexValue).getWert();
		    				double delta = 0;
		    				double deltaR = 0;
		    				double random = kons.getRan();
		    				
		    				//Entscheidung ob DeltaFac oder DeltaAbs benutzt wird
		    				if(kons.getDeltaFac() != 0) {
		    					
		    					//Unterscheiden ob DeltaFac Summiert oder Reduziert
		    					if(kons.getDeltaFac()>1) {
		    						
		    						delta = ((kons.getDeltaFac()-1)*wert)*((100-random)/100);
		    						deltaR = ((kons.getDeltaFac()-1)*wert)*(random/100);
		    					
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)delta);
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)(Math.random()*(deltaR+1)));
		    					}else {
		    						delta = ((wert -(wert * kons.getDeltaFac()))*((100-random)/100))*(-1);
		    						deltaR = (wert - (wert * kons.getDeltaFac()))*(random/100);
		    						
		    						
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)delta);
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue, ((int)(Math.random()*(deltaR+1)))*(-1));
		    					}
		    					
		    				}else if(kons.getDeltaAbs () != 0) {
		    					
		    					//Unterscheide ob DeltaAbs Summiert oder Reduziert
		    					if(kons.getDeltaAbs()>1) {
		    						delta = kons.getDeltaAbs()*((100-random)/100);
		    						deltaR = kons.getDeltaAbs()*(random/100);
		    					
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)delta);
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)(Math.random()*(deltaR+1)));
		    					}else {
		    						delta = kons.getDeltaAbs()*((100-random)/100);
		    						deltaR = (kons.getDeltaAbs()*(random/100))*(-1);
		    						
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)delta);
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue,((int)(Math.random()*(deltaR+1))*(-1)));
		    						
		    					}
		    				}
		    				
		    				
		    			}
	    				break;
	    				
	    			case 2:
	    				for(int p=0;p<situ.get(k).getC().size();p++) {
		    				kons = situ.get(k).getC().get(p);
		    				
		    				//index für das Richtige Attribut
		    				int indexValue = s.worker.get(kons.getMitarbeiter()-1).getIndex(kons.getVar());    				
		    				
		    				if(indexValue == -1) {
		    					info = new Alert(AlertType.INFORMATION);
		    					info.setContentText("Mitarbeiter " + + kons.getMitarbeiter() + ", besitzt kein Attribut "+ kons.getVar() + ". \nDie Konsequenz wurde Uebersprungen" );
		    			        info.show();
		    					continue;
		    				}
		    				
		    				double wert = s.worker.get(kons.getMitarbeiter()-1).getEigen().get(indexValue).getWert();
		    				double delta = 0;
		    				double deltaR = 0;
		    				double random = kons.getRan();
		    				
		    				//Entscheidung ob DeltaFac oder DeltaAbs benutzt wird
		    				if(kons.getDeltaFac() != 0) {
		    					
		    					//Unterscheiden ob DeltaFac Summiert oder Reduziert
		    					if(kons.getDeltaFac()>1) {
		    						
		    						delta = ((kons.getDeltaFac()-1)*wert)*((100-random)/100);
		    						deltaR = ((kons.getDeltaFac()-1)*wert)*(random/100);
		    					
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)delta);
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)(Math.random()*(deltaR+1)));
		    					}else {
		    						delta = ((wert -(wert * kons.getDeltaFac()))*((100-random)/100))*(-1);
		    						deltaR = (wert - (wert * kons.getDeltaFac()))*(random/100);
		    						
		    						
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)delta);
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue, ((int)(Math.random()*(deltaR+1)))*(-1));
		    					}
		    					
		    				}else if(kons.getDeltaAbs () != 0) {
		    					
		    					//Unterscheide ob DeltaAbs Summiert oder Reduziert
		    					if(kons.getDeltaAbs()>1) {
		    						delta = kons.getDeltaAbs()*((100-random)/100);
		    						deltaR = kons.getDeltaAbs()*(random/100);
		    					
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)delta);
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)(Math.random()*(deltaR+1)));
		    					}else {
		    						delta = kons.getDeltaAbs()*((100-random)/100);
		    						deltaR = (kons.getDeltaAbs()*(random/100))*(-1);
		    						
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)delta);
		    						s.worker.get(kons.getMitarbeiter()-1).addWert(indexValue,((int)(Math.random()*(deltaR+1))*(-1)));
		    						
		    					}
		    				}
		    				
		    				
		    			}
	    				break;
	    				
	    			}
	    			
	    		}
	    		
	    		//Attribute der Mitarbeiter in die BarChart Laden
	    		barChart.getData().clear();
	        	for(int u=0; u<s.worker.size(); u++) {
	        		addBarCharts(s.worker.get(u), barChart);
	        		
	        	} 
	    	}
	    		
	    }
	    
	   @Override
	    public void initialize (URL url, ResourceBundle rb) {
	    	//Hier kann der BarChart schon beim Programmstart mit Daten initialisiert werden
	    	
	   }
	    
	    
	    public void addBarCharts(Mitarbeiter m, BarChart<String, Number> barChart) {
	    	XYChart.Series<String, Number> series = new XYChart.Series<>();
	    	series.setName(m.getName());
	    	for(int i=0;i<m.getEigen().size();i++) {
	    		series.getData().add(new XYChart.Data<>(m.getEigen().get(i).getName(),m.getEigen().get(i).getWert()));
	    	}
	    	barChart.getData().add(series);
    		
    	}
	    
	   
	    
	    @FXML
	    void loadSitu(ActionEvent event) {
	    	//Situationen aus Tabelle laden
	    	FileChooser fileChooser = new FileChooser();
        	File selectedDirectory = fileChooser.showOpenDialog(null);
        	DatenVerarbeiten(selectedDirectory);
        	 
	    }
	    
	    private void DatenVerarbeiten(File selectedDirectory) {
	    	info = new Alert(AlertType.ERROR);
	    	int zeile=0;
			int spalte=0;
			String text =null; 
			File file = new File(selectedDirectory.getPath());
			Scanner fileReader = null;
			try {
				fileReader = new Scanner(file);
			} catch (FileNotFoundException e) {
				info.setContentText("Datei konnte nicht gefunden werden.");
				info.show();
			}
			
			try {
				text = fileReader.nextLine();
				String [] arr =text.split(";");
				spalte=arr.length;
				fileReader = new Scanner(file);
				while (fileReader.nextLine()!=null) {
					zeile++;
				}
			} catch (Exception e) {
			}
			
			String [][] tabelle = new String[zeile][spalte];
			
			try {
				fileReader = new Scanner(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < zeile; i++) {
				text = fileReader.nextLine();
				for (int j = 0; j < spalte; j++) {
					
					String [] arr =text.split(";");
					tabelle[i][j] = arr[j];
				}
			}
			if (tabelle[0][0].contains("Frage")) {
				situ = new LinkedList<Situation>();
				for (int i = 1; i < tabelle.length; i++) {
					int index =Integer.parseInt(tabelle[i][0])-1;
					if (situ.isEmpty()) {
						situ.add(new Situation(tabelle[i]));
					}
					else {
						if (situ.get(index)==null) {
							situ.set(index, new Situation(tabelle[i]));
						}
						else {
							situ.get(index).addFrage(tabelle[i]);
						}
					}
				}
			}
			else {
				s = new Simulator(tabelle);
				
			}
			
		}

}