package org.stokesdrift.container.jruby;

import java.io.File;

import org.jruby.Ruby;
//import org.jruby.rack.ext.RackLibrary;
import org.jruby.rack.DefaultRackApplicationFactory;
import org.jruby.rack.RackContext;
import org.jruby.runtime.builtin.IRubyObject;

public class DriftRackApplicationFactory extends DefaultRackApplicationFactory implements RubyRuntimeManager {
	
	private RackContext rackServletContext;
	
	/**
	 * Creates a new application instance (without initializing it). <br/>
	 * NOTE: exception handling is left to the outer factory.
	 * 
	 * @return new application instance
	 */
	@Override
	public org.jruby.rack.RackApplication newApplication() {		
		return new JrubyRackApplicationImpl(new ApplicationObjectFactory() {
			public IRubyObject create(Ruby runtime) {
				System.out.println("TRYING TO CREATE");
				setLoadPaths(runtime);				
				// return createApplicationObject(runtime);
				return null;
			}
		}, this);
	}
	
	protected void setLoadPaths(Ruby runtime) {
	   String libDir = System.getProperty("STOKESDRIFT_LIB_DIR");
	   System.out.println(" WHAT ARE WE DOING " + libDir);
	   if (libDir != null) {
		  // TODO load from resources eg stokes_drift/header
		  // TODO add to jruby kernel some how
		  
		   
		  File dir = new File(libDir);
		  String[] fileList = dir.list();
		  for(String fileName : fileList) {
			  System.out.println("Setting up file: " + fileName);
		  }
//		  withHeader.append("jar_lib_dir = Dir[File.join(ENV['STOKESDRIFT_LIB_DIR'], '*.jar')]\n");
//		  withHeader.append("jar_lib_dir.each do |f|\n");
//		  withHeader.append(" next if f =~ /jruby-complete.+/\n");
//		  withHeader.append(" $CLASSPATH << f \n");
//		  withHeader.append(" require f \n");
//		  withHeader.append("end \n");	
	   }
	}
	
    /**
     * Initialize this factory using the given context.
     * <br/>
     * NOTE: exception handling is left to the outer factory.
     * @param rackContext
     */
    @Override
    public void init(final RackContext rackContext) {
       System.out.println("INITIING STUFF ");
       rackServletContext = rackContext;
       super.init(rackContext);
       
    }
    
//    protected void loadJRubyRack(final Ruby runtime) {
//        RackLibrary.load(runtime);
//    }
    
    public RackContext getContext()
    {
    	return rackServletContext;
    }
}
