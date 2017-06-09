package restrizioniSpecifiche.interfaces;

import specificheAEmilia.Expression;

public interface ISpecificheSPWB 
	extends ISpecificheSP
	{

	public Expression[] getPrioSelezione();

	public Expression[] getProbSelezione();

	}