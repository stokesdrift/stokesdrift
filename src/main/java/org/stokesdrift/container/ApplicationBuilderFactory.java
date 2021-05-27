package org.stokesdrift.container;

import java.util.Iterator;

import org.jboss.weld.literal.NamedLiteral;

import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

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
