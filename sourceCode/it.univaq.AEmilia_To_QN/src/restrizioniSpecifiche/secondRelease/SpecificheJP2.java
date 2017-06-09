package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.RestrizioniSpecException;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import specificheAEmilia.Expression;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaBuffer;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoin;
import equivalenzaComportamentale.interfaces.IEquivalenzaRouting;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizio;

public class SpecificheJP2 
	extends Specifiche2 
	{

	public SpecificheJP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		}

	public SpecificheJP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		}

	@Override
	public boolean isCompliantSpecificRules() 
		throws RestrizioniSpecException 
		{
		return restrizione12();
		}
	
	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione11() && restrizione12();
		}

	/**
	 * L'azione join è attaccata a buffers, uno per ogni classe di clienti.
	 * I buffer attaccati devono essere diversi ed a singola classe.
	 * 
	 * @return
	 */
	public boolean restrizione11() 
		{
		boolean ris = true;
		for (IEquivalenza equivalenza : this.equivalenzeInput)
			{
			if (equivalenza instanceof IEquivalenzaBuffer)
				ris = ris && true;
			else
				ris = ris && false;
			}
		ris = ris && restrizione11_1();
		ris = ris && restrizione11_2();
		return ris;
		}

	@Override
	public List<String> getInputInteractionsNames() 
		{
		IEquivalenzaJoin equivalenzaJoin = getCastedEquiv();
		String string = equivalenzaJoin.getJoin();
		List<String> list = new ArrayList<String>();
		list.add(string);
		return list;
		}

	@Override
	public List<String> getOutputInteractionsNames() 
		{
		IEquivalenzaJoin equivalenzaJoin = getCastedEquiv();
		String[] strings = equivalenzaJoin.getDelivers();
		// come precondizione strings non è null
		List<String> list = new CopyOnWriteArrayList<String>(strings);
		return list;
		}

	private IEquivalenzaJoin getCastedEquiv()
		{
		return (IEquivalenzaJoin)this.equivalenza;
		}
	
	@Override
	public List<String> getOutputsFromInput(String string) 
		{
		IEquivalenzaJoin equivalenzaJoin = getCastedEquiv();
		String string2 = equivalenzaJoin.getJoin();
		List<String> list = new ArrayList<String>();
		if (!string.equals(string2))
			return list;
		list = new CopyOnWriteArrayList<String>(equivalenzaJoin.getDelivers());
		return list;
		}
	
	/*
	 * L'azione join è attaccata a processi join, 
	 * processi di servizio o processi di routing.
	 */
	public boolean restrizione13()
		{
		for (IEquivalenza equivalenza : this.equivalenzeInput)
			{
			if (!(equivalenza instanceof IEquivalenzaJoin) && 
					!(equivalenza instanceof IEquivalenzaServizio) &&
					!(equivalenza instanceof IEquivalenzaRouting))
				return false;
			}
		return true;
		}
	
	// i pesi delle azioni di prosecuzione del percorso devono essere Real
	public boolean restrizioneIstanze7() 
		{
		IEquivalenzaJoin equivalenzaJoin = getCastedEquiv();
		Expression[] espressiones = equivalenzaJoin.getProbRouting();
		for (int i = 0; i < espressiones.length; i++)
			{
			Expression expression = espressiones[i];
			if (!MetodiVari.isOnlyReal(expression) && !MetodiVari.isOnlyInteger(expression))
				return false;
			}
		return true;
		}
	
	// i buffer attaccati sono diversi.
	private boolean restrizione11_1() 
		{
		AEIdecl idecl = this.AEIsDeclInput.get(0);
		String string = idecl.getName();
		for (int i = 1; i < this.AEIsDeclInput.size(); i++)
			{
			AEIdecl idecl2 = this.AEIsDeclInput.get(i);
			String string2 = idecl2.getName();
			if (string.equals(string2))
				return false;
			}
		return true;
		}
	
	// i buffer attaccati sono a singola classe.
	private boolean restrizione11_2()
		{
		for (IEquivalenza equivalenza : this.equivalenzeInput)
			{
			// per precondizione equivalenza è un buffer
			IEquivalenzaBuffer equivalenzaBuffer = (IEquivalenzaBuffer)equivalenza;
			int i = equivalenzaBuffer.getNumeroClassiCliente();
			if (i != 1)
				return false;
			}
		return true;
		}
	}
