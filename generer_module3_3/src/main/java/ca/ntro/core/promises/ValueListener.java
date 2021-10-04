package ca.ntro.core.promises;

public interface ValueListener<T extends Object> {
	
	void onValue(T value);

}
