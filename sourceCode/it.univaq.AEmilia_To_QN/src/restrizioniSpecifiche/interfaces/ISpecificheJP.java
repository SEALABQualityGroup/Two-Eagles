package restrizioniSpecifiche.interfaces;

import java.util.List;

import specificheAEmilia.Expression;
import equivalenzaComportamentale.interfaces.IEquivalenzaJoin;

public interface ISpecificheJP 
	extends ISpecifiche
	{

	public IEquivalenzaJoin getEquivalenzaJoin();

	public abstract List<Expression> getProbRouting();

	}