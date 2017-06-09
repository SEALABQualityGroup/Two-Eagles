package mappingAEIintoElementiBase;

import java.util.List;

import restrizioniSpecifiche.interfaces.ISpecifiche;

public interface IFromAEIintoElementiBaseFactory {

	public abstract IFromAEIintoElementiBase createFromAEIintoElementiBase(
			List<ISpecifiche> list) throws AEIintoElementiBaseException;

}