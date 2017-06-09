package it.univaq.ttep.editors.security;

import org.eclipse.ui.editors.text.TextEditor;

public class SecurityEditor extends TextEditor {

	private ColorManager colorManager;

	public SecurityEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new SecurityConfiguration(colorManager));
		setDocumentProvider(new SecurityDocumentProvider());
	}
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

}
