package it.univaq.ttep.editors.modelChecker;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.*;

public class ModelCheckerPartitionScanner extends RuleBasedPartitionScanner {	
	public final static String Aemilia_COMMENT = "__aemilia_comment";
	
	public final static String Aemilia_archi_Elem_Types = "__archi_Elem_Types";
	public final static String Aemilia_archi_Topology = "__archi_Topology";
	public final static String Aemilia_behav_Variations = "__behav_Variations";
	
	public final static String comment_begin = "%";
	public final static String archiElemTypesBegin = "ARCHI_ELEM_TYPES";
	public final static String archiTopologyBegin = "ARCHI_TOPOLOGY";
	public final static String behavVariationsBegin = "BEHAV_VARIATIONS";
	public final static String endLine = "/n";
	
	public ModelCheckerPartitionScanner() {
		super();
		
		IToken comment = new Token(Aemilia_COMMENT);
		List<PatternRule> rules= new ArrayList<PatternRule>();
		rules.add(new SingleLineRule(comment_begin, endLine, comment));
		IPredicateRule[] result = new IPredicateRule[rules.size()];
		rules.toArray(result);
		setPredicateRules(result);
	}
}
