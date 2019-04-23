package java_extApp;
 
import java.util.Scanner;
/**
* <h1>K.D.ExtensionApp</h1>
* Tento program sluzi ako prezentacia kostry pre buduci projekt.
* <p>
* Podla urcenia poctu hracov sa postupne dostavaju do evidencie konkretny
* hraci, ktori budu v hre ucinkovat. Hlavnym identifikatorom je ich meno.
* Program postupne za pomoci pozastavenia chodu hlavneho vlakna vypisuje
* meno hraca, ktory je v hre prave na rade.
* Ku danemu menu sa zobrazi jedinecna karticka s ulohou, ktora je vytiahnuta
* z databazy pri pociatocnom spusteni programu. Karticky sa generuju nahodne.
* Karticky, ktore sa uz pocas hry raz nejakemu hracovi zobrazili su zozbierane
* do HashSet-u, ktory sa pred kazdym dalsim generovanim prehlada a overi
* dostupnost.
* </p>
* Hlavna funkcia programu {@link #main(String[])}, je zdokumentovana podla uvedeneho zoznamu:
* <ul>
* 	<li>Ucel metody,</li>
* 	<li>Vyznam jednotlivych parametrov,</li>
* 	<li>Navratove hodnoty,</li>
* 	<li>Dovod veduci k pripadnemu vyhodeniu vynimky.</li>
* </ul>
* 
* @author Martin Krajcovic
* @version 1.0_test
* @since Spring-2019
*/
public class Main extends Cards {
	static int[] pocetHracov = new int[3];
	/**
	 * <h1>MAIN</h1>
	 * V hlavnom tele programu je interakcia so zvolenym poctom hracov.
	 * Kazdemu bude pridelene meno/prezyvka, ktoru si navoli a vlastne
	 * pocitadlo sposobu ukoncenia vygenerovanej ulohy.
	 * 
	 * @param args Unused
	 * @return Nothing
	 * @throws InterruptedException
	 * 		   Kvoli hlavnemu vlaknu (toto v aplikacii nebude)
	 */
	public static void main(String[] args) {
		System.out.print("Enter the number of players: ");
		System.out.println("3");
		
		Player[] players = new Player[pocetHracov.length];
		Scanner sc_name = new Scanner(System.in);
		
		/* MENA HRACOV */
		for (int i = 0; i < pocetHracov.length; i++) {
			System.out.print("Enter name of the player: ");
			players[i] = new Player(sc_name.nextLine());
		}
		sc_name.close();
		System.out.println("---------------------------");
		
		/* STIAHNUTIE UDAJOV Z DATABAZY */
		receiveCards();	
		System.out.println("Cards have been downloaded");
		System.out.println("---------------------------");
		
		/* HRACI CYKLUS */
		int round = 0;		// <______________
		while (round < 3) {	//dosadit priznak |
			int turn = 0;	//poradie sa zakazdym vynuluje, aby sa opakovalo v dalsom kole
			while(turn < pocetHracov.length) {
				try {
					Thread.currentThread();
					Thread.sleep(2000);
					System.out.println(players[turn].getName() + " is playing");
					System.out.println(useCards());		//nahodne vygenerovanie karticiek na vystup
					if ((round % 2) == 0) {				//kazde druhe kolo sa pripise hracovi bod za pitie
						players[turn].setDrinkScore(players[turn].getDrinkScore()+1);
					}
					else
						players[turn].setDoScore(players[turn].getDoScore()+1);	//kazde neparne = bod za ulohu
					System.out.println();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				turn++;
			}
			round++;
		}
		/* SKORE HRACOV */
		System.out.println("---------------------------");
		System.out.println("         GAME OVER");
		System.out.println("---------------------------");
		for (int i = 0; i < pocetHracov.length; i++) {
			System.out.println(players[i].getName().toUpperCase() + ": ");
			System.out.print("[Ulohy: " + players[i].getDoScore());
			System.out.println("  Drinky: " + players[i].getDrinkScore() + "]" + System.lineSeparator());
		}
	}
}