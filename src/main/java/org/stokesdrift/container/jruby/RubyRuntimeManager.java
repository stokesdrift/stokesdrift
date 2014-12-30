package org.stokesdrift.container.jruby;

import org.jruby.Ruby;
import org.jruby.exceptions.RaiseException;
import org.jruby.rack.RackContext;

public interface RubyRuntimeManager {
	
	public Ruby newRuntime() throws RaiseException;
	
	public RackContext getContext();
}
