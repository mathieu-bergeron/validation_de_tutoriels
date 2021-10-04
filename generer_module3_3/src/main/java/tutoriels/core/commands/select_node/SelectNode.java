package tutoriels.core.commands.select_node;

import ca.ntro.core.commands.Command;
import ca.ntro.core.commands.CommandTarget;
import ca.ntro.core.system.debug.T;

public class SelectNode extends Command {
	
	private long nodeId;
	
	public void setNodeId(long nodeId) {
		T.call(this);
		
		this.nodeId = nodeId;
	}

	@Override
	protected void executeImpl(CommandTarget target) {
		T.call(this);
		
		((SelectNodeTarget) target).selectNode(nodeId);
	}

}
