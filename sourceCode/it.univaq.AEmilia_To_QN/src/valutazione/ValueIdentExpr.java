package valutazione;

/**
 * Viene utilizzata come valore per un identificatore presente in una specifica
 * AEmilia e contiene: un oggetto valore che può essere Integer, Double, Boolean o Expression e
 * rappresenta un valore per un'identificatore; un booleano valutazione che se vale false vuol dire
 * che l'identificatore non può essere valutato.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
public class ValueIdentExpr {

	private Object valore;
	private boolean valutazione;

	public ValueIdentExpr(Object valore, boolean valutazione)
		{
		super();
		this.valore = valore;
		this.valutazione = valutazione;
		}

	public Object getValore()
		{
		return valore;
		}

	public void setValore(Object valore)
		{
		this.valore = valore;
		}

	public boolean isValutazione()
		{
		return valutazione;
		}

	public void setValutazione(boolean valutazione)
		{
		this.valutazione = valutazione;
		}

	public boolean equals(Object o)
		{
		if (!(o instanceof ValueIdentExpr)) return false;
		ValueIdentExpr vie = (ValueIdentExpr)o;
		if (getValore() == null && vie.getValore() == null) return true;
		if (getValore() != null && vie.getValore() == null) return false;
		if (getValore() == null && vie.getValore() != null) return false;
		if (!getValore().equals(vie.getValore())) return false;
		if (isValutazione() != vie.isValutazione()) return false;
		return true;
		}
}
