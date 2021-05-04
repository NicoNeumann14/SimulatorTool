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
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

public class SampleController implements Initializable {
	
		@FXML
		private GridPane gridTable;
	
    	@FXML
	    private BarChart<String, Number> barChart;

	    @FXML
	    private CategoryAxis caAxis;

	    @FXML
	    private NumberAxis nAxis;
	    
	    @FXML
	    private TextField txfRunde;
	   
	    @FXML
	    private ChoiceBox<String> chBoxTeil;

	    @FXML
	    private Label lbRunden, lbAnzeige, lbTSp;
	    
	    //private LinkedList<Label> labels = new LinkedList<Label>();
	    private Label[][] labels = new Label[7][6];
	    private Simulator s, copyS;
	    private boolean sIsfull = false;
	    
	    private LinkedList<Situation> situ;
	    private boolean sitIs = false;
	    
	    private LinkedList<Simulator> teilNehm;
	    private boolean teilIsfull = false;
	    private String [][] mitarbeiter;
	    
	    
	    private Alert info;
    	
	  

	    @FXML
	    void runSim(ActionEvent event) {
	    	//Rundenbasierte Simulation der einzelden Situationen
	    	
	    	
	    	if(sIsfull && sitIs && teilIsfull) {
	    	
	    	int runden = Integer.parseInt(txfRunde.getText());
	    	int zahl = Integer.parseInt(lbRunden.getText().trim());
	    	zahl += runden;
	    	lbRunden.setText(""+zahl);
	    	Fragen kons;
	    	
	    	//Anzahl an Spielern die Simuliert werden
	    	for(int t=0;t<teilNehm.size();t++) {
	    		
	    		if(situ.size() > teilNehm.get(0).auswahl.size()) {
	    			info.setHeaderText("Zuwenig Auswahl möglichkeiten bei zuvielen Situationen");
	    			info.show();
	    			return;
	    		}
	    	
	        //Konsequenzen durchführen pro Runde
	    	for(int i=0;i<runden;i++) {
	    		
	    		//Anzahl Situationen (Fragen)
	    		for(int k=0;k<situ.size();k++) {
	    			
	    			//Auswahl repräsentiert die HandlungsOption
	    			int auswahl = 0;
	    			String choose = teilNehm.get(t).auswahl.get(k);
	    			if(choose.equals("B")) {
	    				auswahl = 1;
	    			}else if(choose.equals("C")) {
	    				auswahl = 2;
	    			}
	    			
	    			
	    			
	    			switch(auswahl){
	    			case 0:
	    				for(int p=0;p<situ.get(k).getA().size();p++) {
		    				kons = situ.get(k).getA().get(p);
		    				
		    				//index für das Richtige Attribut
		    				int indexValue = teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).getIndex(kons.getVar());    				
		    				
		    				if(indexValue == -1) {
		    					info = new Alert(AlertType.INFORMATION);
		    					info.setContentText("Mitarbeiter "  + kons.getMitarbeiter() + ", besitzt kein Attribut "+ kons.getVar() + ". \nDie Konsequenz wurde Uebersprungen" );
		    			        info.show();
		    					continue;
		    				}
		    				
		    				double wert = teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).getEigen().get(indexValue).getWert();
		    				double delta = 0;
		    				double deltaR = 0;
		    				double random = kons.getRan();
		    				
		    				//Entscheidung ob DeltaFac oder DeltaAbs benutzt wird
		    				if(kons.getDeltaFac() != 0) {
		    					
		    					//Unterscheiden ob DeltaFac Summiert oder Reduziert
		    					if(kons.getDeltaFac()>1) {
		    						
		    						delta = ((kons.getDeltaFac()-1)*wert)*((100-random)/100);
		    						deltaR = ((kons.getDeltaFac()-1)*wert)*(random/100);
		    					
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)delta);
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)(Math.random()*(deltaR+1)));
		    					}else {
		    						delta = ((wert -(wert * kons.getDeltaFac()))*((100-random)/100))*(-1);
		    						deltaR = (wert - (wert * kons.getDeltaFac()))*(random/100);
		    						
		    						
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)delta);
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue, ((int)(Math.random()*(deltaR+1)))*(-1));
		    					}
		    					
		    				}else if(kons.getDeltaAbs () != 0) {
		    					
		    					//Unterscheide ob DeltaAbs Summiert oder Reduziert
		    					if(kons.getDeltaAbs()>1) {
		    						delta = kons.getDeltaAbs()*((100-random)/100);
		    						deltaR = kons.getDeltaAbs()*(random/100);
		    					
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)delta);
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)(Math.random()*(deltaR+1)));
		    					}else {
		    						delta = kons.getDeltaAbs()*((100-random)/100);
		    						deltaR = (kons.getDeltaAbs()*(random/100))*(-1);
		    						
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)delta);
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue,((int)(Math.random()*(deltaR+1))*(-1)));
		    						
		    					}
		    				}
		    				
		    				
		    			}
	    				break;
	    				
	    			case 1:
	    				for(int p=0;p<situ.get(k).getB().size();p++) {
		    				kons = situ.get(k).getB().get(p);
		    				
		    				//index für das Richtige Attribut
		    				int indexValue = teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).getIndex(kons.getVar());    				
		    				
		    				if(indexValue == -1) {
		    					info = new Alert(AlertType.INFORMATION);
		    					info.setContentText("Mitarbeiter "  + kons.getMitarbeiter() + ", besitzt kein Attribut "+ kons.getVar() + ". \nDie Konsequenz wurde Uebersprungen" );
		    			        info.show();
		    					continue;
		    				}
		    				
		    				double wert = teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).getEigen().get(indexValue).getWert();
		    				double delta = 0;
		    				double deltaR = 0;
		    				double random = kons.getRan();
		    				
		    				//Entscheidung ob DeltaFac oder DeltaAbs benutzt wird
		    				if(kons.getDeltaFac() != 0) {
		    					
		    					//Unterscheiden ob DeltaFac Summiert oder Reduziert
		    					if(kons.getDeltaFac()>1) {
		    						
		    						delta = ((kons.getDeltaFac()-1)*wert)*((100-random)/100);
		    						deltaR = ((kons.getDeltaFac()-1)*wert)*(random/100);
		    					
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)delta);
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)(Math.random()*(deltaR+1)));
		    					}else {
		    						delta = ((wert -(wert * kons.getDeltaFac()))*((100-random)/100))*(-1);
		    						deltaR = (wert - (wert * kons.getDeltaFac()))*(random/100);
		    						
		    						
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)delta);
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue, ((int)(Math.random()*(deltaR+1)))*(-1));
		    					}
		    					
		    				}else if(kons.getDeltaAbs () != 0) {
		    					
		    					//Unterscheide ob DeltaAbs Summiert oder Reduziert
		    					if(kons.getDeltaAbs()>1) {
		    						delta = kons.getDeltaAbs()*((100-random)/100);
		    						deltaR = kons.getDeltaAbs()*(random/100);
		    					
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)delta);
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)(Math.random()*(deltaR+1)));
		    					}else {
		    						delta = kons.getDeltaAbs()*((100-random)/100);
		    						deltaR = (kons.getDeltaAbs()*(random/100))*(-1);
		    						
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)delta);
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue,((int)(Math.random()*(deltaR+1))*(-1)));
		    						
		    					}
		    				}
		    				
		    				
		    			}
	    				break;
	    				
	    			case 2:
	    				for(int p=0;p<situ.get(k).getC().size();p++) {
		    				kons = situ.get(k).getC().get(p);
		    				
		    				//index für das Richtige Attribut
		    				int indexValue = teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).getIndex(kons.getVar());    				
		    				
		    				if(indexValue == -1) {
		    					info = new Alert(AlertType.INFORMATION);
		    					info.setContentText("Mitarbeiter "  + kons.getMitarbeiter() + ", besitzt kein Attribut "+ kons.getVar() + ". \nDie Konsequenz wurde Uebersprungen" );
		    			        info.show();
		    					continue;
		    				}
		    				
		    				double wert = teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).getEigen().get(indexValue).getWert();
		    				double delta = 0;
		    				double deltaR = 0;
		    				double random = kons.getRan();
		    				
		    				//Entscheidung ob DeltaFac oder DeltaAbs benutzt wird
		    				if(kons.getDeltaFac() != 0) {
		    					
		    					//Unterscheiden ob DeltaFac Summiert oder Reduziert
		    					if(kons.getDeltaFac()>1) {
		    						
		    						delta = ((kons.getDeltaFac()-1)*wert)*((100-random)/100);
		    						deltaR = ((kons.getDeltaFac()-1)*wert)*(random/100);
		    					
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)delta);
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)(Math.random()*(deltaR+1)));
		    					}else {
		    						delta = ((wert -(wert * kons.getDeltaFac()))*((100-random)/100))*(-1);
		    						deltaR = (wert - (wert * kons.getDeltaFac()))*(random/100);
		    						
		    						
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue, (int)delta);
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue, ((int)(Math.random()*(deltaR+1)))*(-1));
		    					}
		    					
		    				}else if(kons.getDeltaAbs () != 0) {
		    					
		    					//Unterscheide ob DeltaAbs Summiert oder Reduziert
		    					if(kons.getDeltaAbs()>1) {
		    						delta = kons.getDeltaAbs()*((100-random)/100);
		    						deltaR = kons.getDeltaAbs()*(random/100);
		    					
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)delta);
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)(Math.random()*(deltaR+1)));
		    					}else {
		    						delta = kons.getDeltaAbs()*((100-random)/100);
		    						deltaR = (kons.getDeltaAbs()*(random/100))*(-1);
		    						
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue,(int)delta);
		    						teilNehm.get(t).worker.get(kons.getMitarbeiter()-1).addWert(indexValue,((int)(Math.random()*(deltaR+1))*(-1)));
		    						
		    					}
		    				}
		    				
		    				
		    			}
	    				break;
	    						
	    			}
	    			
	    		}
	    		
	    		//Attribute der Mitarbeiter nach jeder Runde in (die BarChart) in ein Array für Runden
	    		
	        		
	        	
	    	}
	    	}
	    	//Attribute der Mitarbeiter am ende Aller Runden
	    	lbAnzeige.setText("Start/Aktuell");
	    	barChart.getData().clear();
	    	for(int i=0;i<copyS.worker.size();i++) {
	    		addBarCharts(copyS.worker.get(i), barChart);
	    	}
	    	String sp = chBoxTeil.getValue();
			   sp = sp.substring(7);
			   int spieler = Integer.parseInt(sp);
	    	for(int i=0;i<teilNehm.get(spieler-1).worker.size();i++) {
	    		addBarCharts(teilNehm.get(spieler-1).worker.get(i), barChart);
	    	} barChart.setTitle(chBoxTeil.getValue()+", Übersicht Mitarbeiter Attribute");
	    	
	    }else {
	    	info = new Alert(Alert.AlertType.WARNING);
	    	info.setTitle("DatenListe nicht vollständig!");
	    	info.setHeaderText("Zuerst Mitarbeiter, Situationen und Auswahl laden.");
	    	info.show();
	    	
	    }
	  } 
	    
	       
	    
	   @Override
	    public void initialize (URL url, ResourceBundle rb) {
	    	//Hier kann der BarChart schon beim Programmstart mit Daten initialisiert werden
		   
		   //Label für Tabelle inizialisieren & add to GridPane
		   for(int i=0;i<labels.length;i++) {
			   for(int j=0;j<labels[0].length;j++) {
				   labels[i][j] = new Label();
				   gridTable.add(labels[i][j], j, i);
			   }
		   }
		   
		   
		   
		   labels[0][0].setText("Mitarbeiter");
		   
	    	
		   
		   
	   }
	   
	   @FXML
	   void refreshChart(ActionEvent event) {
		if(teilIsfull) {
			   lbAnzeige.setText("Aktuell");
		   	   barChart.getData().clear();
			   String sp = chBoxTeil.getValue();
			   sp = sp.substring(7);
			   int spieler = Integer.parseInt(sp);
			   for(int i=0;i<teilNehm.get(spieler-1).worker.size();i++) {
				   addBarCharts(teilNehm.get(spieler-1).worker.get(i), barChart);
			   } 
			   barChart.setTitle(chBoxTeil.getValue()+", Übersicht Mitarbeiter Attribute");
			   
			   lbTSp.setText(sp);
			   for(int i=0;i<teilNehm.get(spieler-1).worker.size();i++) {
					for(int j=0;j<copyS.worker.get(0).getEigen().size();j++) {	
						labels[i+1][j+1].setText(String.valueOf(teilNehm.get(spieler-1).worker.get(i).getEigen().get(j).getWert()));
					}
				} 
			         
		   }else{
			   info = new Alert(AlertType.INFORMATION);
			   info.setTitle("Keine Mitarbeiterdaten vorhanden");
			   info.setHeaderText("Erst möglich wenn Mitarbeiter geladen worden sind");
			   info.show();
		   }
		   
	   }
	    
	    
	    public void addBarCharts(Mitarbeiter m, BarChart<String, Number> barChart) {
	    	XYChart.Series<String, Number> series = new XYChart.Series<>();
	    	series.setName(m.getName());
	    	for(int i=0;i<m.getEigen().size();i++) {
	    		series.getData().add(new XYChart.Data<>(m.getEigen().get(i).getName(),m.getEigen().get(i).getWert()));
	    	}
	    	barChart.getData().add(series);
    		
    	}
	    
	    
	    //Die eingelesenen Attribut-Werte wiederherstellen
	    public void loadCopy() {
	    	 
	    	
	    	if(sIsfull) {
	    		   lbAnzeige.setText("Start/Aktuell");
				   barChart.getData().clear();
				   for(int u=0; u<copyS.worker.size(); u++) {
			    		addBarCharts(copyS.worker.get(u), barChart);	
			    		}
				   barChart.setTitle(chBoxTeil.getValue()+", Übersicht Mitarbeiter Attribute");
				   String sp = chBoxTeil.getValue();
				   sp = sp.substring(7);
				   int spieler = Integer.parseInt(sp);
				   for(int h =0; h<teilNehm.get(spieler-1).worker.size();h++) {
					   addBarCharts(teilNehm.get(spieler-1).worker.get(h), barChart);
				   }
				   
				   lbTSp.setText(sp);
				   for(int i=0;i<teilNehm.get(spieler-1).worker.size();i++) {
						for(int j=0;j<copyS.worker.get(0).getEigen().size();j++) {	
							labels[i+1][j+1].setText(String.valueOf(teilNehm.get(spieler-1).worker.get(i).getEigen().get(j).getWert()));
						}
					} 
				      
			   }else{
				   info = new Alert(AlertType.INFORMATION);
				   info.setTitle("Keine Mitarbeiterdaten vorhanden");
				   info.setHeaderText("Erst möglich wenn Mitarbeiter geladen worden sind");
				   info.show();
			   }
			   
	    }
	    
	   
	    
	    @FXML
	    void loadSitu(ActionEvent event) {
	    	//Situationen aus Tabelle laden
	    	FileChooser fileChooser = new FileChooser();
        	File selectedDirectory = fileChooser.showOpenDialog(null);
        	DatenVerarbeiten(selectedDirectory);
        	
        	if(sIsfull && !teilIsfull) {
        		lbAnzeige.setText("Aktuell");
        		barChart.getData().clear();
            	for(int i=0; i<s.worker.size(); i++) {
            		addBarCharts(s.worker.get(i), barChart);
            		
            	} 
        	}else if (sIsfull && teilIsfull) {
        		lbAnzeige.setText("Aktuell");
        		barChart.getData().clear();
        		for(int i=0; i<teilNehm.get(0).worker.size(); i++) {
            		addBarCharts(teilNehm.get(0).worker.get(i), barChart);
            		
            	}barChart.setTitle(chBoxTeil.getValue() + ", Übersicht Mitarbeiter Attribute"); 
        	}
        	 
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
				sitIs = true;
				for (int i = 1; i < tabelle.length; i++) {
					int index =Integer.parseInt(tabelle[i][0])-1;
					if (situ.isEmpty()) {
						situ.add(new Situation(tabelle[i]));
					}else if(situ.size()<= index) {
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
			else if(tabelle[0][0].contains("TN")) {
				if(mitarbeiter == null) {
					info.setHeaderText("Zuerst die Mitarbeiterdaten laden");
					info.show();
				}else {
					teilNehm = new LinkedList<Simulator>();
					for(int i=1;i<tabelle.length;i++) {
						teilNehm.add(new Simulator(mitarbeiter));
					    for(int k=1;k<tabelle[i].length;k++) {
					    	teilNehm.get(i-1).addAuswahl(tabelle[i][k]);
					    }
					} teilIsfull = true;
					
					
					//ChooseBox inizalisieren
					String[] namen = new String[teilNehm.size()];
					for(int i=0;i<teilNehm.size();i++) {
						namen[i] = "Spieler"+(i+1);
					}
					chBoxTeil.getItems().removeAll(chBoxTeil.getItems());
					chBoxTeil.getItems().addAll(namen);
					chBoxTeil.getSelectionModel().select(0);
					
					lbRunden.setText("0");
					
					//Tabellen Ansicht laden
					if(teilNehm.size()<7) {
						lbTSp.setText("1");
						for(int i=0;i<teilNehm.getFirst().worker.size();i++) {
							labels[i+1][0].setText(String.valueOf(i+1));
						}
						
						for(int i=0;i<teilNehm.getFirst().worker.size();i++) {
							for(int j=0;j<copyS.worker.get(0).getEigen().size();j++) {	
								labels[i+1][j+1].setText(String.valueOf(teilNehm.get(0).worker.get(i).getEigen().get(j).getWert()));
							}
						} 
						
						
					}else {
						info.setHeaderText("Die Tabellen Ansicht ist nur für max 6 Mitarbeiter, Die Auswahl Tabelle anpassen!");
						info.show();
					}
				}
			}
			else {
				mitarbeiter = new String[zeile][spalte];
				mitarbeiter = tabelle.clone();
				s = new Simulator(tabelle);
				copyS = new Simulator(tabelle);
				sIsfull = true;
				
				if(copyS.worker.get(0).getEigen().size()<6) {
					for(int i=0;i<copyS.worker.get(0).getEigen().size();i++){
						labels[0][i+1].setText(copyS.worker.get(0).getEigen().get(i).getName());
					}
				}else {
					info.setHeaderText("Die Tabellen Ansicht ist nur für max 5 Attribute, Die Mitarbeiter Tabelle anpassen!");
					info.show();
				}
			
		}
	}
}