import java.util.Comparator;

public class CompStore implements Comparator<Magazin>{
	public int compare(Magazin mag1, Magazin mag2) {
		double total1 = mag1.getTotalFaraTaxe();
		double total2 = mag2.getTotalFaraTaxe();
		return (int) (total1 - total2);
	}
}
