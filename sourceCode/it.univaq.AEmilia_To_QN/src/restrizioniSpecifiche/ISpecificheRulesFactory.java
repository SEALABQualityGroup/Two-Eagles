package restrizioniSpecifiche;

import specificheAEmilia.ArchiType;

public interface ISpecificheRulesFactory {

	public abstract ISpecificheRules createSpecificheRules(ArchiType archiType)
			throws RestrizioniSpecException;

}