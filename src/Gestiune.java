import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Gestiune {
	private static Gestiune instance = null;

	private Gestiune() {
		//nothing
	}

	public static Gestiune getInstance() {
		if (instance == null)
			instance = new Gestiune();
		return instance;
	}

	private ArrayList<Produs> produse = new ArrayList<>();
	private ArrayList<Magazin> magazine = new ArrayList<>();
	private TreeMap<String, TreeMap<String, Integer>> taxe = new TreeMap<String, TreeMap<String, Integer>>();
	private ArrayList<String> typeStores = new ArrayList<>();

	public ArrayList<Produs> getProduse() {
		return produse;
	}

	public ArrayList<Magazin> getMagazine() {
		return magazine;
	}

	public TreeMap<String, TreeMap<String, Integer>> getTaxe() {
		return taxe; // << Getter for the TreeMap
	}
	
	public ArrayList<String> getTypes(){
		return typeStores;
	}

	public void parseProduse() {
		ArrayList<String> tari = new ArrayList<>();
		File f = new File("produse.txt");

		try {
			Scanner scan = new Scanner(f);
			StringTokenizer tok = new StringTokenizer(scan.nextLine(), " \n");

			// get the Countries
			tok.nextToken(); // ignore Produs
			tok.nextToken(); // ignore Categorie
			while (tok.hasMoreTokens()) {
				tari.add(tok.nextToken());
			}

			// Populate the List with Produse
			while (scan.hasNextLine()) {
				StringTokenizer tok2 = new StringTokenizer(scan.nextLine(), " \n");
				String prod = tok2.nextToken();
				String cat = tok2.nextToken();
				int index = 0;
				while (tok2.hasMoreTokens()) {
					Produs produs = new Produs();
					produs.setDenumire(prod);
					produs.setCategorie(cat);
					produs.setTaraOrigine(tari.get(index));
					produs.setPret(Double.parseDouble(tok2.nextToken()));
					getProduse().add(produs);
					index += 1;
				}
			}
			scan.close();
		}

		catch (FileNotFoundException e) {
			System.out.println("Eroare la deschiderea produse.txt");
			e.printStackTrace();
		}
	}

	public void parseTaxe() {
		File f = new File("taxe.txt");
		ArrayList<String> tari = new ArrayList<>();
		try {
			Scanner scan = new Scanner(f);
			StringTokenizer tok = new StringTokenizer(scan.nextLine(), " \n");

			// get the Countries (Rusia & Crimeea)
			tok.nextToken();
			while (tok.hasMoreTokens()) {
				tari.add(tok.nextToken());
			}
			for (String tara : tari) {
				getTaxe().put(tara, new TreeMap<String, Integer>());
			}

			// populating the Map ...
			while (scan.hasNextLine()) {
				StringTokenizer tok2 = new StringTokenizer(scan.nextLine(), " \n");
				String categorie = tok2.nextToken();
				int index = 0;
				while (tok2.hasMoreTokens()) {
					getTaxe().get(tari.get(index)).put(categorie, Integer.parseInt(tok2.nextToken()));
					index += 1;
				}
			}

			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("Eroare la deschiderea taxe.txt");
			e.printStackTrace();
		}
	}

	public void parseStores() {
		getTypes().add("MiniMarket");
		getTypes().add("MediumMarket");
		getTypes().add("HyperMarket");
		
		File f = new File("facturi.txt");
		try {
			Scanner scan = new Scanner(f);
			MagazinFactory factory = new MagazinFactory();
			while (scan.hasNextLine()) {
				String line = scan.nextLine();

				// Add new Store
				if (line.contains("Magazin")) {
					StringTokenizer tok = new StringTokenizer(line, ":\n");
					tok.nextToken();
					String codeMag = tok.nextToken();
					
					Magazin store = factory.makeStore(codeMag);
					store.setNume(tok.nextToken());
					getMagazine().add(store);
				}

				// Add an new invoice for the last store
				else if (line.contains("Factura")) {
					StringTokenizer tok2 = new StringTokenizer(line, " \n");
					Factura fact = new Factura(tok2.nextToken());
					int index_last_magazine = getMagazine().size();
					index_last_magazine -= 1;
					getMagazine().get(index_last_magazine).getFacturi().add(fact);

					// adaugam toate produsele comandate [Consumerism :))]
					scan.nextLine(); // ignore this line [ DenumireProdus..]
					line = scan.nextLine();
					while (line.length() > 0) {

						StringTokenizer tok3 = new StringTokenizer(line, " \n");

						String denumireProdus = tok3.nextToken();
						String taraProdus = tok3.nextToken();
						int cantitateProdus = Integer.parseInt(tok3.nextToken());
						String categorieProdus = "";

						ProdusComandat produs = new ProdusComandat();
						produs.setCantitate(cantitateProdus); // cantitate inregistrata

						for (Produs i : getProduse()) {
							if (i.getDenumire().equals(denumireProdus) && i.getTaraOrigine().equals(taraProdus)) {
								produs.setProdus(i); // produs inregistrat
								categorieProdus += i.getCategorie();
								break;
							}
						}

						// calculate the taxes
						double adaos = getTaxe().get(taraProdus).get(categorieProdus);
						adaos = produs.getProdus().getPret() * adaos / 100;
						DecimalFormat df = new DecimalFormat("###.####");
						produs.setTaxa(Double.parseDouble(df.format(adaos)));	//taxa inregistrata
						//System.out.print("Taxa pentru " + produs.getProdus().getDenumire() + " din " + produs.getProdus().getTaraOrigine() + " e: ");
						//System.out.println(produs.getTaxa());
						
						fact.getLinii().add(produs); //adding the product on the invoice
						
						if (scan.hasNextLine())
							line = scan.nextLine();
						else
							break;
					}
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("Eroare la deschiderea facturi.txt");
			e.printStackTrace();
		}
	}
	
	public void sortEverything() {
		Collections.sort(magazine,new CompStore());
		for(Magazin mag : getMagazine()) {
			Collections.sort(mag.getFacturi(),new CompFact());
		}
	}
	
	public String toString() {
		String rez = "";
		for(Magazin mag : getMagazine()) {
			rez += mag;
			rez += "\n";
		}
		return rez;
	}
	
	public void makeOutput() {
		try {
			PrintWriter writer = new PrintWriter("out.txt","UTF-8");
			DecimalFormat df = new DecimalFormat("###.####");
			for(String type : getTypes()) {
				writer.println(type);
				for(Magazin mag : getMagazine()) {
					if(mag.getType().equals(type)) {
						writer.println(mag.getNume());
						writer.println();
						writer.println("Total " + df.format(mag.getTotalFaraTaxe()) + " " + df.format(mag.getTotalCuTaxe()) + " " + df.format(mag.getTotalCuTaxeScutite()));
						writer.println();
						writer.println("Tara");
						for(String tara : getTaxe().keySet()) {
							double totalTaraFaraTaxe = Double.parseDouble(df.format(mag.getTotalTaraFaraTaxe(tara)));
							double totalTaraCuTaxe = Double.parseDouble(df.format(mag.getTotalTaraCuTaxe(tara)));
							double totalTaraCuTaxeScutite = Double.parseDouble(df.format(mag.getTotalTaraCuTaxeScutite(tara)));
							if(totalTaraFaraTaxe > 0) {
								writer.println(tara + " " + totalTaraFaraTaxe + " " + totalTaraCuTaxe + " " + totalTaraCuTaxeScutite);
							}
							else {
								writer.println(tara + " " + 0);
							}
							
						}
						writer.println();
						for(Factura fact : mag.getFacturi()) {
							writer.println(fact.getDenumire());
							writer.println();
							writer.println("Total " + df.format(fact.getTotalFaraTaxe()) + " " + df.format(fact.getTotalCuTaxe()));
							writer.println();
							writer.println("Tara");
							for(String tara : getTaxe().keySet()) {
								double totalFactTaraFaraTaxe = Double.parseDouble(df.format(fact.getTotalTaraFaraTaxe(tara)));
								double totalFactTaraCuTaxe = Double.parseDouble(df.format(fact.getTotalTaraCuTaxe(tara)));
								if(totalFactTaraFaraTaxe > 0) {
									writer.println(tara + " " + totalFactTaraFaraTaxe + " " + totalFactTaraCuTaxe);
								}
								else {
									writer.println(tara + " " + 0);
								}
							}	
							writer.println();
						}
					}
				}
			}
			writer.close();
		}
		catch(IOException e) {
			System.out.println("Something wrong happend when writing the output file.");
			e.printStackTrace();
		}
	}
}

class PowerOn {
	public static void main(String[] args) {
		Gestiune start = Gestiune.getInstance();
		start.parseProduse();
		start.parseTaxe();
		start.parseStores();
		start.sortEverything();
		start.makeOutput();
	}
}