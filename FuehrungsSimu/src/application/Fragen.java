package application;

public class Fragen {
	
	private int mitarbeiter;
	private String variable;
	private double deltaFactor;
	private double deltaAbs;
	private int randomizer;
	
	public Fragen(int mitarbeiter, String variable, double deltaFactor, double deltaAbs, int randomizer) {
		super();
		this.mitarbeiter = mitarbeiter;
		this.variable = variable;
		this.deltaFactor = deltaFactor;
		this.deltaAbs = deltaAbs;
		this.randomizer = randomizer;
	}
	
	public int getMitarbeiter() {
		return this.mitarbeiter;
	}
	
	public String getVar() {
		return this.variable;
	}
	
	public double getDeltaFac() {
		return this.deltaFactor;
	}
	
	public double getDeltaAbs() {
		return this.deltaAbs;
	}
	
	public int getRan() {
		return this.randomizer;
	}
	
}
