package org.stokesdrift.container;

import javax.servlet.DispatcherType;

import org.jruby.rack.RackFilter;
import org.jruby.rack.RackServletContextListener;

import com.mchange.v2.sql.filter.FilterStatement;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.FilterInfo;
import io.undertow.servlet.api.FilterMappingInfo;
import io.undertow.servlet.api.FilterMappingInfo.MappingType;
import io.undertow.servlet.api.ListenerInfo;
import io.undertow.servlet.api.ServletInfo;
import junit.framework.TestCase;

public class UndertowTest extends TestCase {
	
	public void testUndertowLoading() throws Exception {
		int port = 8888;
		// ServletInfo servletInfo = Servlets.servlet("", YourServlet.class).setAsyncSupported(true)
		// 	    .setLoadOnStartup(1).addMapping("/*");
		
			FilterInfo filter = Servlets.filter("RackFilter", RackFilter.class);
			ListenerInfo listenerInfo = Servlets.listener(RackServletContextListener.class);
			DeploymentInfo di = new DeploymentInfo()
					.addListener(listenerInfo)
			        .setContextPath("/")
			        // .addInitParameter(name, value)
			        .addFilter(filter)
			        .addFilterUrlMapping("RackFilter", "/*", DispatcherType.ASYNC)
			        // .addServlet(servletInfo)
			        .setDeploymentName("RackServlet")
			        .setClassLoader(ClassLoader.getSystemClassLoader());
			DeploymentManager deploymentManager = Servlets.defaultContainer().addDeployment(di);
			deploymentManager.deploy();
			Undertow server = Undertow.builder()
			        .addHttpListener(port, "localhost")
			        .setHandler(deploymentManager.start())
			        .build();
			server.start();

	}

}


		                                                                                                                               //			.setHandler(new HttpHandler() {                                                  //					                                                                         //					@Override                                                                //					public void handleRequest(HttpServerExchange exchange) throws Exception {//					  System.out.println(exchange);                                          //					}                                                                        //				})                                                                           //        	.build();                                                                        