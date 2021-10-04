package ca.ntro.core.promises;

public class PromiseSync<T extends Object> implements Promise<T> {
	
	private T value;
	
	public PromiseSync(T value) {
		this.value = value;
	}

	@Override
	public void onValueOrError(ValueListener<T> valueListener, ErrorListener errorListener) {
		if(value != null) {
			valueListener.onValue(value);
		}else {
			errorListener.onError();
		}
	}
}
