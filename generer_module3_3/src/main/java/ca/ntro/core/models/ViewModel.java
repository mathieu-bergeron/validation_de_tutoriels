package ca.ntro.core.models;

import ca.ntro.core.views.NtroView;

public interface ViewModel {

	void observeAndDisplay(NtroView view);
	

	/*
	public void installViewPromise(Promise<NtroView> viewPromise) {
		T.call(this);
		
		viewPromise.onValueOrError(new ValueListener<NtroView>() {

			@Override
			public void onValue(NtroView view) {
				T.call(this);
				
				observeAndDisplay(view);
			}

		}, new ErrorListener() {
			
			@Override
			public void onError() {
				T.call(this);
				
			}
		});
	}
	*/
	

	

}
