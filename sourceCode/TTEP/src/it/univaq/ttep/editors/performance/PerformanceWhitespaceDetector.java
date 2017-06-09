package it.univaq.ttep.editors.performance;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class PerformanceWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}
}
