package org.stokesdrift.container;

import java.util.Iterator;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.weld.literal.NamedLiteral;

public class ApplicationBuilderFactory {

	@Inject @Any
	Instance<ApplicationBuilder> builder;
	
	public ApplicationBuilder getBuilder(String appType) {
		appType = new StringBuilder(appType).append("_builder").toString();		
		return builder.select(new NamedLiteral(appType) ).get();
	}
	
	
	public Iterator<ApplicationBuilder> list() {		
		return builder.iterator();
	}

}
