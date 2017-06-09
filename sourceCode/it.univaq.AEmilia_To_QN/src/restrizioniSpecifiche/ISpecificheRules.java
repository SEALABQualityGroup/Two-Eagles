package restrizioniSpecifiche;

import java.util.List;

import restrizioniSpecifiche.interfaces.ISpecifiche;

public interface ISpecificheRules {

	/**
	 * Verifica tutte le restrizioni sintattiche specifiche.
	 * 
	 * @return
	 * @throws RestrizioniSpecException
	 */
	public abstract boolean isCompliantSpecificRules()
			throws RestrizioniSpecException;

	public List<ISpecifiche> getListaSpecifiche();

}