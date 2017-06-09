package it.univaq.ttep.editors.performance;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class PerformanceConfiguration extends SourceViewerConfiguration {
	private PerformanceDoubleClickStrategy doubleClickStrategy;
	private PerformanceScanner scanner;
	private ColorManager colorManager;

	public PerformanceConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			PerformancePartitionScanner.Aemilia_COMMENT,
			PerformancePartitionScanner.Aemilia_archi_Elem_Types,
			PerformancePartitionScanner.Aemilia_archi_Topology };
	}
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new PerformanceDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected PerformanceScanner getAemiliaScanner() {
		if (scanner == null) {
			scanner = new PerformanceScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IPerformanceColorConstants.DEFAULT))));
		}
		return scanner;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getAemiliaScanner());
		reconciler.setDamager(dr, PerformancePartitionScanner.Aemilia_archi_Elem_Types);
		reconciler.setRepairer(dr, PerformancePartitionScanner.Aemilia_archi_Elem_Types);

		dr = new DefaultDamagerRepairer(getAemiliaScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(IPerformanceColorConstants.Aemilia_COMMENT)));
		reconciler.setDamager(ndr, PerformancePartitionScanner.Aemilia_COMMENT);
		reconciler.setRepairer(ndr, PerformancePartitionScanner.Aemilia_COMMENT);

		return reconciler;
	}

}