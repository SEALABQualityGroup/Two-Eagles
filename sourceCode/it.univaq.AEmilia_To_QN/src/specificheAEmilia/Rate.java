package specificheAEmilia;

/**
 * Il tipo rate denota l'insieme dei tassi azioni temporizzate
 * esponenzialmente, che coincide con l'insieme dei reali positivi.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public class Rate extends Expression
	{

	/**
	 * Valore del tipo di dato.
	 */
	// il valore di un tipo di dato è utile per la valutazione
	// di una costante o variabile
	private double Valore;

	/**
	 * Crea un oggetto Rate vuoto.
	 *
	 */
	public Rate() {
	}

	/**
	 * Crea un oggetto Rate con valore fornito come parametro.
	 * @param valore - valore reale.
	 */
	public Rate(double valore) {
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
		System.out.println("Rate object");
		super.print();
		System.out.print("Value: ");
		System.out.println(getValore());
		System.out.println();
		}

	/**
	 * Restituisce true se e solo se questo oggetto è uguale a r.
	 * @param r - oggetto Rate.
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
		Expression r = (Expression)o;
		try {
			boolean ris = super.equals(r);
			ris = ris && (getValore() == ((Rate)r).getValore());
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
	public Rate copy()
		{
		Rate a = new Rate();
		a.setValore(getValore());
		return a;
		}

	@Override
	public String toString() 
		{
		String string = new Double(this.Valore).toString();
		return string;
		}

	@Override
	public boolean isLiteral() 
		{
		return true;
		}
	}