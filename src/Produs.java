public class Produs {
	private String denumire;
	private String categorie;
	private String taraOrigine;
	private double pret;
	
	public Produs(String denumire, String categorie, String tara, double pret) {
		this.denumire = denumire;
		this.categorie = categorie;
		this.taraOrigine = tara;
		this.pret = pret;
	}
	
	public Produs() {
		this("","","",0.0);
	}

	public String getDenumire() {
		return denumire;
	}

	public void setDenumire(String denumire) {
		this.denumire = denumire;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getTaraOrigine() {
		return taraOrigine;
	}

	public void setTaraOrigine(String taraOrigine) {
		this.taraOrigine = taraOrigine;
	}

	public double getPret() {
		return pret;
	}

	public void setPret(double pret) {
		this.pret = pret;
	}
	
	public String toString() {
		String rez = "";
		rez += getDenumire() + " " + getCategorie() + " " + getTaraOrigine() + " " + getPret();
		return rez;
	}
}
