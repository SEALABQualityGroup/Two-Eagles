package it.univaq.ttep.editors.security;

import it.univaq.ttep.editors.performance.IPerformanceColorConstants;
import it.univaq.ttep.editors.performance.NonRuleBasedDamagerRepairer;
import it.univaq.ttep.editors.performance.PerformancePartitionScanner;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class SecurityConfiguration extends SourceViewerConfiguration {
	private SecurityDoubleClickStrategy doubleClickStrategy;
	private SecurityScanner scanner;
	private ColorManager colorManager;

	public SecurityConfiguration(ColorManager colorManager) {
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
			doubleClickStrategy = new SecurityDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected SecurityScanner getAemiliaScanner() {
		if (scanner == null) {
			scanner = new SecurityScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(ISecurityColorConstants.DEFAULT))));
		}
		return scanner;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getAemiliaScanner());
		reconciler.setDamager(dr, SecurityPartitionScanner.Aemilia_archi_Elem_Types);
		reconciler.setRepairer(dr, SecurityPartitionScanner.Aemilia_archi_Elem_Types);

		dr = new DefaultDamagerRepairer(getAemiliaScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(ISecurityColorConstants.Aemilia_COMMENT)));
		reconciler.setDamager(ndr, SecurityPartitionScanner.Aemilia_COMMENT);
		reconciler.setRepairer(ndr, SecurityPartitionScanner.Aemilia_COMMENT);

		return reconciler;
	}

}