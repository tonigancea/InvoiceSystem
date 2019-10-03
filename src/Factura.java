import java.util.Vector;

public class Factura {
	private String denumire;
	private Vector<ProdusComandat> linii = new Vector<>();

	public Factura(String name) {
		denumire = name;
	}

	public Factura() {
		this("FacturaPredefinita");
	}

	public String getDenumire() {
		return denumire;
	}

	public Vector<ProdusComandat> getLinii() {
		return linii;
	}

	public double getTotalFaraTaxe() {
		double total = 0;
		for(ProdusComandat prod : linii) {
			total += prod.getCantitate() * prod.getProdus().getPret();
		}
		return total;
	}
	
	public double getTotalCuTaxe() {
		double total = 0;
		for(ProdusComandat prod : linii) {
			total += prod.getCantitate() * ( prod.getProdus().getPret() + prod.getTaxa() );
		}
		return total;
	}
	
	public double getTaxe() {
		double total = 0;
		for(ProdusComandat prod : linii) {
			total += prod.getCantitate() * prod.getTaxa();
		}
		return total;
	}
	
	public double getTotalTaraFaraTaxe(String tara) {
		double total = 0;
		for(ProdusComandat prod : linii) {
			if(prod.getProdus().getTaraOrigine().equals(tara)) {
				total += prod.getCantitate() * prod.getProdus().getPret();
			}
		}
		return total;
	}
	
	public double getTotalTaraCuTaxe(String tara) {
		double total = 0;
		for(ProdusComandat prod : linii) {
			if(prod.getProdus().getTaraOrigine().equals(tara)) {
				total += prod.getCantitate() * ( prod.getProdus().getPret() + prod.getTaxa() );
			}
		}
		return total;
	}
	
	public double getTotalCategorieCuTaxe(String category) {
		double total = 0;
		for(ProdusComandat prod : linii) {
			if(prod.getProdus().getCategorie().equals(category)) {
				total += prod.getCantitate() * ( prod.getProdus().getPret() + prod.getTaxa() );
			}
		}
		return total;
	}
	
	public double getTaxeTara(String tara) {
		double total = 0;
		for(ProdusComandat prod : linii) {
			if(prod.getProdus().getTaraOrigine().equals(tara)) {
				total += prod.getCantitate() * prod.getTaxa();
			}
		}
		return total;
	}
	
	public String toString() {
		String rez = "";
		rez += getDenumire() + "\n";
		return rez;
	}

}
