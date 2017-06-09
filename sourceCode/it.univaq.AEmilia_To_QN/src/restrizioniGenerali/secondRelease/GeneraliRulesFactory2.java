package restrizioniGenerali.secondRelease;

import restrizioniGenerali.IGeneraliRules;
import restrizioniGenerali.IGeneraliRulesFactory;
import specificheAEmilia.ArchiType;

public class GeneraliRulesFactory2 
	implements IGeneraliRulesFactory 
	{

	@Override
	public IGeneraliRules createGeneraliRules(ArchiType at) 
		{
		return new GeneraliRules2(at);
		}

	}
