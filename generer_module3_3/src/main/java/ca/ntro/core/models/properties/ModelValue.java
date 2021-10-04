package ca.ntro.core.models.properties;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.json.JsonObjectIO;
import ca.ntro.core.models.ModelStore;
import ca.ntro.core.models.StoreConnectable;
import ca.ntro.core.models.stores.ValuePath;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;


/**
 * Every user-defined value inside a model
 * must be a subclass of ModelValue
 * 
 * @author mbergeron
 *
 */
public abstract class ModelValue extends JsonObjectIO implements StoreConnectable {

	@Override
	public void connectToStore(ValuePath valuePath, ModelStore modelStore) {
		T.call(this);

		connectSubValues(valuePath, modelStore);
	}

	private void connectSubValues(ValuePath valuePath, ModelStore modelStore) {
		T.call(this);
		
		for(Method getter : Introspector.userDefinedGetters(this)) {
			
			String fieldName = Introspector.fieldNameForGetter(getter);
			
			Object fieldValue = null;
			
			try {

				fieldValue = getter.invoke(this);

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				
				Log.fatalError("[ModelValue] Cannot invoke getter " + getter.getName(), e);
				
			}
			
			if(fieldValue instanceof StoreConnectable) {
				
				valuePath.addFieldName(fieldName);
				
				((StoreConnectable) fieldValue).connectToStore(valuePath, modelStore);
			}
		}
	}
}
