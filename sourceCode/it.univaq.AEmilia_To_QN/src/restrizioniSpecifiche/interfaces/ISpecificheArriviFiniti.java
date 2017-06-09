package restrizioniSpecifiche.interfaces;

import java.util.HashMap;
import java.util.List;


public interface ISpecificheArriviFiniti 
	extends ICompliantSpecificRules
	{

	/*
	 * Ha come chiavi i nomi dei tipi dei processi di arrivi finiti e
	 * come valori la lista degli ISpecificheSCAP relative alle istanze del tipo
	 * di elemento architetturale.
	 */
	public HashMap<String, List<ISpecificheSCAP>> getHashMapSCAP();

	}