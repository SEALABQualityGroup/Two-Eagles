package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.RestrizioniSpecException;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import specificheAEmilia.Expression;
import specificheAEmilia.Integer;
import specificheAEmilia.Real;
import valutazione.Interezza;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaArrivi;
import equivalenzaComportamentale.interfaces.IEquivalenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaFork;
import equivalenzaComportamentale.interfaces.IEquivalenzaForkConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoin;
import equivalenzaComportamentale.interfaces.IEquivalenzaRouting;
import equivalenzaComportamentale.interfaces.IEquivalenzaRoutingConBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizio;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizioConBuffer;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.PutBehavior;

public class SpecificheB2 
	extends Specifiche2 
	{

	public SpecificheB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		}

	public SpecificheB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		}

	/**
	 * Ogni azione di get è attaccata ad un processo di arrivi, 
	 * processo fork, processo join, processo di servizio, 
	 * processo di routing.
	 * 
	 * @return
	 */
	public boolean restrizione6()
		{
		boolean b = true;
		// ogni equivalenza di output attaccata deve essere un processo di arrivo,
		// un processo fork, un processo join, un processo di servizio
		// o un processo di routing.
		for (IEquivalenza iEquivalenza : this.equivalenzeInput)
			{
			if (iEquivalenza instanceof IEquivalenzaArrivi)
				b = b && true;
			else if (iEquivalenza instanceof IEquivalenzaFork)
				b = b && true;
			else if (iEquivalenza instanceof IEquivalenzaJoin)
				b = b && true;
			else if (iEquivalenza instanceof IEquivalenzaServizio)
				b = b && true;
			else if (iEquivalenza instanceof IEquivalenzaRouting)
				b = b && true;
			else b = b && false;
			}
		return b;
		}
	
	/**
	 * Ogni azione put è attaccata ad un processo fork con buffer, 
	 * un processo join, un processo di servizio con buffer, 
	 * un processo di routing con buffer.
	 * Tutte le azioni put sono connesse allo stesso elemento base. 
	 * (non vale per i centri di servizio multiserver)
	 * 
	 * @return
	 */
	public boolean restrizione7()
		{
		boolean b = true;
		// ogni iEquivalenza di input attaccata deve essere un processo fork con buffer,
		// un processo join, un processo di servizio con buffer, un processo di routing con buffer.
		for (IEquivalenza iEquivalenza : this.equivalenzeOutput)
			{
			if (iEquivalenza instanceof IEquivalenzaForkConBuffer)
				b = b && true;
			else if (iEquivalenza instanceof IEquivalenzaJoin)
				b = b && true;
			else if (iEquivalenza instanceof IEquivalenzaServizioConBuffer)
				b = b && true;
			else if (iEquivalenza instanceof IEquivalenzaRoutingConBuffer)
				b = b && true;
			else b = b && false;
			}
		b = b && restrizione7_1();
		return b;
		}
	
	// Tutte le azioni put sono connesse allo stesso elemento base. 
	// (non vale per i centri di servizio multiserver)
	private boolean restrizione7_1()
		{
		AEIdecl idecl = this.AEIsDeclOutput.get(0);
		String string = idecl.getName();
		for (int i = 1; i < this.AEIsDeclOutput.size(); i++)
			{
			AEIdecl idecl2 = this.AEIsDeclOutput.get(i);
			String string2 = idecl2.getName();
			if (!string.equals(string2))
				return false;
			}
		return true;
		}
	
	@Override
	public boolean isCompliantSpecificRules() 
		throws RestrizioniSpecException 
		{
		return restrizione7();
		}
	
	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione6() && restrizione7();
		}

	@Override
	public List<String> getInputInteractionsNames() 
		{
		IEquivalenzaBuffer equivalenzaBuffer = getCastedEquiv();
		String[] strings = equivalenzaBuffer.getGets();
		List<String> list = new CopyOnWriteArrayList<String>(strings);
		return list;
		}

	@Override
	public List<String> getOutputInteractionsNames() 
		{
		IEquivalenzaBuffer equivalenzaBuffer = getCastedEquiv();
		String[] strings = equivalenzaBuffer.getPuts();
		List<String> list = new CopyOnWriteArrayList<String>(strings);
		return list;
		}
	
	private IEquivalenzaBuffer getCastedEquiv()
		{
		return (IEquivalenzaBuffer)this.equivalenza;
		}

	@Override
	public List<String> getOutputsFromInput(String string) 
		{
		IEquivalenzaBuffer equivalenzaBuffer = getCastedEquiv();
		String string2 = equivalenzaBuffer.getPutFromGet(string);
		List<String> list = new ArrayList<String>();
		if (string2 != null)
			list.add(string2);
		return list;
		}
		
	// 6) I parametri presenti nell’intestazione del comportamento devono essere 
	// inizializzati a interi non negativi
	public boolean restrizioneIstanze8()
		{
		// per precondizioni le espressioni di inizializzazione di varInits sono
		// già state valutate e devono corrispondere a interi non negativi
		IEquivalenzaBuffer equivalenzaBuffer = getCastedEquiv();
		Expression[] espressiones = equivalenzaBuffer.getCapacitaIniziali();
		for (Expression expression : espressiones)
			{
			if (!(expression instanceof Integer) && !(MetodiVari.isOnlyReal(expression)))
				return false;
			if (MetodiVari.isOnlyInteger(expression))
				{
				Integer integer = (Integer)expression;
				int i = integer.getValore();
				if (i < 0)
					return false;
				}
			if (MetodiVari.isOnlyReal(expression))
				{
				Real real = (Real)expression;
				double d = real.getValore();
				if (d < 0)
					return false;
				// real deve essere un intero
				Interezza interezza = new Interezza();
				if (!interezza.isIntero(real))
					return false;
				}
			}
		return true;
		}
	
	// si verificano le condizioni sulle espressioni dei processi put
	// 8) si verifica la condizione di buffer vuoto
	public boolean restrizioneIstanze9()
		{
		IEquivalenzaBuffer equivalenzaBuffer = getCastedEquiv();
		List<PutBehavior> list = equivalenzaBuffer.getPutBehaviors();
		for (PutBehavior putBehavior : list)
			{
			if (!putBehavior.checkEmpty())
				return false;
			}
		return true;
		}
	
	// si verificano le condizioni sulle espressioni dei processi put
	// 9) si verificano le espressioni sulla chiamata comportamentale
	public boolean restrizioneIstanze10()
		{
		IEquivalenzaBuffer equivalenzaBuffer = getCastedEquiv();
		List<PutBehavior> list = equivalenzaBuffer.getPutBehaviors();
		for (PutBehavior putBehavior : list)
			{
			if (!putBehavior.checkBehavProcess())
				return false;
			}
		return true;
		}	
	}
