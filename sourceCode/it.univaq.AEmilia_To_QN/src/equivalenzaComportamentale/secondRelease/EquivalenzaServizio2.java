package equivalenzaComportamentale.secondRelease;

import java.util.HashMap;
import java.util.List;

import specificheAEmilia.Action;
import specificheAEmilia.Expression;

public abstract class EquivalenzaServizio2 
	extends Equivalenza2 
	{

	protected HashMap<String, List<String>> choosesFromSelection;
	protected HashMap<String, Action[]> deliverActionsFromSelections;
	protected String[] delivers;
	protected HashMap<String, List<String>> deliversFromSelection;
	protected HashMap<String, Expression[]> pesiDestinazioniFromSel;
	protected HashMap<String, Expression[]> prioDestinazioniFromSel;
	protected HashMap<String, Expression[]> probRoutingFromSel;
	protected String[] selectionsNames;
	protected HashMap<String, Expression[]> tassiServizioFromSel;
	protected Expression[] prioSelezione;
	protected Expression[] probSelezione;

	
	
	}
