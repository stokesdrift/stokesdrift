package org.stokesdrift.container;

import java.util.List;

import org.apache.deltaspike.core.api.provider.BeanProvider;

public class ApplicationBuilderFactory {

	public ApplicationBuilder getBuilder(String appType) {
		return BeanProvider.getContextualReference(appType, false, ApplicationBuilder.class);
	}
	
	
	public List<ApplicationBuilder> list() {
		return BeanProvider.getContextualReferences(ApplicationBuilder.class, false);
	}
	
}
