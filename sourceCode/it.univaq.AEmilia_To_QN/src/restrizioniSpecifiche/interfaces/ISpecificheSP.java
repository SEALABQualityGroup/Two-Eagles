package restrizioniSpecifiche.interfaces;

import java.util.HashMap;
import java.util.List;

import specificheAEmilia.BehavEquation;
import specificheAEmilia.Expression;
import equivalenzaComportamentale.interfaces.IEquivalenzaServizio;

public interface ISpecificheSP 
	extends ISpecifiche
	{

	public IEquivalenzaServizio getEquivalenzaServizio();

	// le chiavi sono le interazioni di input
	public abstract HashMap<String,List<Expression>> getProbRouting();

	public abstract HashMap<String, String> getServicesNamesFromSelections();

	public abstract List<BehavEquation> getServiceEquations();
	
	public abstract HashMap<String, List<String>> getDeliversFromSelection();
	
	public abstract List<Expression> getProbSelection();

	}