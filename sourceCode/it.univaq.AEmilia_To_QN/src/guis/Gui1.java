package guis;


import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import mappingElementiBaseIntoPMIF.ISalvaElementiBase;
import mappingElementiBaseIntoPMIF.MappingElementiBaseException;
import scanSpecAEmilia.ScanLisFile;
import transformerFases.ITrasformazione;
import transformerFases.ITrasformazioneFactory;
import transformerFases.RisultatoFase;
import utilities.MetodiVari;
import config.ServiceTimeFactory;

public class Gui1 {

	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="56,-39"
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem openLisMenuItem = null;
	private JTextArea AreaPerMessaggi = null;
	private JScrollPane PannelloMessaggi = null;
	private JSplitPane jSplitPane = null;
	private JScrollPane PannelloPerLis = null;
	private JTextArea AreaPerLis = null;
	private JFileChooser fc = null;  //  @jve:decl-index=0:visual-constraint="341,0"
	private JToolBar jToolBar = null;
	private JButton jButton = null;
	private JFileChooser fc2 = null;
	
	// il file selezionato dall'utente
	private File selectedFile;
	
	/**
	 * This method initializes jFrame
	 *
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(504, 326);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("From_AEmilia_To_QN");
			jFrame.setLocationByPlatform(true);
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 *
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(40);
			borderLayout.setVgap(0);
			jContentPane = new JPanel();
			jContentPane.setLayout(borderLayout);
			jContentPane.setToolTipText("");
			jContentPane.add(getJSplitPane(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getJToolBar(), BorderLayout.NORTH);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar
	 *
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu
	 *
	 * @return javax.swing.JMenu
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.setSize(new Dimension(31, 21));
			fileMenu.add(getOpenLisMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	/**
	 * This method initializes jMenuItem
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");

			/*
			 * In questo caso l'elemento del menu
			 * aggiunge un listener di azione creando un nuovo
			 * listener che implementa il metodo desiderato.
			 * In questo caso, i tre oggetti coinvolti
			 * nell'evento sono: exitMenuItem, l'interfaccia
			 * ActionListener, l'istanza di ActionListener.
			 */
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes openLisMenuItem
	 *
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getOpenLisMenuItem() {
		if (openLisMenuItem == null) {
			openLisMenuItem = new JMenuItem();
			openLisMenuItem.setActionCommand("");
			openLisMenuItem.setText("Load .lis");
			openLisMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					ExampleFileFilter filtro = new ExampleFileFilter("lis");
					filtro.setDescription("Result parsing of AEmilia specification");
					JFileChooser fileChooser = getFc();
					fileChooser.setFileFilter(filtro);
					int returnVal = fileChooser.showOpenDialog(getJFrame());
					if (returnVal == JFileChooser.APPROVE_OPTION)
						{
						File file = fileChooser.getSelectedFile();
						selectedFile = file;
						String[] ris = ScanLisFile.scanLisFile(file);
						AreaPerLis.setText(ris[0]);
						AreaPerMessaggi.setText(ris[1]);
						// se il caricamento è avvenuto senza errori
						// viene concesso il tasto di
						// trasformazione
						if (AreaPerMessaggi.getText().equals(""))
							{
							getJButton().setEnabled(true);
							}
						}
				}
			});
		}
		return openLisMenuItem;
	}

	/**
	 * This method initializes AreaPerMessaggi
	 *
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getAreaPerMessaggi() {
		if (AreaPerMessaggi == null) {
			AreaPerMessaggi = new JTextArea();
			AreaPerMessaggi.setText("");
			AreaPerMessaggi.setLineWrap(true);
			AreaPerMessaggi.setWrapStyleWord(true);
			AreaPerMessaggi.setFont(new Font("Dialog", Font.BOLD, 12));
			AreaPerMessaggi.setBackground(SystemColor.activeCaptionText);
			AreaPerMessaggi.setEditable(false);
		}
		return AreaPerMessaggi;
	}

	/**
	 * This method initializes PannelloMessaggi
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getPannelloMessaggi() {
		if (PannelloMessaggi == null) {
			PannelloMessaggi = new JScrollPane();
			PannelloMessaggi.setViewportView(getAreaPerMessaggi());
		}
		return PannelloMessaggi;
	}

	/**
	 * This method initializes jSplitPane
	 *
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (jSplitPane == null) {
			jSplitPane = new JSplitPane();
			jSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			jSplitPane.setContinuousLayout(true);
			jSplitPane.setResizeWeight(1.0D);
			jSplitPane.setTopComponent(getPannelloPerLis());
			jSplitPane.setBottomComponent(getPannelloMessaggi());
			jSplitPane.setDividerLocation(100);
		}
		return jSplitPane;
	}

	/**
	 * This method initializes PannelloPerLis
	 *
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getPannelloPerLis() {
		if (PannelloPerLis == null) {
			PannelloPerLis = new JScrollPane();
			PannelloPerLis.setViewportView(getAreaPerLis());
		}
		return PannelloPerLis;
	}

	/**
	 * This method initializes AreaPerLis
	 *
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getAreaPerLis() {
		if (AreaPerLis == null) {
			AreaPerLis = new JTextArea();
			AreaPerLis.setLineWrap(true);
			AreaPerLis.setEditable(false);
			AreaPerLis.setWrapStyleWord(true);
		}
		return AreaPerLis;
	}

	/**
	 * This method initializes fc
	 *
	 * @return javax.swing.JFileChooser
	 */
	private JFileChooser getFc() {
		if (fc == null) {
			fc = new JFileChooser();
			fc.setDialogTitle("Load lis");
			fc.setSize(new Dimension(504, 326));
		}
		return fc;
	}

	/**
	 * This method initializes jToolBar
	 *
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			jToolBar.setPreferredSize(new Dimension(18, 30));
			jToolBar.add(getJButton());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton
	 *
	 * @return javax.swing.JButton
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setSize(new Dimension(30, 12));
			jButton.setHorizontalAlignment(SwingConstants.CENTER);
			jButton.setEnabled(false);
			jButton.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			jButton.setText("Transform in PMIF");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e)
					{
					// si disabilita il bottone della trasformazione
					jButton.setEnabled(false);
					// si imposta un'ogetto RisultatoFase
					// per la prima fase
					RisultatoFase risultatoFase =
						new RisultatoFase();
					risultatoFase.setEsito(true);
					risultatoFase.setRisultato(ScanLisFile.generaStringa(getAreaPerLis().getText()));
					// prelevo il nome del file a partire dal path della directory
					// che lo contiene
					String string3 = MetodiVari.namespaceCompute(selectedFile);
					// si trasforma la specifica aemilia
					ITrasformazioneFactory trasformazioneFactory = ServiceTimeFactory.getTrasformazioneFactory();
					ITrasformazione trasformazione = trasformazioneFactory.createTrasformazione();
					RisultatoFase risultatoFase2 = trasformazione.trasforma(
							getAreaPerMessaggi(), 
							risultatoFase,
							string3);
					if (risultatoFase2.isEsito())
						mostraDialogPerSalvare(risultatoFase2);
					}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes fc2
	 *
	 * @return javax.swing.JFileChooser
	 */
	private JFileChooser getFc2() {
		if (fc2 == null) {
			fc2 = new JFileChooser();
			fc2.setDialogTitle("Save PMIF");
			fc2.setSize(new Dimension(504, 326));
		}
		return fc2;
	}

	/**
	 * Launches this application
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Gui1 application = new Gui1();
				application.getJFrame().setVisible(true);
			}
		});
	}

	private void mostraDialogPerSalvare(RisultatoFase risultatoFase)
		{
		int i = JOptionPane.showConfirmDialog(getJFrame(), "Do you want save the queueing network in PMIF format ?", "Save in PMIF", JOptionPane.YES_NO_OPTION);
		if (i == JOptionPane.YES_OPTION)
			{
			ExampleFileFilter filtro = new ExampleFileFilter("xml");
			filtro.setDescription("PMIF");
			getFc2().setFileFilter(filtro);
			int returnVal = getFc2().showSaveDialog(getJFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION)
				{
				File file = getFc2().getSelectedFile();
				String string = file.getAbsolutePath();
				ISalvaElementiBase salvaElementiBase =
					(ISalvaElementiBase)risultatoFase.getRisultato();
				try {
					salvaElementiBase.salvaInXML(string);
					} 
				catch (MappingElementiBaseException e) 
					{
					e.printStackTrace();
					}
				}
			}
		else if (i == JOptionPane.CLOSED_OPTION)
			{
			mostraDialogPerSalvare(risultatoFase);
			}
		}
}