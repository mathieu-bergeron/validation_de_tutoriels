package tutoriels.core.swing.views;

import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

import ca.ntro.core.system.debug.T;
import tutoriels.core.performance_app.swing.views.PerformanceGraphViewSwing;
import tutoriels.core.performance_app.views.PerformanceGraphView;
import tutoriels.core.views.CurrentNodeView;

public class CurrentNodeViewSwing extends JPanel implements CurrentNodeView {
	private static final long serialVersionUID = 8588135472839250862L;
	
	private JEditorPane htmlPane = new JEditorPane();
	private PerformanceGraphViewSwing performanceGraphView = new PerformanceGraphViewSwing();
	
	private static final String PERFORMANCE_GRAPH = "PerformanceGraphView";
	private static final String HTML_PAGE = "htmlPane";
	
	
	public CurrentNodeViewSwing() {
		super();
		
		setLayout(new GridLayout(0,1));

		this.add(htmlPane);
		htmlPane.setEditable(false);
	}
	

	@Override
	public void step01FetchCommands() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step02InstallEventListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayHtml(String htmlContent) {
		T.call(this);

		htmlPane.setContentType("text/html");
		htmlPane.setText(htmlContent);
	}
}
