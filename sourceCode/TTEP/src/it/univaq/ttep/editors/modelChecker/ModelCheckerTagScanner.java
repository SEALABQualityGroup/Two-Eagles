package it.univaq.ttep.editors.modelChecker;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.rules.*;

public class ModelCheckerTagScanner extends RuleBasedScanner {

	public ModelCheckerTagScanner(ColorManager manager) {
		IToken string =
			new Token(
				new TextAttribute(manager.getColor(IModelCheckerColorConstants.STRING)));

		IRule[] rules = new IRule[3];

		// Add rule for double quotes
		rules[0] = new SingleLineRule("\"", "\"", string, '\\');
		// Add a rule for single quotes
		rules[1] = new SingleLineRule("'", "'", string, '\\');
		// Add generic whitespace rule.
		rules[2] = new WhitespaceRule(new ModelCheckerWhitespaceDetector());

		setRules(rules);
	}
}
