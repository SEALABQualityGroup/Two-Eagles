package specificheAEmilia;

/**
 * Rappresenta un'operatore booleano di maggiore o uguale.
 * In AEmilia ha la seguente sintassi:
 * <pre>
 * <code>
 * &lt;expr&gt; ">=" &lt;expr&gt;
 * </code>
 * </pre>
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */

public class MaggioreUguale extends Expression 
	{

	/**
	 * Primo operando.
	 */
	private Expression Expr1;

	/**
	 * Secondo operando.
	 */
	private Expression Expr2;

	/**
	 * Crea un oggetto MaggioreUguale vuoto.
	 *
	 */
	public MaggioreUguale() {
		}

	/**
	 * Crea un oggetto MaggioreUguale con gli operandi forniti
	 * come parametri.
	 * @param Expr1 - oggetto Expression.
	 * @param Expr2 - oggetto Expression.
	 */
	public MaggioreUguale(Expression Expr1, Expression Expr2) {
		this.Expr1 = Expr1;
		this.Expr2 = Expr2;
		}

	/**
	 * Restituisce il primo operando.
	 * @return un oggetto Expression.
	 */
	public Expression getExpr1() {
		return this.Expr1;
	}

	/**
	 * Restituisce il secondo operando.
	 * @return un oggetto Expression.
	 */
	public Expression getExpr2() {
		return this.Expr2;
	}

	/**
	 * Assegna il primo operando a questa operazione.
	 * @param expr1 - oggetto Expression.
	 */
	public void setExpr1(Expression expr1) {
		this.Expr1 = expr1;
	}

	/**
	 * Assegna il secondo operando a questa operazione.
	 * @param expr2 - oggetto Expression.
	 */
	public void setExpr2(Expression expr2) {
		this.Expr2 = expr2;
	}

	/**
	 * Stampa sullo standard output le informazioni relative
	 * a questo oggetto.
	 */
	public void print()
		{
		System.out.println("MaggioreUguale object");
		super.print();
		System.out.println("Expressions operands:");
		System.out.println("Operand 1:");
		getExpr1().print();
		System.out.println("Operand 2:");
		getExpr2().print();
		}

	/**
	 * Restituisce true se e solo se m contiene le stesse
	 * informazioni di questo oggetto.
	 * @param m - oggetto MaggioreUguale.
	 * @return un valore booleano.
	 */
	/*
	 * I campi della classe da equiparare sono:
	 *
	 * private Expression Expr1;
	 * private Expression Expr2;
	 */

	public boolean equals(Object o)
		{
		if (!(o instanceof MaggioreUguale)) return false;
		MaggioreUguale mu = (MaggioreUguale)o;
		boolean ris = super.equals(mu);
		ris = ris && getExpr1().equals(mu.getExpr1());
		ris = ris && getExpr2().equals(mu.getExpr2());
		return ris;
		}

	/**
	 * Copia i dati contenuti in questo oggetto.
	 * @return un reference ad una copia di questo oggetto.
	 */
	public MaggioreUguale copy()
		{
		MaggioreUguale a = new MaggioreUguale();
		if (getExpr1() != null)
		a.setExpr1(getExpr1().copy());
		if (getExpr2() != null)
		a.setExpr2(getExpr2().copy());
		return a;
		}

	@Override
	public String toString() 
		{
		String string = (this.Expr1.isLiteral() ? this.Expr1.toString() : "(" + this.Expr1.toString() + ")") 
			+ " >= " + (this.Expr2.isLiteral() ? this.Expr2.toString() : "(" + this.Expr2.toString() + ")");
		return string;
		}

	@Override
	public boolean isLiteral() 
		{
		return false;
		}
	}