package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecificheRP;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import specificheAEmilia.Expression;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.MetodiVari;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaRouting;

public abstract class SpecificheRP2 
	extends Specifiche2 
	implements ISpecificheRP 
	{

	public SpecificheRP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType) throws RestrizioniSpecException 
		{
		super(archiType,idecl,equivalenzaFactory,listEquivalenzeArchiType);
		}

	public SpecificheRP2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory) throws RestrizioniSpecException 
		{
		super(archiType,idecl,equivalenzaFactory);
		}
	
	private IEquivalenzaRouting getCastedEquiv()
		{
		return (IEquivalenzaRouting)this.equivalenza;
		}

	@Override
	public List<String> getOutputInteractionsNames() 
		{
		IEquivalenzaRouting equivalenzaRouting = getCastedEquiv();
		String[] strings = equivalenzaRouting.getDelivers();
		// come precondizione strings non è null
		List<String> list = new CopyOnWriteArrayList<String>(strings);
		return list;
		}

	@Override
	public List<Expression> getProbRouting() 
		{
		IEquivalenzaRouting equivalenzaRouting = getCastedEquiv();
		Expression[] espressiones = equivalenzaRouting.getProbRouting();
		if (espressiones == null)
			return new ArrayList<Expression>();
		List<Expression> list = new CopyOnWriteArrayList<Expression>(espressiones);
		return list;
		}
	
	/*
	 * 3) i pesi delle azioni di prosecuzione del percorso devono essere Real; (isOnlyReal)
	 */
	public boolean restrizioneIstanze7() 
		{
		IEquivalenzaRouting equivalenzaRouting = getCastedEquiv();
		Expression[] espressiones = equivalenzaRouting.getProbRouting();
		for (int i = 0; i < espressiones.length; i++)
			{
			Expression expression = espressiones[i];
			if (!MetodiVari.isOnlyReal(expression) && !MetodiVari.isOnlyInteger(expression))
				return false;
			}
		return true;
		}
	}
