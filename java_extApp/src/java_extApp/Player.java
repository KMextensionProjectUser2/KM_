package java_extApp;
/**
 * <h1>DEFINICIA UCHADZACA</h1>
 * Tato trieda reprezentuje herne vlastnosti kazdeho hraca.
 * <p>
 * Kazdy hrac ma vlastne meno a je drzitelom svojho herneho skore
 * v dvoch kategoriach.
 * </p>
 * Obsah tvori konstruktor, settery a gettery {@linkplain #Player(String)},{@link #setDoScore(int)}, 
 * {@link #setDrinkScore(int)},{@link #setName(String)},{@link #getDoScore()},{@link #getDrinkScore()},{@link #getName()}.
 * 
 * @author Martin Krajcovic
 * @version 1.0_test
 * @since Spring-2019
 */
public class Player {
	private String name;
	private int doScore;
	private int drinkScore;
	/**
	 * <h1>KONSTRUKTOR</h1>
	 * V jednom zo startovnych krokoch si kazdy hrac zvoli svoju prezyvku
	 * resp. svoje meno, ktore bude pocas hry pouzivat, to preberie
	 * konstruktor noveho objektu.
	 * @param n Toto je parameter typu String pre konstruktor hraca.
	 */
	Player(String n){
		setName(n);			//meno hraca sa preda setteru
	}
	/**
	 * <h1>SETTER - MENO HRACA</h1>
	 * Po prvom kroku sa zadana prazyvka hraca vlozi do settera,
	 * ktory sa dalej priradi do private premennej objektu tejto triedy.
	 * @param name Toto je parameter, ktory prijma setter pre meno.
	 * @return Nothing
	 */
	public void setName(String name){
		this.name = name;	//meno hraca sa priradi to private premennej objektu triedy
	}
	/**
	 * <h1>GETTER - MENO HRACA</h>
	 * Pomocou gettera dostaneme pocas hry prezyvku daneho objektu hraca
	 * z private premennej objektu tejto triedy.
	 * @param None
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * <h1>SETTER - BODY ZA ULOHY</h1>
	 * Ked hrac ziska bod za splnenie ulohy, pomocou settera sa tento bod
	 * pripise do private premennej objektu tejto triedy.
	 * @param doScore
	 * @return Nothing
	 */
	public void setDoScore(int doScore) {
		this.doScore = doScore;
	}
	/**
	 * <h1>GETTER - BODY ZA ULOHY</h1>
	 * Getter sa v tomto programe vyuziva pre aktualizaciu private premennej
	 * objektu tejto triedy a v zavere pre ziskanie celkoveho poctu bodov hraca
	 * v tejto kategorii.
	 * @param Nothing
	 * @return doScore
	 */
	public int getDoScore() {
		return doScore;
	}
	/**
	 * <h1>SETTER - BODY ZA PITIE</h1>
	 * Ked hrac neziska bod za splnenie ulohy, tak mu bude priradeny bod
	 * za pitie, ktory sa ulozi do private premennej objektu tejto triedy.
	 * @param drinkScore
	 * @return Nothing
	 */
	public void setDrinkScore(int drinkScore) {
		this.drinkScore = drinkScore;
	}
	/**
	 * <h1>GETTER - BODY ZA PITIE</h1>
	 * Getter sa v tomto programe vyuziva pre aktualizaciu private premennej
	 * objektu tejto triedy a v zavere pre ziskanie celkoveho poctu bodov hraca
	 * v tejto kategorii.
	 * @param Nothing
	 * @return drinkScore
	 */
	public int getDrinkScore() {
		return drinkScore;
	}
}