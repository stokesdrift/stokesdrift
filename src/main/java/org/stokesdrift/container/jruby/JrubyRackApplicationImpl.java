package org.stokesdrift.container.jruby;

import org.jruby.Ruby;
import org.jruby.exceptions.RaiseException;
import org.jruby.rack.DefaultRackApplication;

public class JrubyRackApplicationImpl extends DefaultRackApplication {

    protected final Ruby runtime;
    final ApplicationObjectFactory appFactory;
    final RubyUtil utils;

    JrubyRackApplicationImpl(ApplicationObjectFactory appFactory, RubyRuntimeManager manager) {
        this.runtime = manager.newRuntime();
        this.appFactory = appFactory;
        this.utils = new RubyUtil(manager.getContext());
    }

    @Override
    public void init() {
        try {
            setApplication(appFactory.create(runtime));
        }
        catch (RaiseException e) {
        	utils.captureMessage(e);
            throw e;
        }
    }

    
    @Override
    public void destroy() {
        runtime.tearDown(false);
    }

}