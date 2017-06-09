package specificheAEmilia;

/**
 * Rappresenta le interazioni di input che possono essere
 * presenti all'interno di una dichiarazione di un AET.
 * La sintassi in AEmilia è la seguente:
 * <pre>
 * <code>
 * "INPUT_INTERACTIONS" &lt;input_interactions&gt;
 * </code>
 * </pre>
 * Un'interazione di input è classificata essere una
 * uni-interaction, and-interaction o or-interaction
 * dipendente dalla moltiplicità  delle comunicazioni
 * in cui può essere coinvolta.
 * Sintatticamente parlando, ogni &lt;input_interactions&gt; è o void
 * o ha il seguente formato:
 * <pre>
 * <code>
 * &lt;uni_interactions&gt; &lt;and_interactions&gt; &lt;or_interactions&gt;
 * </code>
 * </pre>
 * con almeno uno dei tre elementi, che rappresenta sequenze
 * di identificatori di tipo azione, essere non vuoti.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public class InputInteractions extends Interactions
	{

	/**
	 * Crea un oggetto InputInteractions vuoto.
	 *
	 */
	public InputInteractions() {
	}

	/**
	 * Crea un oggetto InputInteractions con i tipi di interazione
	 * forniti come parametri al metodo.
	 * @param uni - oggetto UNIinteractions.
	 * @param or - oggetto ORinteractions.
	 * @param and - oggetto ANDinteractions.
	 */
	public InputInteractions(UNIinteractions uni, ORinteractions or, ANDinteractions and) 
		{
		this.setUni(uni);
		this.setOr(or);
		this.setAnd(and);
		}

	/**
	 * Stampa sullo standard output le informazioni relative a
	 * questo oggetto.
	 *
	 */
	public void print()
		{
		System.out.println("InputInteractions object");
		// stampa le informazioni contenute nella superclasse
		super.print();
		}

	/**
	 * Confronta se questo oggetto è uguale a quello referenziato
	 * dal parametro del metodo.
	 * @param ii - oggetto InputInteractions.
	 * @return true se e solo se i due oggetti contengono
	 * le stesse informazioni.
	 */
	/*
	 * I campi della classe da equiparare sono:
	 *
	 * private UNIinteractions Uni;
	 * private ORinteractions Or;
	 * private ANDinteractions And;
	 */

	public boolean equals(Object o)
		{
		if (!(o instanceof InputInteractions)) return false;
		InputInteractions ii = (InputInteractions)o;
		boolean ris = true;
		// possono non esserci and-interazioni
		if (getAnd() != null && ii.getAnd() != null)
			ris = getAnd().equals(ii.getAnd());
		else if (getAnd() == null && ii.getAnd() == null)
			ris = ris && true;
		else ris = ris && false;
		// possono non esserci or-interazioni
		if (getOr() != null && ii.getOr() != null)
			ris = getOr().equals(ii.getOr());
		else if (getOr() == null && ii.getOr() == null)
			ris = ris && true;
		else ris = ris && false;
		// possono non esserci uni-interazioni
		if (getUni() != null && ii.getUni() != null)
			ris = getUni().equals(ii.getUni());
		else if (getUni() == null && ii.getUni() == null)
			ris = ris && true;
		else ris = ris && false;
		return ris;
		}

	/**
	 * Copia i dati contenuti in questo oggetto.
	 * @return un reference ad una copia di questo oggetto.
	 */
	public InputInteractions copy()
		{
		InputInteractions a = new InputInteractions();
		if (getAnd() != null)
			a.setAnd(getAnd().copy());
		if (getOr() != null)
			a.setOr(getOr().copy());
		if (getUni() != null)
			a.setUni(getUni().copy());
		return a;
		}

	@Override
	public String toString() 
		{
		String string = "INPUT_INTERACTIONS ";
		string = string + super.toString();
		return string;
		}
	}