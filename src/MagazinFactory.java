public class MagazinFactory {
	public Magazin makeStore(String type) {
		if(type.equals("MiniMarket")) {
			return new MiniMarket();
		}
		else if(type.equals("MediumMarket")) {
			return new MediumMarket();
		}
		else if(type.equals("HyperMarket")) {
			return new HyperMarket();
		}
		return null;
	}
}
