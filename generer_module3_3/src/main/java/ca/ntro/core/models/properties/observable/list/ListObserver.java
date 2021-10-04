package ca.ntro.core.models.properties.observable.list;

import java.util.List;

import ca.ntro.core.models.properties.observable.simple.ValueObserver;

public interface ListObserver<I extends Object> extends ValueObserver<List<I>>, ItemAddedListener<I>, ItemUpdatedListener<I>, ItemRemovedListener<I> {

}
