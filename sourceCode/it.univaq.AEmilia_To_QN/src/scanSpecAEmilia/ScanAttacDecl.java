package scanSpecAEmilia;

import java.util.NoSuchElementException;

import personalScanner.MyScanner;

import specificheAEmilia.AttacDecl;
import specificheAEmilia.AttacDeclInd;

/**
 * Classe utilizzata per scannerizzare ogni parte della
 * dichiarazione di un collegamento tra elementi architetturali,
 * dettata dalla grammatica AEmilia.
 *
 * @author Mirko Email: <a href="mailto:mirkoflamminj@virgilio.it">mirkoflamminj@virgilio.it</a>
 * @version 1.0
 */
/*
 * Una dichiarazione di collegamento architetturale ha la seguente
 * forma:
 *
 * <architectural_attachment_decl> ::= "FROM" <identifier> ["[" <expr> "]"] "." <identifier>
 * "TO" <identifier> ["[" <expr> "]"] "." <identifier>
 * | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
 * ["AND" "FOR_ALL" <identifier> "IN" <expr> ".." <expr>]
 * "FROM" <identifier> ["[" <expr> "]"] "." <identifier>
 * "TO" <identifier> ["[" <expr> "]"] "." <identifier>
 *
 * Nella sua forma pià¹ semplice, una dichiarazione di collegamento
 * architetturale contiene l'indicazione di un'interazione di
 * output seguita dall'indicazione di un'interazione di input.
 * Ognuna delle due interazioni è espressa in una notazione puntata
 * attraverso l'identificatore dell' AEI a cui l'interazione
 * appartiene, un'espressione intera racchiusa tra parentesi
 * quadre, che rappresenta un selettore e deve essere privo di
 * identificatori non dichiarati e invocazioni a generatori di
 * numeri pseudo-casuali, e l'identificatore dell'interazione.
 * L'interazione non deve essere di tipo architetturale. I due
 * AEI devono essere diversi tra loro. Almeno una delle due
 * interazioni deve essere una uni-interaction, e almeno una di
 * loro deve essere un'azione passiva all'interno del
 * comportamento dell'AEI a cui appartiene. I soli identificatori
 * che si possono presentare nell'espressione di selezione
 * possibile sono quelli dei parametri formali costanti dichiarati
 * nell'intestazione del tipo architetturale. La seconda forma è
 * utile per dichiarare in modo conciso diversi collegamenti
 * architetturali attraverso un meccanismo di indicizzazione.
 * Questo richiede la specifica aggiuntiva di fino a due
 * identificatori di indice differenti, che si possono presntare
 * nell'espressioni di selezione, insieme con i loro intervalli,
 * ognuno dei quali è dato da due espressioni intere. Queste due
 * espressioni devono essere prive di identificari non dichiarati
 * e invocazioni di numeri generatori di pseudo-casuali, con il
 * valore della prima espressione non pià¹ grande del valore della
 * seconda espressione. Tutte le uni-interazioni attacate alla
 * stessa and o or interazione deve appartenere a AEI differenti.
 * Tra tutte le uni-interazioni attaccate alla stessa
 * and-interazione passiva, al pià¹ una può essere un'azione non
 * passiva nel comportamento dell'AEI al quale appartiene.
 */

public class ScanAttacDecl {

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * alla dichiarazione di un colleagamento tra elementi
	 * architetturali secondo la grammatica AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	public static boolean isAttacDecl(String specifiche)
		{
		/*
		 * Una dichiarazione di collegamento architetturale ha la seguente forma:
		 *
		 * <architectural_attachment_decl> ::= "FROM" <identifier> ["[" <expr> "]"] "." <identifier>
		 * "TO" <identifier> ["[" <expr> "]"] "." <identifier>
		 * | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
		 * ["AND" "FOR_ALL" <identifier> "IN" <expr> ".." <expr>]
		 * "FROM" <identifier> ["[" <expr> "]"] "." <identifier>
		 * "TO" <identifier> ["[" <expr> "]"] "." <identifier>
		 */
		return isAttacDeclP(specifiche) ||
		isAttacDeclInd(specifiche);
		}

