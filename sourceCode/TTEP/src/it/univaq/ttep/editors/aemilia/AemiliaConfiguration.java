package it.univaq.ttep.editors.aemilia;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class AemiliaConfiguration extends SourceViewerConfiguration {
	private AemiliaDoubleClickStrategy doubleClickStrategy;
	private AemiliaTagScanner tagScanner;
	private AemiliaScanner scanner;
	private ColorManager colorManager;

	public AemiliaConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {
			IDocument.DEFAULT_CONTENT_TYPE,
			AemiliaPartitionScanner.Aemilia_COMMENT,
			AemiliaPartitionScanner.Aemilia_archi_Elem_Types,
			AemiliaPartitionScanner.Aemilia_archi_Topology };
	}
	public ITextDoubleClickStrategy getDoubleClickStrategy(
		ISourceViewer sourceViewer,
		String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new AemiliaDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected AemiliaScanner getAemiliaScanner() {
		if (scanner == null) {
			scanner = new AemiliaScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IAemiliaColorConstants.DEFAULT))));
		}
		return scanner;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getAemiliaScanner());
		reconciler.setDamager(dr, AemiliaPartitionScanner.Aemilia_archi_Elem_Types);
		reconciler.setRepairer(dr, AemiliaPartitionScanner.Aemilia_archi_Elem_Types);

		dr = new DefaultDamagerRepairer(getAemiliaScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(IAemiliaColorConstants.Aemilia_COMMENT)));
		reconciler.setDamager(ndr, AemiliaPartitionScanner.Aemilia_COMMENT);
		reconciler.setRepairer(ndr, AemiliaPartitionScanner.Aemilia_COMMENT);

		return reconciler;
	}

}