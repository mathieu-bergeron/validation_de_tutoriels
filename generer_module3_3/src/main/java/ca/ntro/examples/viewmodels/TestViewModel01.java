package ca.ntro.examples.viewmodels;


import ca.ntro.core.commands.CommandFactory;
import ca.ntro.core.models.ViewModel;
import ca.ntro.core.models.properties.observable.simple.ValueListener;
import ca.ntro.core.models.properties.observable.simple.ValueObserver;
import ca.ntro.core.models.properties.stored.simple.StoredString;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import ca.ntro.examples.commands.change_text.ChangeText;
import ca.ntro.examples.commands.change_text.ChangeTextTarget;
import ca.ntro.examples.models.TestModel01;
import ca.ntro.examples.views.TestView01;

public class TestViewModel01 extends TestModel01 implements ViewModel {

	

	private int commandCounter = 0;

	public TestViewModel01() {
		super();
		T.call(this);
		
		// FIXME: make sure this is always called
		installCommandTargets();
	}


	private void installCommandTargets() {
		T.call(this);

		CommandFactory.installTarget(ChangeText.class, new ChangeTextTarget() {
			@Override
			public void changeText(String text) {
				T.call(this);
				
				commandCounter++;
				getStoredString().set("" + commandCounter + " " + text);
			}
		});
	}

	@Override
	public void observeAndDisplay(NtroView view) {
		T.call(this);

		getStoredString().observe(new ValueObserver<String>() {

			@Override
			public void onValue(String value) {
				T.call(this);

				((TestView01) view).displayText(value);
			}

			@Override
			public void onDeleted(String lastValue) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onValueChanged(String oldValue, String value) {
				T.call(this);

				((TestView01) view).displayText(value);
			}
		});
		
	}
}
