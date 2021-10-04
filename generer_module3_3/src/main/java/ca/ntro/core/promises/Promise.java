package ca.ntro.core.promises;

public interface Promise<T extends Object>{
	
	// TODO: onValue installs a generic ErrorListener
	//void onValue(ValueListener<T> valueListener);

	void onValueOrError(ValueListener<T> valueListener, ErrorListener errorListener);

}
