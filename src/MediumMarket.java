import java.util.ArrayList;

public class MediumMarket extends Magazin {
	
	// nume is from Magazin
	private String tip = "MediumMarket";

	@Override
	public int calculScutiriTaxe() {
		double total = getTotalCuTaxe();
		
		//find all the categories
		ArrayList<String> categorii = new ArrayList<String>();
		for(Factura fact : getFacturi()) {
			for(ProdusComandat comanda : fact.getLinii()) {
				String category = comanda.getProdus().getCategorie();
				if( !(categorii.contains(category)) ) {
					categorii.add(category);
				}
			}
		}
		
		for(String category : categorii) {
			double totalCustom = getTotalCategorieCuTaxe(category);
			if(totalCustom > total/2)
				return 5;
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
