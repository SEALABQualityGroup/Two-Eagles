package mappingElementiBaseIntoPMIF;

import java.util.Collection;

import elementiBaseQN.ElementoBaseQN;

public interface ISalvaElementiBaseFactory 
	{

	public abstract ISalvaElementiBase createSalvaElementiBase(
			Collection<ElementoBaseQN> elementiBase) throws MappingElementiBaseException;

	}