package it.univaq.ttep.editors.security;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class SecurityWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}
}
