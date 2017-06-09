package it.univaq.ttep.editors.modelChecker;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class ModelCheckerWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}
}
