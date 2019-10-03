public interface IMagazin {
	//Total-uri generale
	double getTotalFaraTaxe();
	double getTotalCuTaxe();
	double getTotalCuTaxeScutite();
	
	//Total-uri in functie de tara
	double getTotalTaraFaraTaxe(String tara);
	double getTotalTaraCuTaxe(String tara);
	double getTotalTaraCuTaxeScutite(String tara);
	
	int calculScutiriTaxe();
}
