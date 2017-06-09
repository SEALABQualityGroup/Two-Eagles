package it.univaq.ttep.editors.simulation;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class SimulationWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}
}
