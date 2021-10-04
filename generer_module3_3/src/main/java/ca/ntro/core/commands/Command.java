package ca.ntro.core.commands;

import ca.ntro.core.promises.ErrorListener;
import ca.ntro.core.promises.Promise;
import ca.ntro.core.promises.ValueListener;
import ca.ntro.core.system.debug.T;

public abstract class Command {
	
	private Promise<CommandTarget> targetPromise;

	void setTargetPromise(Promise<CommandTarget> targetPromise) {
		this.targetPromise = targetPromise;
	}
	
	public void execute() {
		targetPromise.onValueOrError(new ValueListener<CommandTarget>() {
			
			@Override
			public void onValue(CommandTarget target) {
				T.call(this);
				
				executeImpl(target);

			}
		}, new ErrorListener() {
			
			@Override
			public void onError() {
				T.call(this);
				
			}
		});
	}
	
	protected abstract void executeImpl(CommandTarget target);

}
