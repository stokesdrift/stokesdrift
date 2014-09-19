package org.stokesdrift.config;

import org.junit.Assert;
import org.junit.Test;

public class OptionsTest {

	@Test
	public void testRootPathOption() {
		String[] args = new String[]{ "-r", "./"};
		Options options = new Options(args);
		String value = options.getValue(Options.Key.ROOT_PATH);
		Assert.assertEquals("./", value);
		
		Assert.assertEquals("drift_config.yml", options.getValue(Options.Key.CONFIG_FILE));
	}
	
}
