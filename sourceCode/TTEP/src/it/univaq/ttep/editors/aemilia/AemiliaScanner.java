package it.univaq.ttep.editors.aemilia;

import org.eclipse.jface.text.rules.*;
import org.eclipse.jface.text.*;
import org.eclipse.swt.SWT;
import java.util.ArrayList;
import java.util.List;

public class AemiliaScanner extends RuleBasedScanner {
	public final static String Aemilia_KEYWORD = "__aemilia_keyword";
	public final static String Aemilia_DEFAULT = "__aemilia_default";
	public final static String Aemilia_TYPE = "__aemilia_type";
	public final static String Aemilia_MATHEMATICAL_FUNCTION = "__aemilia_mathematical_function";
	public final static String Aemilia_PSEUDO_RANDOM_GENERATOR = "__aemilia_pseudo_random_generator";

	public AemiliaScanner(ColorManager manager) {
		IToken keyword = new Token(new TextAttribute(manager.getColor(IAemiliaColorConstants.Aemilia_KEYWORD), null, SWT.BOLD));
		IToken other = new Token(new TextAttribute(manager.getColor(IAemiliaColorConstants.DEFAULT)));
		IToken type = new Token(new TextAttribute(manager.getColor(IAemiliaColorConstants.Aemilia_TYPE), null, SWT.BOLD));
		IToken mathFunction = new Token(new TextAttribute(manager.getColor(IAemiliaColorConstants.Aemilia_MATHEMATICAL_FUNCTION), null, SWT.BOLD));
		IToken randomGenerator = new Token(new TextAttribute(manager.getColor(IAemiliaColorConstants.Aemilia_PSEUDO_RANDOM_GENERATOR), null, SWT.BOLD));
		
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

		rules.add(wordRule);



		IRule[] result = new IRule[rules.size()];
		rules.toArray(result);
		//setRules(result);

		setRules(result);
	}
}