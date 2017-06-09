package restrizioniSpecifiche.secondRelease;

import java.util.ArrayList;
import java.util.List;

import restrizioniSpecifiche.RestrizioniSpecException;
import restrizioniSpecifiche.interfaces.ISpecifiche;
import restrizioniSpecifiche.interfaces.ISpecificheUB;
import specificheAEmilia.AEIdecl;
import specificheAEmilia.ArchiType;
import specificheAEmilia.Expression;
import specificheAEmilia.Real;
import equivalenzaComportamentale.EquivalenzaFactory;
import equivalenzaComportamentale.interfaces.IEquivalenza;
import equivalenzaComportamentale.interfaces.IEquivalenzaBufferIllimitato;
import equivalenzaComportamentale.secondRelease.riconoscimento.comportamenti.UnconditionalGetBehavior;

// Restrizione 6;
// Restrizione 7;
// Restrizione 15.

public class SpecificheUB2 
	extends SpecificheB2 
	implements ISpecificheUB 
	{

	public SpecificheUB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory,
			List<IEquivalenza> listEquivalenzeArchiType)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory, listEquivalenzeArchiType);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un buffer illimitato.
		equivalenza = equivalenzaFactory.getUB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un buffer illimitato
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not unlimited buffer");
		}

	public SpecificheUB2(ArchiType archiType, AEIdecl idecl,
			EquivalenzaFactory equivalenzaFactory)
			throws RestrizioniSpecException 
		{
		super(archiType, idecl, equivalenzaFactory);
		// come precondizione abbiamo che l'istanza corrisponde
		// ad un elemento base di una rete di code equivalente ad
		// un buffer illimitato.
		equivalenza = equivalenzaFactory.getUB();
		// si setta l'equivalenza
		normalizeElemTypeForEquiv();
		equivalenza.setAEIdecl(idecl);
		// se elemType non è un buffer illimitato
		if (!equivalenza.isEquivalente())
			throw new RestrizioniSpecException(this.equivalenza.getEt().getHeader().getName()+
					" is not unbounded buffer");
		}

	private IEquivalenzaBufferIllimitato getCastedEquiv()
		{
		return (IEquivalenzaBufferIllimitato)this.equivalenza;
		}

	@Override
	public boolean isCompliantSpecificRules() 
		throws RestrizioniSpecException 
		{
		return restrizione7() && restrizione14() && restrizioneIstanze8()
		&& restrizioneIstanze9() && restrizioneIstanze10() && restrizioneIstanze11();
		}
	
	public boolean isCompliantFullSpecificRules() 
		throws RestrizioniSpecException
		{
		return restrizione6() && 
		restrizione7() && restrizione14() && restrizioneIstanze8() 
		&& restrizioneIstanze9() && restrizioneIstanze10() && restrizioneIstanze11();
		}

	@Override
	public List<Expression> getProbRouting() 
		{
		IEquivalenzaBufferIllimitato equivalenzaBufferIllimitato = getCastedEquiv();
		int i = equivalenzaBufferIllimitato.getNumeroClassiCliente();
		double d = 1.0;
		Real real = new Real(d);
		List<Expression> list = new ArrayList<Expression>(i);
		list.add(real);
		for (int j = 0; j < i; j ++)
			{
			list.add(real);
			}
		return list;
		}
	
	@Override
	public List<ISpecifiche> getSpecificheOutput()
		throws RestrizioniSpecException
		{
		List<IEquivalenza> list = getEquivalenzeOutput();
		List<ISpecifiche> list2 = new ArrayList<ISpecifiche>();
		for (IEquivalenza equivalenza : list)
			{
			ISpecifiche specifiche = SpecificheRules2.getSpecificheFromEquivalenza(archiType, 
					listEquivalenzeArchiType, equivalenza);
			list2.add(specifiche);
			}
		return list2;
		}	
	
	// si verificano le condizioni sulle espressioni dei processi get
	// 11) si verificano le espressioni sulla chiamata comportamentale
	public boolean restrizioneIstanze11()
		{
		IEquivalenzaBufferIllimitato equivalenzaBufferIllimitato = getCastedEquiv();
		List<UnconditionalGetBehavior> list = equivalenzaBufferIllimitato.getUnconditionalGetBehaviors();
		for (UnconditionalGetBehavior unconditionalGetBehavior : list)
			{
			if (!unconditionalGetBehavior.checkBehavProcess())
				return false;
			}
		return true;
		}
	}
