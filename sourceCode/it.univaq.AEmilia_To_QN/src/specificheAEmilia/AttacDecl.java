package specificheAEmilia;

/**
 * Rappresenta una dichiarazione di collegamento tra istanze di
 * elementi architetturali ed ha la seguente forma sintattica:
 * <pre>
 * <code>
 * &lt;architectural_attachment_decl&gt; ::=
 * "FROM" &lt;identifier&gt; ["[" &lt;expr&gt; "]"] "." &lt;identifier&gt;
 * "TO" &lt;identifier&gt; ["[" &lt;expr&gt; "]"] "." &lt;identifier&gt;
 * | "FOR_ALL" &lt;identifier&gt; "IN" &lt;expr&gt; ".." &lt;expr&gt;
 * ["AND" "FOR_ALL" &lt;identifier&gt; "IN" &lt;expr&gt; ".." &lt;expr&gt;]
 * "FROM" &lt;identifier&gt; ["[" &lt;expr&gt; "]"] "." &lt;identifier&gt;
 * "TO" &lt;identifier&gt; ["[" &lt;expr&gt; "]"] "." &lt;identifier&gt;
 * </code>
 * </pre>
 * Nella sua forma più semplice, una dichiarazione di collegamento
 * architetturale contiene l'indicazione di un'interazione di
 * output seguita dall'indicazione di un'interazione di input.
 * Ognuna delle due interazioni è espressa in una notazione
 * puntata attraverso l'identificatore dell' AEI a cui
 * l'interazione appartiene, un'espressione intera racchiusa
 * tra parentesi quadre, che rappresenta un selettore e deve
 * essere privo di identificatori non dichiarati e invocazioni
 * a generatori di numeri pseudo-casuali, e l'identificatore
 * dell'interazione. L'interazione non deve essere di tipo
 * architetturale.
 * I due AEI devono essere diversi tra loro. Almeno una delle due
 * interazioni deve essere una uni-interaction, e almeno una di
 * loro deve essere un'azione passiva all'interno del comportamento
 * dell'AEI a cui appartiene. I soli identificatori che si possono
 * presentare nell'espressione di selezione possibile sono quelli
 * dei parametri formali costanti dichiarati nell'intestazione del
 * tipo architetturale. La seconda forma è utile per dichiarare
 * in modo conciso diversi collegamenti architetturali attraverso
 * un meccanismo di indicizzazione. Questo richiede la specifica
 * aggiuntiva di fino a due identificatori di indice differenti,
 * che si possono presntare nell'espressioni di selezione, insieme
 * con i loro intervalli, ognuno dei quali è dato da due
 * espressioni intere. Queste due espressioni devono essere prive
 * di identificari non dichiarati e invocazioni di numeri
 * generatori di pseudo-casuali, con il valore della prima
 * espressione non piÃ¹ grande del valore della seconda espressione.
 * Tutte le uni-interazioni attacate alla stessa and o or
 * interazione deve appartenere a AEI differenti. Tra tutte le
 * uni-interazioni attaccate alla stessa and-interazione passiva,
 * al piÃ¹ una può essere un'azione non passiva nel comportamento
 * dell'AEI al quale appartiene.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public class AttacDecl {

	/**
	 * Nome dell'istanza di un elemento architetturale
	 * dalla quale parte il collegamento.
	 */
	private String outputAei;

	/**
	 * Selettore dell'istanza di un elemento architetturale
	 * dal quale parte il collegamento.
	 */
	private Expression fromSelector;

	/**
	 * Nome dell'interazione di output appartenente
	 * all'istanza dell'elemento architetturale al quale
	 * arriva il collegamento.
	 */
	private String inputInteraction;

	/**
	 * Nome dell'istanza di un elemento architetturale
	 * alla quale arriva il collegamento.
	 */
	private String inputAei;

	/**
	 * Selettore dell'istanza di un elemento architetturale
	 * alla quale arriva il collegamento.
	 */
	private Expression toSelector;

	/**
	 * Nome dell'interazione di input appartenente
	 * all'istanza dell'elemento architetturale dal quale
	 * parte il collegamento.
	 */
	private String outputInteraction;

	/**
	 * Crea un oggetto AttacDecl vuoto.
	 *
	 */
	public AttacDecl() {
	}
	/**
	 * Crea un oggetto AttacDecl con le informazioni fornite
	 * come parametri al costruttore.
	 * @param outputAei - oggetto String indicante l'istanza
	 * di un elemento architetturale da cui nasce il
	 * collegamento.
	 * @param fromSelector - oggetto Expression per la selezione
	 * di un'istanza di elemento architetturale.
	 * @param inputInteraction - oggetto String che indica
	 * l'interazione di input.
	 * @param inputAei - oggetto String indicante l'istanza
	 * di un elemento architetturale in cui termina il
	 * collegamento.
	 * @param toSelector - oggetto Expression per la selezione
	 * di un'istanza di elemento architetturale.
	 * @param outputInteraction - oggetto String che indica
	 * l'interazione di output.
	 */
	public AttacDecl(String outputAei, Expression fromSelector, 
			String inputInteraction, String inputAei, Expression toSelector, 
			String outputInteraction) 
		{
		// assegna l'aei di partenza
		this.outputAei = outputAei;
		// assegna il selettore degli aei di partenza
		this.fromSelector = fromSelector;
		// assegna il nome dell'interazione di input
		this.inputInteraction = inputInteraction;
		// assegna l'aei di arrivo
		this.inputAei = inputAei;
		// assegna il selettore degli aei di arrivo
		this.toSelector = toSelector;
		// assegna il nome delle interazioni di output
		this.outputInteraction = outputInteraction;
	}

	/**
	 * Restituisce l'istanza di un elemento architetturale
	 * dalla quale parte il collegamento.
	 * @return oggetto String indicante un'istanza di un
	 * elemento architetturale.
	 */
	public String getOutputAei() {
		// resttuisce l'aei di partenza
		return this.outputAei;
	}

	/**
	 * Restituisce l'azione di un'istanza di un elemento
	 * architetturale corrispondente all'interazione
	 * di input.
	 * @return oggetto String che indica un'interazione di
	 * input.
	 */
	public String getInputInteraction() {
		// restituisce il nome dell'interazione di input
		return this.inputInteraction;
	}

	/**
	 * Restituisce l'azione di un'istanza di un elemento
	 * architetturale corrispondente all'interazione
	 * di output.
	 * @return oggetto String che indica un'interazione di
	 * output.
	 */
	public String getOutputInteraction() {
		// resttuisce il nome dell'interazione di output
		return this.outputInteraction;
	}

	/**
	 * Restituisce il selettore dell'istanza di output
	 * per il collegamento.
	 * @return oggetto Expression per la selezione di
	 * un'istanza di un elemento architetturale.
	 */
	public Expression getFromSelector() {
		// restituisce il selettore degli aei di partenza
		return this.fromSelector;
	}

	/**
	 * Restituisce il selettore dell'istanza di input
	 * per il collegamento.
	 * @return oggetto Expression per la selezione di
	 * un'istanza di un elemento architetturale.
	 */
	public Expression getToSelector() {
		// restituisce il selettore degli aei di arrivo
		return this.toSelector;
	}

	/**
	 * Restituisce l'istanza di un elemento architetturale
	 * alla quale arriva il collegamento.
	 * @return oggetto String indicante un'istanza di un
	 * elemento architetturale.
	 */
	public String getInputAei() {
		// restituisce l'aei di arrivo
		return this.inputAei;
	}

	/**
	 * Imposta una nuova istanza di un elemento architetturale
	 * dalla quale parte il collegamento.
	 * @param outputAei - oggetto String che indica un'istanza di
	 * un elemento architetturale.
	 */
	public void setOutputAei(String outputAei) {
		// assegna l'aei di partenza
		this.outputAei = outputAei;
	}

	/**
	 * Imposta una nuova interazione di input per il
	 * collegamento.
	 * @param inputInteraction - oggetto String indicante
	 * un'interazione di input.
	 */
	public void setInputInteraction(String inputInteraction) {
		// assegna il nome dell'interazione di input
		this.inputInteraction = inputInteraction;
	}

	/**
	 * Imposta una nuova interazione di output per il
	 * collegamento.
	 * @param outputInteraction - oggetto String indicante
	 * un'interazione di output.
	 */
	public void setOutputInteraction(String outputInteraction) {
		// assegna il nome dell'interazione di output
		this.outputInteraction = outputInteraction;
	}

	/**
	 * Imposta un nuovo selettore per l'istanza di un elemento
	 * architetturale di output.
	 * @param fromSelector - oggetto Expression indicante
	 * un selettore di istanze di elementi architetturali.
	 */
	public void setFromSelector(Expression fromSelector) {
		// assegna il selettore degli aei di partenza
		this.fromSelector = fromSelector;
	}

	/**
	 * Imposta un nuovo selettore per l'istanza di un elemento
	 * architetturale di input.
	 * @param toSelector - oggetto Expression indicante
	 * un selettore di istanze di elementi architetturali.
	 */
	public void setToSelector(Expression toSelector) {
		// assegna il selettore degli aei di arrivo
		this.toSelector = toSelector;
	}

	/**
	 * Imposta una nuova istanza di un elemento architetturale
	 * alla quale arriva il collegamento.
	 * @param inputAei - oggetto String che indica un'istanza di
	 * un elemento architetturale.
	 */
	public void setInputAei(String inputAei) {
		// assegna l'aei di arrivo
		this.inputAei = inputAei;
	}

	/**
	 * Stampa sullo standard output le informazione relative
	 * a questo oggetto.
	 *
	 */
	public void print()
		{
		System.out.println("AttacDecl object");
		System.out.println("Output AEIs: "+getOutputAei());
		// i selettori possono essere opzionali e quindi non
		// devono essere stampati se il loro valore è null
		if (getFromSelector() != null)
			{
			System.out.println("Output AEIs selector:");
			getFromSelector().print();
			}
		System.out.println("Output interaction: "+getOutputInteraction());
		System.out.println("Input AEIs: "+getInputAei());
		if (getToSelector() != null)
			{
			System.out.println("Input AEIs selector:");
			getToSelector().print();
			}
		System.out.println("Input interaction: "+getInputInteraction());
		}

	/**
	 * Verifica se questo oggetto è uguale al parametro di
	 * tale metodo.
	 * @param ad - oggetto AttacDecl da confrontare.
	 * @return un valore booleano indicante se i due oggetti
	 * hanno le stesse informazioni.
	 */
	/*
	 * I campi della classe da equiparare sono:
	 *
	 * private String outputAei;
	 * private Expression fromSelector;
	 * private String inputInteraction;
	 * private String inputAei;
	 * private Expression toSelector;
	 * private String outputInteraction;
	 */

	public boolean equals(Object o)
		{
		if (!(o instanceof AttacDecl)) return false;
		AttacDecl ad = (AttacDecl)o;
		boolean ris = getOutputAei().equals(ad.getOutputAei());
		// confrontare opportunamente i
		// campi opzionali dei selettori
		if (getFromSelector() != null && ad.getFromSelector() != null)
			ris = ris && getFromSelector().equals(ad.getFromSelector());
		else if (getFromSelector() == null && ad.getFromSelector() == null)
			ris = ris && true;
		else ris = ris && false;
		ris = ris && getInputInteraction().equals(ad.getInputInteraction());
		ris = ris && getOutputInteraction().equals(ad.getOutputInteraction());
		if (getToSelector() != null && ad.getToSelector() != null)
			ris = ris && getToSelector().equals(ad.getToSelector());
		else if (getToSelector() == null && ad.getToSelector() == null)
			ris = ris && true;
		else ris = ris && false;
		ris = ris && getInputAei().equals(ad.getInputAei());
		return ris;
		}

	/**
	 * Copia i dati contenuti in questo oggetto.
	 * @return un reference ad una copia di questo oggetto.
	 */
	public AttacDecl copy()
		{
		AttacDecl a = new AttacDecl();
		a.setOutputAei(new String(getOutputAei()));
		a.setInputInteraction(new String(getInputInteraction()));
		a.setOutputInteraction(new String(getOutputInteraction()));
		if (getFromSelector() != null)
		a.setFromSelector(getFromSelector().copy());
		if (getToSelector() != null)
		a.setToSelector(getToSelector().copy());
		a.setInputAei(new String(getInputAei()));
		return a;
		}
	@Override
	public String toString() 
		{
		String string = "FROM " + this.outputAei + (this.fromSelector == null ? 
				"" : "[" + this.fromSelector.toString() + "]") + "." + this.outputInteraction + " ";
		string = string + "TO " + this.inputAei + (this.toSelector == null ? 
				"" : "[" + this.toSelector.toString() + "]") + "." + this.inputInteraction;
		return string;
		}
	}