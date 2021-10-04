package tutoriels.core.commands.select_node;

import ca.ntro.core.commands.CommandTarget;

public interface SelectNodeTarget extends CommandTarget {
	
	void selectNode(long nodeId);

}
