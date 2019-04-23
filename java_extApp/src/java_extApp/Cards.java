package java_extApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
/**
 * <h1>KARTICKY S ULOHAMI</h1>
 * Tato trieda zabezpecuje herny obsah stiahnuty zo vzdialenej db
 * priamo do suborov - (potom nie je pripojenie na internet potrebne).
 * 
 * Obsah tvoria dve metody {@link #receiveCards()}, {@link #useCards()},
 * na ktorych je demonstrovana dokumentacia:
 * <ul>
 * 	 <li>Ucel metody,</li>
 * 	 <li>Vyznam jednotlivych parametrov,</li>
 * 	 <li>Navratove hodnoty,</li>
 * 	 <li>Dovod veduci k pripadnemu vyhodeniu vynimky.</li>
 * </ul>
 * 
 * @author Martin Krajcovic
 * @version 1.0_test
 * @since Spring-2019
 */
public class Cards {
	static String[][] card = new String[10][2];
	static File f = new File("cardHead.nz");
	static File d = new File("cardBody.ul");
	static Random r = new Random();
	static Set<Integer> validate = new HashSet<>();
	static int nextRandom = r.nextInt(card.length);
	/**
	 * <h1>STIAHNUTIE KARTICIEK</h1>
	 * Prvy krok metody zacina overenim existencie suborov v relativnom umiestneni.
	 * Pokial neexistuju alebo nejaky z nich chyba, vytvoria sa, stiahnu sa do nich
	 * udaje zo vzdialenej databazy. Pokial subory uz existuju, ich obsah zostava,
	 * pripojenie k db nie je potrebne a rovnako tak ani pripojenie na internet.
	 * Udaje zo suborov sa ulozia do dvojrozmerneho pola -> staticka globalna premenna
	 * 
	 * @param Nothing
	 * @return Nothing
	 * @throws SQLException
	 * @throws IOException
	 */
	public static void receiveCards() {
		if (!(f.exists() && d.exists())) {		//ked subor alebo subory neexistuju
			try (Connection c = DriverManager.getConnection("jdbc:postgresql:postgres","postgres","backlash");
					Statement st = c.createStatement();
					ResultSet rs = st.executeQuery("SELECT DISTINCT name, account_id, amount FROM bills");
					FileWriter fw = new FileWriter(f, false); //false znaci, ze sa bude subor prepisovat
					FileWriter fx = new FileWriter(d, false);
					BufferedReader br1 = new BufferedReader(new FileReader(f));
					BufferedReader br2 = new BufferedReader(new FileReader(d));) {
				f.createNewFile();
				d.createNewFile();
				for (int i = 0; i < card.length; i++) {
					if (rs.next()) {	//pokial su v db udaje, stahuju sa do suborov
						fw.append(rs.getString("name") + System.lineSeparator());
						fx.append(rs.getString("amount") + System.lineSeparator());
					}
				}
				fw.close();		//je pouzity try-with-resource -> takze uzavretia tu nemusia byt
				fx.close();
				c.close();		//uzavretie spojenia 
				for (int i = 0; i < card.length; i++) {
					card[i][0] = br1.readLine();
					card[i][1] = br2.readLine();
				}
				br1.close();
				br2.close();
			} catch (SQLException | IOException e) {
				e.printStackTrace();
				System.exit(1); //aby cyklus v MAIN neiteroval aj bez stiahnutych udajov
			}
		}
		else if (f.exists() && d.exists()) {	//ked oba subory existuju
			try (BufferedReader br1 = new BufferedReader(new FileReader(f));
				BufferedReader br2 = new BufferedReader(new FileReader(d));) {
				for (int i = 0; i < card.length; i++) {	
					card[i][0] = br1.readLine();	//do pola sa nacitaju udaje zo suborov
					card[i][1] = br2.readLine();
				}
				br1.close();	// -> uzavretia tu nemusia byt
				br2.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	/**
	 * <h1>POUZITIE KARTICIEK</h1>
	 * Tato metoda nahodne vygeneruje dvojicu udajov z pola, v ktorom sa nachadzaju
	 * zaznamy zo suborov. Index (poradie) tejto dvojice udajov sa ulozi do HashSet-u.
	 * Cyklus overuje, ci sa uz novy vygenerovany udaj nenachadza v HashSet zozname.
	 * Ak tam tento udaj uz figuruje, potom nastava nove generovanie, pokial sa nedostane
	 * jedinecna hodnota, ktora este nebola na vystupe.
	 * 
	 * Toto sa deje tolko krat, kolko iteruje cyklus v MAIN.
	 * Funkcia vracia zakazdym iba jeden retazec.
	 * (v main je mozne vypisat udaje osobitne, ked zrusime navratovy typ funkcie na void)
	 * 
	 * @param Nothing
	 * @return String 
	 * @throws Nothing
	 */
	public static String useCards() {	/* NAHODNE GENEROVANIE A OVEROVANIE JEDINECNOSTI */
		validate.add(nextRandom);	//ulozenie prvej vygenerovanej dvojice do zoznamu
			while (validate.contains(nextRandom)) {	//lustrovanie zoznamu uz zobrazenych dvojic
				nextRandom = r.nextInt(card.length);//nove generovanie, pokial nebude platit podmienka
			}
			validate.add(nextRandom);	//ulozenie dalsej vygenerovanej dvojice do zoznamu
			return (card[nextRandom][0] + " " + card[nextRandom][1]);	//vysledok funkcie = jeden retazec
	}
}