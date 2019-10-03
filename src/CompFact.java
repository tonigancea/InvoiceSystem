import java.util.Comparator;

public class CompFact implements Comparator<Factura>{
	public int compare(Factura fact1, Factura fact2) {
		double total1 = fact1.getTotalCuTaxe();
		double total2 = fact2.getTotalCuTaxe();
		return (int)(total1 - total2);
	}
}
