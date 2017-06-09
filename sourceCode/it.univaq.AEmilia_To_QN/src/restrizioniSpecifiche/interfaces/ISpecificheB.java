package restrizioniSpecifiche.interfaces;

import java.util.List;

import restrizioniSpecifiche.RestrizioniSpecException;
import specificheAEmilia.Expression;

public interface ISpecificheB 
	extends ISpecifiche
	{

	public abstract List<Expression> getProbRouting();

	public abstract List<ISpecifiche> getSpecificheOutput()
		throws RestrizioniSpecException; 

	}