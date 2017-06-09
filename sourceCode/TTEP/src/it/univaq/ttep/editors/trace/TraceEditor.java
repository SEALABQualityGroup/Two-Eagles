package it.univaq.ttep.editors.trace;

import org.eclipse.ui.editors.text.TextEditor;

public class TraceEditor extends TextEditor {

	private ColorManager colorManager;

	public TraceEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new TraceConfiguration(colorManager));
		setDocumentProvider(new TraceDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
