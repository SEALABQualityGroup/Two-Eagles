package it.univaq.ttep.editors.trace;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class TraceWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}
}
