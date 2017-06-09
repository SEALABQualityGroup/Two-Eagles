package it.univaq.ttep.editors.modelChecker;

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

public class ModelCheckerConfiguration extends SourceViewerConfiguration {
	private ModelCheckerDoubleClickStrategy doubleClickStrategy;
	private ModelCheckerScanner scanner;
	private ColorManager colorManager;

	public ModelCheckerConfiguration(ColorManager colorManager) {
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
			doubleClickStrategy = new ModelCheckerDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected ModelCheckerScanner getModelCheckerScanner() {
		if (scanner == null) {
			scanner = new ModelCheckerScanner(colorManager);
			scanner.setDefaultReturnToken(
				new Token(
					new TextAttribute(
						colorManager.getColor(IModelCheckerColorConstants.DEFAULT))));
		}
		return scanner;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getModelCheckerScanner());
		reconciler.setDamager(dr, ModelCheckerPartitionScanner.Aemilia_archi_Elem_Types);
		reconciler.setRepairer(dr, ModelCheckerPartitionScanner.Aemilia_archi_Elem_Types);

		dr = new DefaultDamagerRepairer(getModelCheckerScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
				new TextAttribute(
					colorManager.getColor(IModelCheckerColorConstants.Aemilia_COMMENT)));
		reconciler.setDamager(ndr, ModelCheckerPartitionScanner.Aemilia_COMMENT);
		reconciler.setRepairer(ndr, ModelCheckerPartitionScanner.Aemilia_COMMENT);

		return reconciler;
	}

}