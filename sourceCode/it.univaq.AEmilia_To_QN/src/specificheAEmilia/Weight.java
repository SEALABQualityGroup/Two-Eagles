package specificheAEmilia;

/**
 * Il tipo weight denota l'insieme dei pesi azioni passive e
 * immediate, che coincidono con l'insieme dei reali positivi.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public class Weight extends Expression 
	{

	/**
	 * Valore del tipo di dato.
	 */
	// il valore di un tipo di dato è utile per la valutazione
	// di una costante o variabile
	private double Valore;

	/**
	 * Crea un oggetto Weight vuoto.
	 *
	 */
	public Weight() {
	}

	/**
	 * Crea un oggetto Weight con valore fornito come parametro.
	 * @param valore - valore reale.
	 */
	public Weight(double valore) {
		this.Valore = valore;
	}

	/**
	 * Restituisce il valore del tipo di dato.
	 * @return un numero reale.
	 */
	public double getValore()
		{
		return this.Valore;
		}

	/**
	 * Assegna un valore a questo tipo di dato.
	 * @param valore - numero reale.
	 */
	public void setValore(double valore) {
		this.Valore = valore;
	}

	/**
	 * Stampa sullo standard output le informazioni relative
	 * a questo oggetto.
	 */
	public void print()
		{
		System.out.println("Weight object");
		super.print();
		System.out.print("Value: ");
		System.out.println(getValore());
		System.out.println();
		}

	/**
	 * Restituisce true se e solo se questo oggetto è uguale a w.
	 * @param w - oggetto Weight.
	 * @return un valore booleano.
	 */
	/*
	 * I campi della classe da equiparare sono:
	 *
	 * private float Valore;
	 */

	public boolean equals(Object o)
		{
		if (!(o instanceof Expression)) return false;
		Expression w = (Expression)o;
		try {
			boolean ris = super.equals(w);
			ris = ris && (getValore() == ((Weight)w).getValore());
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
	public Weight copy()
		{
		Weight a = new Weight();
		a.setValore(getValore());
		return a;
		}

	@Override
	public String toString() 
		{
		return new Double(this.Valore).toString();
		}

	@Override
	public boolean isLiteral() 
		{
		return true;
		}
	}