package it.univaq.ttep.editors.aemilia;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.rules.*;

public class AemiliaTagScanner extends RuleBasedScanner {

	public AemiliaTagScanner(ColorManager manager) {
		IToken string =
			new Token(
				new TextAttribute(manager.getColor(IAemiliaColorConstants.Aemilia_KEYWORD)));

		IRule[] rules = new IRule[3];

		// Add rule for double quotes
		rules[0] = new SingleLineRule("\"", "\"", string, '\\');
		// Add a rule for single quotes
		rules[1] = new SingleLineRule("'", "'", string, '\\');
		// Add generic whitespace rule.
		rules[2] = new WhitespaceRule(new AemiliaWhitespaceDetector());

		setRules(rules);
	}
}
