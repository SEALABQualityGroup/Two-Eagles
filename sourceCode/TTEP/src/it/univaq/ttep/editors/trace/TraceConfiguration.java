package it.univaq.ttep.editors.trace;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class TraceConfiguration extends SourceViewerConfiguration {
	private TraceDoubleClickStrategy doubleClickStrategy;
	private TraceScanner scanner;
	private ColorManager colorManager;

	public TraceConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			TracePartitionScanner.Aemilia_COMMENT,
			TracePartitionScanner.Aemilia_archi_Elem_Types,
			TracePartitionScanner.Aemilia_archi_Topology };
	}
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new TraceDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected TraceScanner getAemiliaScanner() {
		if (scanner == null) {
			scanner = new TraceScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(ITraceColorConstants.DEFAULT))));
		}
		return scanner;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getAemiliaScanner());
		reconciler.setDamager(dr, TracePartitionScanner.Aemilia_archi_Elem_Types);
		reconciler.setRepairer(dr, TracePartitionScanner.Aemilia_archi_Elem_Types);

		dr = new DefaultDamagerRepairer(getAemiliaScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(ITraceColorConstants.Aemilia_COMMENT)));
		reconciler.setDamager(ndr, TracePartitionScanner.Aemilia_COMMENT);
		reconciler.setRepairer(ndr, TracePartitionScanner.Aemilia_COMMENT);

		return reconciler;
	}

}