	/**
	 * Crea un oggetto AttacDecl, includendo informazioni
	 * ottenute attraverso la scannerizzazione di specifiche.
	 * @param specifiche - oggetto String.
	 * @return un oggetto AttacDecl.
	 * @throws ScanException
	 */
	public static AttacDecl scanAttacDecl(String specifiche)
		throws ScanException
		{
		/*
		 * Una dichiarazione di collegamento architetturale ha la seguente forma:
		 *
		 * <architectural_attachment_decl> ::= "FROM" <identifier> ["[" <expr> "]"] "." <identifier>
		 * "TO" <identifier> ["[" <expr> "]"] "." <identifier>
		 * | "FOR_ALL" <identifier> "IN" <expr> ".." <expr>
		 * ["AND" "FOR_ALL" <identifier> "IN" <expr> ".." <expr>]
		 * "FROM" <identifier> ["[" <expr> "]"] "." <identifier>
		 * "TO" <identifier> ["[" <expr> "]"] "." <identifier>
		 */
		AttacDecl ad = new AttacDecl();
		if (isAttacDeclP(specifiche))
			ad = scanAttacDeclP(specifiche);
		else if (isAttacDeclInd(specifiche))
			ad = scanAttacDeclInd(specifiche);
		else throw new ScanException(specifiche + " is not architectural attachment declaration");
		return ad;
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * alla dichiarazione semplice di un collegamento tra elementi
	 * architetturali secondo la grammatica AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isAttacDeclP(String specifiche)
		{
		/*
		 * Una dichiarazione di collegamento architetturale ha la seguente forma:
		 *
		 * <architectural_attachment_decl> ::= "FROM" <identifier> ["[" <expr> "]"]
		 * "." <identifier> "TO" <identifier> ["[" <expr> "]"] "." <identifier>
		 */
		boolean ris = false;
		String FromAei = new String();
		String SF = new String();
		String InputInteraction = new String();
		String ToAei = new String();
		String ST = new String();
		String OutputInteraction = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*FROM\\s*");
			s.useDelimiter("\\s*\\.\\s*[a-zA-Z_&&[^0-9<>=!()]](\\w*)\\s*");
			// FromAei contiene l'Aei di partenza
			FromAei = s.next();
			// FromAei può contenere un selettore
			if (FromAei.contains("["))
				{
				MyScanner s1 = new MyScanner(FromAei);
				s1.useDelimiter("\\s*\\[\\s*");
				FromAei = s1.next();
				s1.useDelimiter("\\s*\\]\\s*");
				s1.skip("\\s*\\[\\s*");
				// SF è il selettore di istanze
				SF = s1.next();
				s1.skip("\\s*\\]\\s*");
				}
			s.useDelimiter("\\s*TO\\s*");
			s.skip("\\s*\\.\\s*");
			// OutputInteraction è l'interazione di output
			OutputInteraction = s.next();
			s.useDelimiter("\\s*\\.\\s*[a-zA-Z_&&[^0-9<>=!()]](\\w*)\\s*");
			s.skip("\\s*TO\\s*");
			// ToAei è l'istanza di arrivo
			ToAei = s.next();
			// ToAei può contenere un selettore
			if (ToAei.contains("["))
				{
				MyScanner s1 = new MyScanner(ToAei);
				s1.useDelimiter("\\s*\\[\\s*");
				ToAei = s1.next();
				s1.useDelimiter("\\s*\\]\\s*");
				s1.skip("\\s*\\[\\s*");
				// ST è il selettore di istanze
				ST = s1.next();
				s1.skip("\\s*\\]\\s*");
				}
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\.\\s*");
			// InputInteraction è l'interazione di input
			InputInteraction = s.next();
			ris = ScanIdent.isIdent(FromAei) &&
			ScanIdent.isIdent(OutputInteraction) &&
			ScanIdent.isIdent(ToAei) &&
			ScanIdent.isIdent(InputInteraction);
			if (!SF.equals("")) ris = ris &&
			ScanExp.isEspressione(SF);
			if (!ST.equals("")) ris = ris &&
			ScanExp.isEspressione(ST);
			return ris;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto AttacDecl come una dichiarazione semplice di
	 * un collegamento tra elementi architetturali, includendo
	 * informazioni ottenute attraverso la scannerizzazione di
	 * specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto AttacDecl.
	 * @throws ScanException
	 */
	private static AttacDecl scanAttacDeclP(String specifiche)
		throws ScanException
		{
		/*
		 * Una dichiarazione di collegamento architetturale ha la seguente forma:
		 *
		 * <architectural_attachment_decl> ::= "FROM" <identifier> ["[" <expr> "]"]
		 * "." <identifier> "TO" <identifier> ["[" <expr> "]"] "." <identifier>
		 */
		AttacDecl ad = new AttacDecl();
		String FromAei = new String();
		String SF = new String();
		String InputInteraction = new String();
		String ToAei = new String();
		String ST = new String();
		String OutputInteraction = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*FROM\\s*");
			s.useDelimiter("\\s*\\.\\s*[a-zA-Z_&&[^0-9<>=!()]](\\w*)\\s*");
			// istanza di arrivo del collegamento
			FromAei = s.next();
			// FromAei può contenere un selettore
			if (FromAei.contains("["))
				{
				MyScanner s1 = new MyScanner(FromAei);
				s1.useDelimiter("\\s*\\[\\s*");
				FromAei = s1.next();
				s1.useDelimiter("\\s*\\]\\s*");
				s1.skip("\\s*\\[\\s*");
				// selettore di istanze
				SF = s1.next();
				s1.skip("\\s*\\]\\s*");
				}
			s.useDelimiter("\\s*TO\\s*");
			s.skip("\\s*\\.\\s*");
			// OutputInteraction è l'interazione di output
			OutputInteraction = s.next();
			s.useDelimiter("\\s*\\.\\s*[a-zA-Z_&&[^0-9<>=!()]](\\w*)\\s*");
			s.skip("\\s*TO\\s*");
			// ToAei è l'istanza di arrivo
			ToAei = s.next();
			// ToAei può contenere un selettore
			if (ToAei.contains("["))
				{
				MyScanner s1 = new MyScanner(ToAei);
				s1.useDelimiter("\\s*\\[\\s*");
				ToAei = s1.next();
				s1.useDelimiter("\\s*\\]\\s*");
				s1.skip("\\s*\\[\\s*");
				ST = s1.next();
				s1.skip("\\s*\\]\\s*");
				}
			s.useDelimiter("\\s*\\z");
			s.skip("\\s*\\.\\s*");
			// InputInteraction è l'interazione di input
			InputInteraction = s.next();
			ad.setOutputAei(ScanIdent.scanIdent(FromAei));
			ad.setOutputInteraction(ScanIdent.scanIdent(OutputInteraction));
			ad.setInputAei(ScanIdent.scanIdent(ToAei));
			ad.setInputInteraction(ScanIdent.scanIdent(InputInteraction));
			if (!SF.equals("")) ad.setFromSelector(ScanExp.scanEspressione(SF));
			if (!ST.equals("")) ad.setToSelector(ScanExp.scanEspressione(ST));
			return ad;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not AttacDecl object",e);
			}
		}

	/**
	 * Restituisce true se e solo se specifiche corrisponde
	 * alla dichiarazione indicizzata di collegamenti tra elementi
	 * architetturali secondo la grammatica AEmilia.
	 *
	 * @param specifiche - oggetto String.
	 * @return un valore booleano.
	 */
	private static boolean isAttacDeclInd(String specifiche)
		{
		/*
		 * Una dichiarazione di collegamento architetturale ha la seguente forma:
		 *
		 * <architectural_attachment_decl> ::= "FOR_ALL" <identifier> "IN" <expr>
		 * ".." <expr> ["AND" "FOR_ALL" <identifier> "IN" <expr> ".." <expr>]
		 * "FROM" <identifier> ["[" <expr> "]"] "." <identifier>
		 * "TO" <identifier> ["[" <expr> "]"] "." <identifier>
		 */
		String indice1 = new String();
		String ExprInizio1 = new String();
		String ExprFine1 = new String();
		String indice2 = new String();
		String ExprInizio2 = new String();
		String ExprFine2 = new String();
		String r = new String();
		boolean ris = false;
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*FOR_ALL\\s*");
			s.useDelimiter("\\s*IN\\s*");
			// indice1 contiene il primo indice
			indice1 = s.next();
			s.useDelimiter("\\s*\\.\\.\\s*");
			s.skip("\\s*IN\\s*");
			// espressione iniziale del primo indice
			ExprInizio1 = s.next();
			s.useDelimiter("\\s*FROM\\s*");
			s.skip("\\s*\\.\\.\\s*");
			// espressione finale del primo indice
			ExprFine1 = s.next();
			// può esserci una seconda indicizzazione
			if (ExprFine1.contains("AND"))
				{
				MyScanner s1 = new MyScanner(ExprFine1);
				s1.useDelimiter("\\s*AND\\s*");
				ExprFine1 = s1.next();
				s1.useDelimiter("\\s*IN\\s*");
				s1.skip("\\s*AND\\s*FOR_ALL\\s*");
				// secondo indice
				indice2 = s1.next();
				s1.useDelimiter("\\s*\\.\\.\\s*");
				s1.skip("\\s*IN\\s*");
				// espressione iniziale per il secondo indice
				ExprInizio2 = s1.next();
				s1.useDelimiter("\\s*\\z");
				s1.skip("\\s*\\.\\.\\s*");
				// espressione finale per il secondo indice
				ExprFine2 = s1.next();
				}
			s.useDelimiter("\\s*\\z");
			r = s.next();
			ris = ScanIdent.isIdent(indice1) && ScanExp.isEspressione(ExprInizio1)
			&& ScanExp.isEspressione(ExprFine1) && isAttacDeclP(r);
			if (!indice2.equals("")) ris = ris && ScanIdent.isIdent(indice2);
			if (!ExprInizio2.equals("")) ris = ris && ScanExp.isEspressione(ExprInizio2);
			if (!ExprFine2.equals("")) ris = ris && ScanExp.isEspressione(ExprFine2);
			return ris;
			}
		catch (NoSuchElementException e)
			{
			return false;
			}
		}

