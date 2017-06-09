package it.univaq.ttep.editors.simulation;

import org.eclipse.ui.editors.text.TextEditor;

public class SimulationEditor extends TextEditor {

	private ColorManager colorManager;

	public SimulationEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new SimulationConfiguration(colorManager));
		setDocumentProvider(new SimulationDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
