package equivalenzaComportamentale.secondRelease;

import specificheAEmilia.Action;
import specificheAEmilia.BehavEquation;
import specificheAEmilia.Expression;



public abstract class EquivalenzaArrivi2 
	extends Equivalenza2
	{

	protected String[] chooses = null;
	protected Action[] deliverActions = null;
	protected String[] delivers = null;
	protected Expression[] pesiConsegna = null;
	protected Expression[] prioConsegna = null;
	protected Expression[] probRouting = null;
	protected Expression[] tassiProcesso = null;
	protected BehavEquation phaseBehavior = null;

	public EquivalenzaArrivi2() 
		{
		super();
		}

	}