	/**
	 * Crea un oggetto AttacDeclInd come una dichiarazione
	 * indicizzata di collegamenti tra elementi architetturali,
	 * includendo informazioni ottenute attraverso la
	 * scannerizzazione di specifiche.
	 *
	 * @param specifiche - oggetto String.
	 * @return un oggetto ArchiElemTypes.
	 * @throws ScanException
	 */
	private static AttacDeclInd scanAttacDeclInd(String specifiche)
		throws ScanException
		{
		/*
		 * Una dichiarazione di collegamento architetturale ha la seguente forma:
		 *
		 * <architectural_attachment_decl> ::= "FOR_ALL" <identifier> "IN" <expr>
		 * ".." <expr> ["AND" "FOR_ALL" <identifier> "IN" <expr> ".." <expr>]
		 * "FROM" <identifier> ["[" <expr> "]"] "." <identifier>
		 * "TO" <identifier> ["[" <expr> "]"] "." <identifier>
		 */
		AttacDeclInd adi = new AttacDeclInd();
		String indice1 = new String();
		String ExprInizio1 = new String();
		String ExprFine1 = new String();
		String indice2 = null;
		String ExprInizio2 = null;
		String ExprFine2 = null;
		String r = new String();
		MyScanner s = new MyScanner(specifiche);
		try {
			s.skip("\\s*FOR_ALL\\s*");
			s.useDelimiter("\\s*IN\\s*");
			// indice1 contiene il primo indice
			indice1 = s.next();
			s.useDelimiter("\\s*\\.\\.\\s*");
			s.skip("\\s*IN\\s*");
			// espressione iniziale del primo indice
			ExprInizio1 = s.next();
			s.useDelimiter("\\s*FROM\\s*");
			s.skip("\\s*\\.\\.\\s*");
			// espressione finale del primo indice
			ExprFine1 = s.next();
			// può esserci una seconda indicizzazione
			if (ExprFine1.contains("AND"))
				{
				MyScanner s1 = new MyScanner(ExprFine1);
				s1.useDelimiter("\\s*AND\\s*");
				ExprFine1 = s1.next();
				s1.useDelimiter("\\s*IN\\s*");
				s1.skip("\\s*AND\\s*FOR_ALL\\s*");
				// secondo indice
				indice2 = s1.next();
				s1.useDelimiter("\\s*\\.\\.\\s*");
				s1.skip("\\s*IN\\s*");
				// espressione iniziale per il secondo indice
				ExprInizio2 = s1.next();
				s1.useDelimiter("\\s*\\z");
				s1.skip("\\s*\\.\\.\\s*");
				// espressione finale per il secondo indice
				ExprFine2 = s1.next();
				}
			s.useDelimiter("\\s*\\z");
			r = s.next();
			adi.setIndex1(ScanIdent.scanIdent(indice1));
			adi.setBeginningExpr1(ScanExp.scanEspressione(ExprInizio1));
			adi.setEndingExpr1(ScanExp.scanEspressione(ExprFine1));
			AttacDecl ad = scanAttacDeclP(r);
			adi.setOutputAei(ad.getOutputAei());
			adi.setOutputInteraction(ad.getOutputInteraction());
			adi.setInputInteraction(ad.getInputInteraction());
			adi.setFromSelector(ad.getFromSelector());
			adi.setToSelector(ad.getToSelector());
			adi.setInputAei(ad.getInputAei());
			if (!(indice2 == null))
				adi.setIndex2(ScanIdent.scanIdent(indice2));
			if (!(ExprInizio2 == null))
				adi.setBeginningExpr2(ScanExp.scanEspressione(ExprInizio2));
			if (!(ExprFine2 == null))
				adi.setEndingExpr2(ScanExp.scanEspressione(ExprFine2));
			return adi;
			}
		catch (NoSuchElementException e)
			{
			throw new ScanException(specifiche+
				" is not AttacDeclInd object",e);
			}
		}
}
