
public class HyperMarket extends Magazin {

	// nume is from Magazin
	private String tip = "HyperMarket";

	public String getType() {
		return tip;
	}
	
	@Override
	public int calculScutiriTaxe() {
		double total = getTotalCuTaxe();
		
		for(Factura fact : getFacturi()) {
			double totalCustom = fact.getTotalCuTaxe();
			if(totalCustom > total/10) {
				return 1;
			}
		}
		return 0;
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
