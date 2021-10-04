package tutoriels.core.swing.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import ca.ntro.core.system.debug.T;

@SuppressWarnings("serial")
public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {
	
	private Map<DefaultMutableTreeNode, Icon> icons = new HashMap<>();

	public void setNodeIcon(DefaultMutableTreeNode node, Icon icon) {
		T.call(this);
		
		icons.put(node, icon);
	}
	
	@Override
	public Component getTreeCellRendererComponent(
			JTree tree, 
			Object value, 
			boolean selected, 
			boolean expanded, 
			boolean leaf, 
			int row, 
			boolean hasFocus){
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		
		NodeInfo nodeInfo = (NodeInfo) node.getUserObject();
		
		Icon icon = icons.get(node);

		Component result = null;
		
		if(icon != null) {
			
			JLabel label = new JLabel();
			label.setIcon(icon);
			label.setText("<html>" + nodeInfo.toString() + "</html>");
			
			if(selected) {
				label.setOpaque(true);
				label.setBackground(new Color(200,200,255));
			}
			
			result = label;

		}else {

			result = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		}

		return result;
	}

	

}
