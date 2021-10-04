package tutoriels.core.swing.views;

import static tutoriels.core.models.reports.values.ValidationState.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import ca.ntro.core.system.debug.MustNot;
import ca.ntro.core.system.debug.T;
import tutoriels.core.models.reports.values.ValidationState;
import tutoriels.core.views.ReportNodeView;


public class ReportNodeViewSwing implements ReportNodeView {
	
	private JTree tree;
	private CustomTreeCellRenderer treeCellRenderer;

	private DefaultMutableTreeNode rootNode;
	
	private Map<Long, DefaultMutableTreeNode> children = new HashMap<>();
	
	public ReportNodeViewSwing(JTree tree, DefaultMutableTreeNode rootNode, CustomTreeCellRenderer treeCellRenderer) {
		T.call(this);
		
		this.tree = tree;
		this.rootNode = rootNode;
		this.treeCellRenderer = treeCellRenderer;
	}

	@Override
	public void displayTitle(String title) {
		T.call(this);
		
		NodeInfo nodeInfo = (NodeInfo) rootNode.getUserObject();
		
		nodeInfo.setTitle(title);

		((DefaultTreeModel) tree.getModel()).nodeChanged(rootNode);
	}

	@Override
	public ReportNodeView appendNewChild(long id) {
		T.call(this);
		
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new NodeInfo(id));
		
		children.put(id, childNode);
		
		ReportNodeViewSwing childView = new ReportNodeViewSwing(tree, childNode, treeCellRenderer);
		
		rootNode.add(childNode);
		
		return childView;
	}

	@Override
	public ReportNodeView insertChild(int index, long id) {
		T.call(this);

		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new NodeInfo(id));
		
		children.put(id, childNode);
		
		ReportNodeViewSwing childView = new ReportNodeViewSwing(tree, childNode, treeCellRenderer);
		
		rootNode.insert(childNode, index);

		((DefaultTreeModel) tree.getModel()).reload(rootNode);

		return childView;
	}

	@Override
	public void displayValidationState(ValidationState state) {
		T.call(this);
		
		if(state == TIMEOUT || state == CRASH || state == ERROR) {
			
			// TODO: expand rows to make this visible
			
			// TODO: actually, make the first error visible
			
			// TODO: probably better to put this into the ViewModel
		}
		
		treeCellRenderer.setNodeIcon(rootNode, iconForValidationState(state));

		((DefaultTreeModel) tree.getModel()).nodeChanged(rootNode);
	}
	
	private Icon iconForValidationState(ValidationState state) {
		
		URL resource = this.getClass().getResource("/icons/" + state.name() + ".png");
		
		Icon icon = new ImageIcon(resource);
		
		return icon;
	}

	@Override
	public void removeChild(long id) {
		T.call(this);
		
		DefaultMutableTreeNode child = children.get(id);
		
		MustNot.beNull(child);
		
		rootNode.remove(child);
		
		children.remove(id);

		((DefaultTreeModel) tree.getModel()).reload(rootNode);
	}
	
	@Override
	public void step01FetchCommands() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step02InstallEventListeners() {
		// TODO Auto-generated method stub
		
	}








}
