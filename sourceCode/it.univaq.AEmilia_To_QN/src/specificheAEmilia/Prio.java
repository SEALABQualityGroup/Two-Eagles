package specificheAEmilia;

/**
 * Il tipo prio denota l'insieme delle priorità  delle azioni
 * passive e immediate, che coincide con l'insieme degli interi
 * positivi.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public class Prio extends Expression 
	{

	/**
	 * Valore del tipo di dato.
	 */
	// il valore di un tipo di dato è utile per la valutazione
	// di una costante o variabile
	private int Valore;

	/**
	 * Crea un oggetto Prio vuoto.
	 *
	 */
	public Prio() {
	}

	/**
	 * Crea un oggetto Prio con valore fornito come parametro.
	 * @param valore - numero intero.
	 */
	public Prio(int valore) {
		this.Valore = valore;
	}

	/**
	 * Restituisce il suo valore.
	 * @return un numero intero.
	 */
	public int getValore()
		{
		return this.Valore;
		}

	/**
	 * Assegna un valore a questo tipo di dato.
	 * @param valore - un numero intero.
	 */
	public void setValore(int valore) {
		this.Valore = valore;
	}

	/**
	 * Stampa sullo standard output le informazioni
	 * relative a questo oggetto.
	 */
	public void print()
		{
		System.out.println("Prio object");
		super.print();
		System.out.print("Value: ");
		System.out.println(getValore());
		}

	/**
	 * Restituisce true se e solo se questo oggetto è uguale
	 * a p.
	 * @param p - oggetto Prio.
	 * @return un valore booleano.
	 */
	/*
	 * I campi della classe da equiparare sono:
	 *
	 * private int Valore;
	 */

	public boolean equals(Object o)
		{
		if (!(o instanceof Expression)) return false;
		Expression p = (Expression)o;
		try {
			boolean ris = super.equals(p);
			ris = ris && (getValore() == ((Prio)p).getValore());
			return ris;
			}
		catch (ClassCastException e)
			{
			return false;
			}
		}

	/**
	 * Copia i dati contenuti in questo oggetto.
	 * @return un reference ad una copia di questo oggetto.
	 */
	public Prio copy()
		{
		Prio a = new Prio();
		a.setValore(getValore());
		return a;
		}

	@Override
	public String toString() 
		{
		String string = new java.lang.Integer(this.Valore).toString();
		return string;
		}

	@Override
	public boolean isLiteral() 
		{
		return true;
		}
	}
