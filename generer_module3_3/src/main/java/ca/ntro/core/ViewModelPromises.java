package ca.ntro.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.promises.ErrorListener;
import ca.ntro.core.promises.Promise;
import ca.ntro.core.promises.ValueListener;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.models.ViewModel;
import ca.ntro.core.views.NtroView;

public class ViewModelPromises {
	
	// XXX: not a multimap to keep JSweet compatibility
	private static final Map<Class<? extends NtroView>, List<Promise<NtroView>>> viewPromises = new HashMap<>();
	
	public static void installViewPromise(Class<? extends NtroView> viewClass, Promise<NtroView> viewPromise) {
		T.call(ViewModelPromises.class);

		if(viewPromises.containsKey(viewClass)) {
			
			viewPromises.get(viewClass).add(viewPromise);
			
		}else {
			
			List<Promise<NtroView>> promises = new ArrayList<>();
			
			promises.add(viewPromise);
			
			viewPromises.put(viewClass, promises);
		}
	}
	
	public static void installModelPromise(Class<? extends NtroView> viewClass, Promise<ViewModel> modelPromise) {
		T.call(ViewModelPromises.class);
		
		modelPromise.onValueOrError(new ValueListener<ViewModel>() {
			
			@Override
			public void onValue(ViewModel model) {
				T.call(this);
				
				if(viewPromises.containsKey(viewClass)) {
					for(Promise<NtroView> viewPromise : viewPromises.get(viewClass)) {
						
						viewPromise.onValueOrError(new ValueListener<NtroView>() {
							
							@Override
							public void onValue(NtroView view) {
								T.call(this);
								
								model.observeAndDisplay(view);
							}
						}, new ErrorListener() {
							
							@Override
							public void onError() {
								T.call(this);
								
							}
						});
						


						//model.installViewPromise(viewPromise);
					}
				}
			}

		}, new ErrorListener() {
			
			@Override
			public void onError() {
				T.call(this);
				
			}
		});
		
		
	}

}
