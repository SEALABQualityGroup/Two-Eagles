package it.univaq.ttep.editors.aemilia;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class AemiliaWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}
}
