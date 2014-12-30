package org.stokesdrift.container.jruby;

import org.jruby.exceptions.RaiseException;
import org.jruby.rack.RackContext;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

public class RubyUtil {

	private RackContext rackContext;

	public RubyUtil(RackContext rackContext) {
		this.rackContext = rackContext;
	}

	public void captureMessage(final RaiseException re) {
		try {
			IRubyObject rubyException = re.getException();
			ThreadContext context = rubyException.getRuntime().getCurrentContext();
			// JRuby-Rack internals (@see jruby/rack/capture.rb) :
			rubyException.callMethod(context, "capture");
			rubyException.callMethod(context, "store");
		} catch (Exception e) {
			rackContext.log("INFO", "failed to capture exception message", e);
		}
	}

}
