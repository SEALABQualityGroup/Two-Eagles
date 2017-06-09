package restrizioniSpecifiche.secondRelease;

import restrizioniSpecifiche.ISpecificheRules;
import restrizioniSpecifiche.ISpecificheRulesFactory;
import restrizioniSpecifiche.RestrizioniSpecException;
import specificheAEmilia.ArchiType;

public class SpecificheRulesFactory2 implements ISpecificheRulesFactory {

	@Override
	public ISpecificheRules createSpecificheRules(ArchiType archiType)
			throws RestrizioniSpecException 
		{
		return new SpecificheRules2(archiType);
		}
	}
