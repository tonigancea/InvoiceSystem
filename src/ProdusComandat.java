public class ProdusComandat {
	private Produs produs;
	private double taxa;
	private int cantitate;

	public Produs getProdus() {
		return produs;
	}

	public void setProdus(Produs produs) {
		this.produs = produs;
	}

	public double getTaxa() {
		return taxa;
	}

	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}

	public int getCantitate() {
		return cantitate;
	}

	public void setCantitate(int cantitate) {
		this.cantitate = cantitate;
	}

	public String toString() {
		String rez = "";
		rez += getProdus().getDenumire() + " la " + getProdus().getPret() + " si taxa de " + getTaxa() + " la bucata. Bucati:" + getCantitate();
		return rez;
	}
}
