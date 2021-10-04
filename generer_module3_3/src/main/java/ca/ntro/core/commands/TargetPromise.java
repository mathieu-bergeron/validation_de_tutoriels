package ca.ntro.core.commands;

import ca.ntro.core.promises.ErrorListener;
import ca.ntro.core.promises.Promise;
import ca.ntro.core.promises.ValueListener;

public class TargetPromise implements Promise<CommandTarget>{
	
	private ValueListener<CommandTarget> valueListener;
	private CommandTarget target;
	
	void installTarget(CommandTarget target) {
		this.target = target;
		if(valueListener != null) {
			valueListener.onValue(target);
		}
	}

	@Override
	public void onValueOrError(ValueListener<CommandTarget> valueListener, ErrorListener errorListener) {
		this.valueListener = valueListener;

		if(target != null) {
			valueListener.onValue(target);
		}
	}

}
