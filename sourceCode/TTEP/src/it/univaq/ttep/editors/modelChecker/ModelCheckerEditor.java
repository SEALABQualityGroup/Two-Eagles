package it.univaq.ttep.editors.modelChecker;

import org.eclipse.ui.editors.text.TextEditor;

public class ModelCheckerEditor extends TextEditor {

	private ColorManager colorManager;

	public ModelCheckerEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new ModelCheckerConfiguration(colorManager));
		setDocumentProvider(new ModelCheckerDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
