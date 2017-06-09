package it.univaq.ttep.editors.simulation;

import it.univaq.ttep.editors.performance.IPerformanceColorConstants;
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

public class SimulationConfiguration extends SourceViewerConfiguration {
	private SimulationDoubleClickStrategy doubleClickStrategy;
	private SimulationTagScanner tagScanner;
	private SimulationScanner scanner;
	private ColorManager colorManager;

	public SimulationConfiguration(ColorManager colorManager) {
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
			doubleClickStrategy = new SimulationDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected SimulationScanner getAemiliaScanner() {
		if (scanner == null) {
			scanner = new SimulationScanner(colorManager);
			scanner.setDefaultReturnToken(
					new Token(
							new TextAttribute(
									colorManager.getColor(ISimulationColorConstants.DEFAULT))));
		}
		return scanner;
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr =
			new DefaultDamagerRepairer(getAemiliaScanner());
		reconciler.setDamager(dr, SimulationPartitionScanner.Aemilia_archi_Elem_Types);
		reconciler.setRepairer(dr, SimulationPartitionScanner.Aemilia_archi_Elem_Types);

		dr = new DefaultDamagerRepairer(getAemiliaScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr =
			new NonRuleBasedDamagerRepairer(
					new TextAttribute(
							colorManager.getColor(ISimulationColorConstants.Aemilia_COMMENT)));
		reconciler.setDamager(ndr, SimulationPartitionScanner.Aemilia_COMMENT);
		reconciler.setRepairer(ndr, SimulationPartitionScanner.Aemilia_COMMENT);

		return reconciler;
	}

}