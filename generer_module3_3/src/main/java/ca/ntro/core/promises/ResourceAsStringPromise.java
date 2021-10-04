package ca.ntro.core.promises;

import ca.ntro.core.system.file.Resource;

public class ResourceAsStringPromise  implements Promise<String> {
	
	private Promise<Resource> resourcePromise;
	
	public ResourceAsStringPromise(Promise<Resource> resourcePromise) {
		this.resourcePromise = resourcePromise;
	}
	
	@Override
	public void onValueOrError(ValueListener<String> valueListener, ErrorListener errorListener) {
		resourcePromise.onValueOrError(new ValueListener<Resource>() {
			@Override
			public void onValue(Resource value) {

				value.getString().onValueOrError(valueListener, errorListener);

			}

		}, errorListener);
	}

}
