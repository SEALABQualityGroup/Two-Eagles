package restrizioniSpecifiche.interfaces;

import java.util.List;

import specificheAEmilia.BehavEquation;
import specificheAEmilia.Expression;
import equivalenzaComportamentale.interfaces.IEquivalenzaArrivi;

public interface ISpecificheAP 
	extends ISpecifiche
	{

	public IEquivalenzaArrivi getEquivalenzaArrivi();

	public abstract List<Expression> getProbRouting();

	public BehavEquation getPhaseBehavior();

	}