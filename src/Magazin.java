import java.text.DecimalFormat;
import java.util.Vector;

public abstract class Magazin implements IMagazin {
	private String nume;
	private Vector<Factura> facturi = new Vector<>();
	
	public void setNume(String name) {
		nume = name;
	}
	
	public Vector<Factura> getFacturi(){
		return facturi;
	}
	
	public String getNume() {
		return nume;
	}
	
	public String getType() {
		return null;
	}

	@Override
	public double getTotalFaraTaxe() {
		double total = 0;
		for(Factura fact : facturi) {
			total += fact.getTotalFaraTaxe();
		}
		return total;
	}

	@Override
	public double getTotalCuTaxe() {
		double total = 0;
		for(Factura fact : facturi) {
			total += fact.getTotalCuTaxe();
		}
		return total;
	}

	@Override
	public double getTotalCuTaxeScutite() {
		DecimalFormat df = new DecimalFormat("###.####");
		double total = getTotalCuTaxe();
		double procentReducere = calculScutiriTaxe();
		procentReducere = procentReducere / 100;
		double pretRedus = Double.parseDouble(df.format(total))*(1-procentReducere);
		//System.out.println(getNume() + ": Total=" + total + " cu o reducere de " + procentReducere + "%. Asadar noul total este:" + pretRedus);
		return pretRedus;
	}

	@Override
	public double getTotalTaraFaraTaxe(String tara) {
		double total = 0;
		for(Factura fact : facturi) {
			total += fact.getTotalTaraFaraTaxe(tara);
		}
		return total;
	}

	@Override
	public double getTotalTaraCuTaxe(String tara) {
		double total = 0;
		for(Factura fact : facturi) {
			total += fact.getTotalTaraCuTaxe(tara);
		}
		return total;
	}
	
	public double getTotalCategorieCuTaxe(String category) {
		double total = 0;
		for(Factura fact : facturi) {
			total += fact.getTotalCategorieCuTaxe(category);
		}
		return total;
	}

	@Override
	public double getTotalTaraCuTaxeScutite(String tara) {
		DecimalFormat df = new DecimalFormat("###.####");
		double total = getTotalTaraCuTaxe(tara);
		double procentReducere = calculScutiriTaxe();
		procentReducere = procentReducere / 100;
		double pretRedus = Double.parseDouble(df.format(total))*(1-procentReducere);
		return pretRedus;
	}
}
