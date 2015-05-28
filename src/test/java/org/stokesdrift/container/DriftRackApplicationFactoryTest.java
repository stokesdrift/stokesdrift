package org.stokesdrift.container;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletContext;

import org.jruby.Ruby;
import org.jruby.RubyInstanceConfig;
import org.jruby.rack.servlet.DefaultServletRackContext;
import org.jruby.rack.servlet.ServletRackConfig;
import org.jruby.rack.servlet.ServletRackContext;
import org.jruby.runtime.builtin.IRubyObject;
import org.junit.Assert;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.stokesdrift.container.jruby.DriftRackApplicationFactory;

public class DriftRackApplicationFactoryTest {

	@Test
	public void testLoadPaths() throws Exception {		
		DriftRackApplicationFactory factory = new DriftRackApplicationFactory();
		ServletContext servletContext = PowerMockito.mock(ServletContext.class);
		ServletRackConfig config = new ServletRackConfig(servletContext);
		ServletRackContext context = new DefaultServletRackContext(config);

		// Shouldn't add since the gems direction doesn't exist
		URL configRuFile = this.getClass().getClassLoader().getResource("examples/config.ru");
		File file = new File(configRuFile.toURI());
		String jrubyHome = file.getParent();
		
		URI libUri = Ruby.class.getProtectionDomain().getCodeSource().getLocation().toURI();
		File libDirectory = new File(libUri);
		
		System.setProperty("STOKESDRIFT_LIB_DIR",libDirectory.getParent());
		System.setProperty("JRUBY_HOME",jrubyHome);
	    System.setProperty("GEM_PATH","test/home/gem_path");
	    
		factory.init(context);
		Ruby runtime = factory.newRuntime();
		Assert.assertNotNull(runtime);
				
		RubyInstanceConfig rackConfig = factory.getRuntimeConfig();
		List<String> loadPaths = rackConfig.getLoadPaths();
		Assert.assertTrue(loadPaths.size() > 0);
		System.out.println("LIBS " );
		for (String loadPath: loadPaths) {
			System.out.println(loadPath);
		}
		
		Assert.assertEquals(jrubyHome, rackConfig.getJRubyHome());
		
		IRubyObject rubyObject = runtime.executeScript("ENV.inspect", "jruby_home.rb");
		System.out.println("WHAT IS THIS " + rubyObject.asString());
		
	}	
}