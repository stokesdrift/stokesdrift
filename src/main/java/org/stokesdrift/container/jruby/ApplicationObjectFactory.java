package org.stokesdrift.container.jruby;

import org.jruby.Ruby;
import org.jruby.runtime.builtin.IRubyObject;

public interface ApplicationObjectFactory {
	IRubyObject create(Ruby runtime);
}