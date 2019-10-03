import java.util.ArrayList;

public class MiniMarket extends Magazin{
	
	// nume is from Magazin
	private String tip = "MiniMarket";

	@Override
	public int calculScutiriTaxe() {
		double total = getTotalCuTaxe();
		
		//find all the countries
		ArrayList<String> tari = new ArrayList<>();
		for(Factura fact : getFacturi()) {
			for(ProdusComandat comanda : fact.getLinii()) {
				String country = comanda.getProdus().getTaraOrigine();
				if( !(tari.contains(country)) ) {
					tari.add(country);
				}
			}
		}
		
		for(String tara : tari) {
			double totalCustom = getTotalTaraCuTaxe(tara);
			if(totalCustom > total/2) {
				return 10;
			}
		}
		return 0;
	}
	
	public String getType() {
		return tip;
	}

	public String toString() {
		String rez = "";
		rez += getNume() + " is a " + tip + '\n';
		for(Factura i : getFacturi()) {
			rez += i.getDenumire() + '\n';
			for(ProdusComandat j : i.getLinii()) {
				rez += j;
				rez += "\n";
			}
		}
		return rez;
	}

}
