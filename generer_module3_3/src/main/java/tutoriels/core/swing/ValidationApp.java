package tutoriels.core.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import ca.ntro.core.ViewModelPromises;
import ca.ntro.core.app.NtroApp;
import ca.ntro.core.models.ViewModel;
import ca.ntro.core.models.stores.MemoryStore;
import ca.ntro.core.promises.Promise;
import ca.ntro.core.promises.PromiseSync;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import ca.ntro.swing.InitializerSwing;
import tutoriels.core.app.MainWindowClosedListener;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.app.CurrentExercise;
import tutoriels.core.swing.views.ValidationAppMainViewSwing;

public class ValidationApp extends NtroApp {

	static {

		InitializerSwing.initialize();

	}

	private ValidationAppMainViewSwing mainView;
	private ReportViewModel mainModel;
	
	private MainWindowClosedListener mainWindowClosedListener;

	public ValidationApp() {
		super();
		T.call(this);

		initialize();
	}
	
	public ValidationApp(MainWindowClosedListener mainWindowClosedListener) {
		super();
		T.call(this);
		
		this.mainWindowClosedListener = mainWindowClosedListener;

		initialize();
	}

	private void initialize() {
		T.call(this);

		// XXX: Swing code in Swing'g GUI thread
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createApp();
			}
		});
	}

	private void createApp() {
		T.call(this);

		createMainModel();
		createMainView();
		installOnCloseListener();

		// FIXME: should be called from Ntro
		step01InstallViewPromises();
		step02InstallModelPromises();
	}
	
	private void createMainView() {
		T.call(this);

		mainView = new ValidationAppMainViewSwing(CurrentExercise.getId());
		mainView.initialize();
	}

	private void installOnCloseListener() {
		T.call(this);

		mainView.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				T.call(this);

				mainView.dispose();
				if(mainWindowClosedListener != null) {
					mainWindowClosedListener.mainWindowClosed();
				}
			}
		});
	}

	private void createMainModel() {
		T.call(this);

		mainModel = MemoryStore.get(ReportViewModel.class, CurrentExercise.getId());
	}

	@Override
	protected void step01InstallViewPromises() {

		Promise<NtroView> viewPromise = new PromiseSync<NtroView>(mainView);
		ViewModelPromises.installViewPromise(ValidationAppMainViewSwing.class, viewPromise);

	}

	@Override
	protected void step02InstallModelPromises() {
		T.call(this);
		
		Promise<ViewModel> modelPromise = new PromiseSync<ViewModel>(mainModel);
		ViewModelPromises.installModelPromise(ValidationAppMainViewSwing.class, modelPromise);
	}
}
