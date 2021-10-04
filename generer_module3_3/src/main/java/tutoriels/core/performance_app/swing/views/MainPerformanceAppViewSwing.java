package tutoriels.core.performance_app.swing.views;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import tutoriels.core.performance_app.views.MainPerformanceAppView;
import tutoriels.core.performance_app.views.PerformanceGraphView;

@SuppressWarnings("serial")
public class MainPerformanceAppViewSwing extends JFrame implements NtroView, MainPerformanceAppView {
	
	private JTabbedPane tabbedPane = new JTabbedPane();

	public MainPerformanceAppViewSwing(String title) {
		super(title);
		T.call(this);
	}

	public void initialize() {
		T.call(this);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		} catch (Exception e) {}

		setLayout(new GridLayout(1,0));
		setSize(1200, 600);
		
		this.add(tabbedPane);
		
		//pack();
		setVisible(true);
		
		// FIXME: this should be called by Ntro
		step01FetchCommands();
		step02InstallEventListeners();
	}

	@Override
	public void step01FetchCommands() {
		T.call(this);
	}

	@Override
	public void step02InstallEventListeners() {
		T.call(this);
	}

	@Override
	public PerformanceGraphView addGraphView(String title) {
		T.call(this);

		PerformanceGraphViewSwing graphView = new PerformanceGraphViewSwing();
		
		tabbedPane.addTab(title, graphView);
		
		return graphView;
	}
}
