package specificheAEmilia;

/**
 * Rappresenta una dichiarazione di interazione architetturale.
 * In AEmilia, ha la seguente forma:
 * <pre>
 * <code>
 * &lt;architectural_interaction_decl&gt; ::=
 * &lt;identifier&gt; ["[" &lt;expr&gt; "]"] "." &lt;identifier&gt;
 * | "FOR_ALL" &lt;identifier&gt; "IN" &lt;expr&gt; ".." &lt;expr&gt;
 * &lt;identifier&gt; "[" &lt;expr&gt; "]" "." &lt;identifier&gt;
 * </code>
 * </pre>
 * Nella sua forma più semplice, una dichiarazione di interazione
 * architetturale contiene l'identificatore di un aei a cui
 * l'interazione appartiene, una possibile espressione intera
 * racchiusa tra parentesi quadre, che rappresenta un selettore e
 * deve essere privo di identificatori non dichiarati e
 * invocazioni a generatori di numeri pseudo-casuali, e
 * l'identificatore dell'interazione. I soli identificatori che
 * si possono presentare nella possibile espressione di selezione
 * sono quelli dei parametri formali costanti dichiarati
 * nell'intestazione del tipo architetturale. La seconda forma
 * è utile per dichiarare in modo conciso diverse interazioni
 * architetturali attraverso un meccanismo di indicizzazione.
 * Questo richiede la specifica dell'identificatore indice,
 * che si può successivamente presentare nell'espressione di
 * selezione, insieme con il suo intervallo, che è dato da due
 * espressioni intere. Queste due espressioni devono essere prive
 * da identificatori non dichiarati e invocazioni a generatori di
 * numeri pseudo-casuali, con il valore della prima espressione
 * non pià¹ grande del valore della seconda espressione.

 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public class InteractionDecl {

	/**
	 * Istanza di un elemento architetturale che interagisce
	 * con l'esterno.
	 */
	private String aei;

	/**
	 * selector di un'istanza di un elemento architetturale.
	 */
	private Expression selector;

	/**
	 * Nome dell'azione che costituisce l'interazione
	 * architetturale.
	 */
	private String interaction;

	/**
	 * Crea un oggetto InteractionDecl vuoto.
	 *
	 */
	public InteractionDecl() {
	}

	/**
	 * Crea un oggetto InteractionDecl con le iformazioni
	 * necessarie fornite come parametri.
	 * @param aei - oggetto String.
	 * @param selector - oggetto Expression.
	 * @param interaction - oggetto String.
	 */
	public InteractionDecl(String aei, Expression selector, String interaction) {
		this.aei = aei;
		this.selector = selector;
		this.interaction = interaction;
	}

	/**
	 * Restituisce l'aei di apparetenenza dell'interazione.
	 * @return oggetto String.
	 */
	public String getAei() {
		return this.aei;
	}

	/**
	 * Restituisce il nome dell'azione che costituisce
	 * l'interazione con l'esterno rispetto al tipo
	 * architetturale specificato in AEmilia.
	 * @return oggetto String.
	 */
	public String getInteraction() {
		return this.interaction;
	}

	/**
	 * Restituisce il selettore di un'istanza di elemento
	 * architetturale.
	 * @return oggetto Expression.
	 */
	public Expression getSelector() {
		return this.selector;
	}

	/**
	 * Assegna il nome dell'aei di appartenenza dell'interazione.
	 * @param aei - oggetto String.
	 */
	public void setAei(String aei) {
		this.aei = aei;
	}

	/**
	 * Assegna il nome dell'azione che costituisce l'interazione
	 * architetturale.
	 * @param interaction - oggetto String.
	 */
	public void setInteraction(String interaction) {
		this.interaction = interaction;
	}

	/**
	 * Assegna un selettore di istanza di un elemento
	 * architetturale.
	 * @param selector - oggetto Expression.
	 */
	public void setSelector(Expression selector) {
		this.selector = selector;
	}

	/**
	 * Stampa sullo standard output le informazioni di
	 * un oggetto InteractionDecl.
	 *
	 */
	public void print()
		{
		System.out.println("InteractionDecl object");
		System.out.println("Membership aei: "+getAei());
		// il selettore è opzionale
		if (getSelector() != null)
			{
			System.out.println("Selector:");
			getSelector().print();
			}
		System.out.println("Declared interaction: "+getInteraction());
		}

	/**
	 * Restituisce true se e solo se id è uguale a questo oggetto.
	 * @param id - oggetto InteractionDecl.
	 * @return un valore booleano.
	 */
	/*
	 * I campi della classe da equiparare sono:
	 *
	 * private String aei;
	 * private Expression selector;
	 * private String interaction;
	 */

	public boolean equals(Object o)
		{
		if (!(o instanceof InteractionDecl)) return false;
		InteractionDecl id = (InteractionDecl)o;
		boolean ris = getAei().equals(id.getAei());
		// il selettore è opzionale
		if (getSelector() != null && id.getSelector() != null)
			ris = ris && getSelector().equals(id.getSelector());
		else if (getSelector() == null && id.getSelector() == null)
			ris = ris && true;
		else ris = ris && false;
		ris = ris && getInteraction().equals(id.getInteraction());
		return ris;
		}

	/**
	 * Copia i dati contenuti in questo oggetto.
	 * @return un reference ad una copia di questo oggetto.
	 */
	public InteractionDecl copy()
		{
		InteractionDecl a = new InteractionDecl();
		a.setAei(new String(getAei()));
		a.setInteraction(new String(getInteraction()));
		if (getSelector() != null)
		a.setSelector(getSelector().copy());
		return a;
		}

	@Override
	public String toString() 
		{
		String string = this.aei + (this.selector == null ? "" : "[" + this.selector.toString() + "]") +
			"." + this.interaction;
		return string;
		}
	}