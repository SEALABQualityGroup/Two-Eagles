package valutazione;

import specificheAEmilia.AEIdeclInd;
import specificheAEmilia.ActionRate;
import specificheAEmilia.AttacDeclInd;
import specificheAEmilia.Boolean;
import specificheAEmilia.Divisione;
import specificheAEmilia.Expression;
import specificheAEmilia.Integer;
import specificheAEmilia.InteractionDeclInd;
import specificheAEmilia.Moltiplicazione;
import specificheAEmilia.RateInf;
import specificheAEmilia.Rate_;
import specificheAEmilia.Real;
import specificheAEmilia.Somma;
import specificheAEmilia.Sottrazione;

/**
 * Classe utilizzate per verificare i seguenti requisiti di interezza:
 * - La priorità  del tasso di un'azione immediata deve essere intero.
 * - La priorità  del tasso di un'azione passiva deve essere intero.
 * - L'intervallo di indicizzazione per la dichiarazione di collegamenti
 * indicizzata deve essere definito da due espressioni intere.
 * - L'intervallo di indicizzazione per la dichiarazione di interazioni
 * indicizzata deve essere definito da due espressioni intere.
 * - Un intervallo di interi deve essere specificato da due espressioni intere.
 * - L'intervallo di indicizzazione per la dichiarazione di istanze
 * indicizzata deve essere definito da due espressioni intere.
 *
 * @author Mirko
 */

public class Interezza {

	/**
	 * Restituisce true se e solo se e è un'espressione intera.
	 * 
	 * @param e
	 * @return
	 */
	public boolean isIntero(Expression e)
		{
		boolean ris = false;
		if (e instanceof Boolean) ris = false;
		else if (e instanceof Integer) ris = true;
		else if (e instanceof Divisione) ris = false;
		else if (e instanceof Moltiplicazione) ris = false;
		else if (e instanceof Somma) ris = false;
		else if (e instanceof Sottrazione) ris = false;
		else if (e instanceof Real)
			{
			Double double1 = ((Real)e).getValore();
			if (Math.rint(double1) == double1)
				ris = true;
			else ris = false;
			}
		return ris;
		}

	/**
	 * Restituisce l'intero corrispondente a e.
	 * 
	 * @param e
	 * @return
	 * @throws InterezzaException
	 */
	public Integer returnIntero(Expression e) throws InterezzaException
		{
		Integer ris = new Integer();
		if (e instanceof Boolean)
			throw new InterezzaException("Expression is not a integer");
		else if (e instanceof Integer)
			ris = (Integer)e;
		else if (e instanceof Divisione)
			throw new InterezzaException("Expression is not a integer");
		else if (e instanceof Moltiplicazione)
			throw new InterezzaException("Expression is not a integer");
		else if (e instanceof Somma)
			throw new InterezzaException("Expression is not a integer");
		else if (e instanceof Sottrazione)
			throw new InterezzaException("Expression is not a integer");
		else if (e instanceof Real)
			{
			Double double1 = ((Real)e).getValore();
			if (Math.rint(double1) == double1)
				ris = new Integer(double1.intValue());
			else
				throw new InterezzaException("Expression is not a integer");
			}
		return ris;
		}

	/**
	 * Restituisce true se e solo se ar ha una priorità intera.
	 * 
	 * @param ar
	 * @return
	 */
	public boolean isPrioritaAzioneIntera(ActionRate ar)
		{
		boolean b = false;
		if (ar instanceof RateInf)
			{
			Expression espressione = ((RateInf)ar).getPrio();
			b = isIntero(espressione);
			if (isIntero(espressione))
				try {
					((RateInf)ar).setPrio(returnIntero(espressione));
					}
				catch (InterezzaException e)
					{}
			}
		if (ar instanceof Rate_)
			{
			Expression espressione = ((Rate_)ar).getPrio();
			b = isIntero(espressione);
			if (isIntero(espressione))
				try {
					((Rate_)ar).setPrio(returnIntero(espressione));
					}
				catch (InterezzaException e)
					{}
			}
		return b;
		}

	/**
	 * Restituisce true se e solo se attacDeclInd contiene espressioni intere nella definizione
	 * degli intervalli per l'indicizzazione.
	 * 
	 * @param attacDeclInd
	 * @return
	 */
	public boolean isRangesInteri(AttacDeclInd attacDeclInd)
		{
		boolean b = true;
		b = b && isIntero(attacDeclInd.getEndingExpr1());
		if (isIntero(attacDeclInd.getEndingExpr1()))
			try {
				attacDeclInd.setEndingExpr1(returnIntero(attacDeclInd.getEndingExpr1()));
				}
			catch (InterezzaException e)
				{}
		b = b && isIntero(attacDeclInd.getBeginningExpr1());
		if (isIntero(attacDeclInd.getBeginningExpr1()))
			try {
				attacDeclInd.setBeginningExpr1(returnIntero(attacDeclInd.getBeginningExpr1()));
				}
			catch (InterezzaException e)
				{}
		if (attacDeclInd.getEndingExpr2() != null)
			{
			b = b && isIntero(attacDeclInd.getEndingExpr2());
			if (isIntero(attacDeclInd.getEndingExpr2()))
				try {
					attacDeclInd.setEndingExpr2(returnIntero(attacDeclInd.getEndingExpr2()));
					}
				catch (InterezzaException e)
					{}
			}
		if (attacDeclInd.getBeginningExpr2() != null)
			{
			b = b && isIntero(attacDeclInd.getBeginningExpr2());
			if (isIntero(attacDeclInd.getBeginningExpr2()))
				try {
					attacDeclInd.setBeginningExpr2(returnIntero(attacDeclInd.getBeginningExpr2()));
					}
				catch (InterezzaException e)
					{}
			}
		return b;
		}

	/**
	 * Restituisce true se e solo se interactionDeclInd contiene due espressioni intere
	 * nella definizione dell'intervallo di indicizzazione.
	 * 
	 * @param interactionDeclInd
	 * @return
	 */
	public boolean isRangeIntero(InteractionDeclInd interactionDeclInd)
		{
		boolean ris = true;
		Expression espressione = interactionDeclInd.getEndingExpr();
		Expression espressione2 = interactionDeclInd.getBeginningExpr();
		ris = ris && isIntero(espressione);
		if (isIntero(espressione))
			try {
				interactionDeclInd.setExprFine(returnIntero(espressione));
				}
			catch (InterezzaException e)
				{}
		ris = ris && isIntero(espressione2);
		if (isIntero(espressione2))
			try {
				interactionDeclInd.setExprInizio(returnIntero(espressione2));
				}
			catch (InterezzaException e)
				{}
		return ris;
		}

	/**
	 * Restituisce true se e solo se ideclInd contiene 
	 * espressioni intere per la definizione dell'intervallo
	 * di indicizzazione.
	 * 
	 * @param ideclInd
	 * @return
	 */
	public boolean isRangeIntero(AEIdeclInd ideclInd)
		{
		boolean ris = true;
		Expression espressione = ideclInd.getEndingExpr();
		Expression espressione2 = ideclInd.getBeginningExpr();
		ris = ris && isIntero(espressione);
		if (isIntero(espressione))
			try {
				ideclInd.setEndingExpr(returnIntero(espressione));
				}
			catch (InterezzaException e)
				{}
		ris = ris && isIntero(espressione2);
		if (isIntero(espressione2))
			try {
				ideclInd.setBeginningExpr(returnIntero(espressione2));
				}
			catch (InterezzaException e)
				{}
		return ris;
		}
}
