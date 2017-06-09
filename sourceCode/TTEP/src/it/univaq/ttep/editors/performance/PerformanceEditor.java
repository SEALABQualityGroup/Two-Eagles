package it.univaq.ttep.editors.performance;

import org.eclipse.ui.editors.text.TextEditor;

public class PerformanceEditor extends TextEditor {

	private ColorManager colorManager;

	public PerformanceEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new PerformanceConfiguration(colorManager));
		setDocumentProvider(new PerformanceDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
