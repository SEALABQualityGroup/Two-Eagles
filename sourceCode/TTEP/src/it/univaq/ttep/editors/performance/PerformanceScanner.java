package it.univaq.ttep.editors.performance;

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;
import org.eclipse.swt.SWT;
import java.util.ArrayList;
import java.util.List;

public class PerformanceScanner extends RuleBasedScanner {
	public final static String Aemilia_KEYWORD = "__aemilia_keyword";
	public final static String Aemilia_DEFAULT = "__aemilia_default";
	public final static String Aemilia_TYPE = "__aemilia_type";
	public final static String Aemilia_MATHEMATICAL_FUNCTION = "__aemilia_mathematical_function";
	public final static String Aemilia_PSEUDO_RANDOM_GENERATOR = "__aemilia_pseudo_random_generator";
	public final static String Auxiliary_KEYWORD = "__auxiliary_keyword";

	public PerformanceScanner(ColorManager manager) {
		IToken keyword = new Token(new TextAttribute(manager.getColor(IPerformanceColorConstants.Aemilia_KEYWORD), null, SWT.BOLD));
		IToken other = new Token(new TextAttribute(manager.getColor(IPerformanceColorConstants.DEFAULT)));
		IToken type = new Token(new TextAttribute(manager.getColor(IPerformanceColorConstants.Aemilia_TYPE), null, SWT.BOLD));
		IToken mathFunction = new Token(new TextAttribute(manager.getColor(IPerformanceColorConstants.Aemilia_MATHEMATICAL_FUNCTION), null, SWT.BOLD));
		IToken randomGenerator = new Token(new TextAttribute(manager.getColor(IPerformanceColorConstants.Aemilia_PSEUDO_RANDOM_GENERATOR), null, SWT.BOLD));
		IToken auxKeyword = new Token(new TextAttribute(manager.getColor(IPerformanceColorConstants.AUX_KEYWORD), null, SWT.BOLD));

		List<WordRule> rules = new ArrayList<WordRule>();

		WordRule wordRule = new WordRule(new IWordDetector()
		{
			public boolean isWordPart(char character)
			{
				return Character.isJavaIdentifierPart(character);
			}
			public boolean isWordStart(char character)
			{
				return Character.isJavaIdentifierStart(character);
			}
		}, other);


		String[] aemiliaWords = {
				"ARCHI_TYPE",
				"BEHAV_HIDINGS",
				"const",
				"first",
				"ARCHI_ELEM_TYPES",
				"HIDE",
				"local",
				"tail",
				"ELEM_TYPE",
				"INTERNALS",
				"stop",
				"concat",
				"BEHAVIOR",
				"INTERACTIONS",
				"invisible",
				"insert",
				"INPUT_INTERACTIONS",
				"ALL",
				"exp",
				"remove",
				"OUTPUT_INTERACTIONS",
				"BEHAV_RESTRICTIONS",
				"inf",
				"length",
				"UNI",
				"RESTRICT",
				"choice",
				"binomial",
				"AND",
				"OBS_INTERNALS",
				"cond",
				"poisson",
				"array_cons",
				"OR",
				"OBS_INTERACTIONS",
				"void",
				"neg_binomial",
				"read",
				"ARCHI_TOPOLOGY",
				"ALL_OBSERVABLES",
				"prio",
				"geometric",
				"write",
				"ARCHI_ELEM_INSTANCES",
				"BEHAV_RENAMINGS",
				"rate",
				"pascal",
				"ARCHI_INTERACTIONS",
				"RENAME",
				"weight",
				"record_cons",
				"ARCHI_ATTACHMENTS",
				"AS",
				"true",
				"get",
				"FROM",
				"FOR_ALL",
				"false",
				"put",
				"TO",
				"IN",
				"BEHAV_VARIATIONS",
				"END",	
				"list_cons"
		};

		for (int i = 0; i < aemiliaWords.length; i++)
			wordRule.addWord(aemiliaWords[i], keyword);

		rules.add(wordRule);


		String[] aemiliaType = {
				"array",
				"boolean",
				"integer",
				"list",
				"real",
				"record",
		};

		for (int i = 0; i < aemiliaType.length; i++)
			wordRule.addWord(aemiliaType[i], type);
		
		rules.add(wordRule);

		String[] aemiliaMathematicalFunction = {
				"mod",
				"abs",
				"ceil",
				"floor",
				"min",
				"max",
				"power",
				"epower",
				"loge",
				"log10",
				"sqrt",
				"sin",
				"cos"
		};

		for (int i = 0; i < aemiliaMathematicalFunction.length; i++)
			wordRule.addWord(aemiliaMathematicalFunction[i], mathFunction);
		
		rules.add(wordRule);

		String[] aemiliaPseudoRandomGenerators = {
				"c_uniform",
				"erlang",
				"gamma",
				"exponential",
				"weibull",
				"beta",
				"normal",
				"pareto",
				"b_pareto",
				"d_uniform",
				"bernoulli",
		};

		for (int i = 0; i < aemiliaPseudoRandomGenerators.length; i++)
			wordRule.addWord(aemiliaPseudoRandomGenerators[i], randomGenerator);

		String[] auxWords = {
				"PROPERTY",
				"DEADLOCK_FREE",
				"OBS_NRESTR_INTERNALS",
				"IS",
				"FOR_ALL_PATHS",
				"OBS_NRESTR_INTERACTIONS",
				"TRUE",
				"FOR_ALL_PATHS_ALL_STATES_SAT",
				"ALL_OBS_NRESTR",
				"FALSE",
				"FOR_ALL_PATHS_SOME_STATE_SAT",
				"MEASURE",
				"NOT",
				"EXISTS_PATH",
				"ENABLED",
				"EXISTS_TRANS",
				"EXISTS_PATH_ALL_STATES_SAT",
				"STATE_REWARD",
				"EXISTS_WEAK_TRANS",
				"EXISTS_PATH_SOME_STATE_SAT",
				"TRANS_REWARD",
				"FOR_ALL_TRANS",
				"STRONG_UNTIL",
				"RUN_LENGTH_ON_EXEC",
				"FOR_ALL_WEAK_TRANS",
				"WEAK_UNTIL",
				"RUN_LENGTH",
				"EXISTS_TRANS_SET",
				"NEXT_STATE_SAT",
				"RUN_NUMBER",
				"EXISTS_WEAK_TRANS_SET",
				"ALL_FUTURE_STATES_SAT",
				"MEAN",
				"FOR_ALL_TRANS_SETS",
				"SOME_FUTURE_STATE_SAT",
				"VARIANCE",
				"FOR_ALL_WEAK_TRANS_SETS",
				"UNTIL",
				"DISTRIBUTION",
				"LABEL",
				"RELEASES",
				"REWARD"
		};

		for (int i = 0; i < auxWords.length; i++)
			wordRule.addWord(auxWords[i], auxKeyword);

		rules.add(wordRule);



		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);

		setRules(result);
	}
}