/**
 * Copyright (C) 2016 Luis Moral Guerrero <luis.moral@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package es.molabs.configuration.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import es.molabs.configuration.ConfigurationManager;

@RunWith(MockitoJUnitRunner.class)
public class ConfigurationManagerTest 
{	
	@Test
	public void testInitialization() throws Throwable
	{
		ConfigurationManager configurationManager = new ConfigurationManager();
		configurationManager.addFile(getClass().getResource("/es/molabs/configuration/test/manager/zero.properties"));
		
		// Checks that the properties are loaded
		testGetString(configurationManager, "test.property1", null);
		
		// Initialized the manager
		configurationManager.init();
		
		// Checks that the properties are loaded
		testGetString(configurationManager, "test.property1", "value1");
		
		// Checks that it is initialized
		boolean expectedValue = true;
		boolean value = configurationManager.isInitialized();
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
				
		// Destroys the manager
		configurationManager.destroy();
		
		// Checks that the properties are unloaded
		testGetString(configurationManager, "test.property1", null);		
		
		// Checks that it is not initialized
		expectedValue = false;
		value = configurationManager.isInitialized();
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		// Initializes the manager again
		configurationManager.init();
		
		// Checks that the properties are loaded
		testGetString(configurationManager, "test.property1", "value1");
		
		// Checks that it is initialized
		expectedValue = true;
		value = configurationManager.isInitialized();
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
		
		configurationManager.destroy();
	}
	
	@Test
	public void testTypes() throws Throwable
	{
		ConfigurationManager configurationManager = new ConfigurationManager();
		configurationManager.addFile(getClass().getResource("/es/molabs/configuration/test/manager/type.properties"));
		configurationManager.init();
		
		Assert.assertEquals("Value must be [" + "text" + "].", "text", configurationManager.getString("test.string.1"));
		Assert.assertEquals("Value must be [" + true + "].", true, configurationManager.getBoolean("test.boolean.1"));
		Assert.assertEquals("Value must be [" + 1213 + "].", 1213, configurationManager.getShort("test.short.1").shortValue());		
		Assert.assertEquals("Value must be [" + 121131213 + "].", 121131213, configurationManager.getInteger("test.int.1").intValue());
		Assert.assertEquals("Value must be [" + 39459320953495l + "].", 39459320953495l, configurationManager.getLong("test.long.1").longValue());
		Assert.assertEquals("Value must be [" + 1.01f + "].", 1.01f, configurationManager.getFloat("test.float.1").floatValue(), 0.01f);
		Assert.assertEquals("Value must be [" + 2.01d + "].", 2.01d, configurationManager.getDouble("test.double.1").doubleValue(), 0.01d);		
		
		configurationManager.destroy();
	}
	
	private void testGetString(ConfigurationManager configurationManager, String property, String expectedValue)
	{	
		String value = configurationManager.getString(property);
		Assert.assertEquals("Value must be [" + expectedValue + "].", expectedValue, value);
	}
}