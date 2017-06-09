package restrizioniSpecifiche.interfaces;

import java.util.HashMap;
import java.util.List;

public interface ISpecificheMultiServer 
	extends ICompliantSpecificRules
	{

	public HashMap<String, List<ISpecificheSP>> getMultiServerMap();

	public void setMultiServerMap(
			HashMap<String, List<ISpecificheSP>> multiServerMap);

}