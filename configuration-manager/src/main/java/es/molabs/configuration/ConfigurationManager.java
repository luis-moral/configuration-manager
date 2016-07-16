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
package es.molabs.configuration;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.molabs.io.utils.NodePropertiesBundle;
import es.molabs.properties.NodePropertiesToken;

public class ConfigurationManager 
{
	private final Logger logger = LoggerFactory.getLogger(getClass());
		
	private NodePropertiesBundle nodePropertiesBundle = null;
	
	private boolean initialized;
	
	public ConfigurationManager(NodePropertiesToken...tokenList)
	{
		this(Arrays.asList(tokenList));
	}
	
	public ConfigurationManager(List<NodePropertiesToken> tokenList)
	{
		nodePropertiesBundle = new NodePropertiesBundle(tokenList);
		
		initialized = false;
	}
	
	public void init()
	{
		if (!initialized)
		{
			try
			{
				nodePropertiesBundle.init();
			}
			catch (IOException IOe)
			{
				throw new IllegalArgumentException(IOe);
			}
			
			// Sets the manager as initialized
			initialized = true;
			
			logger.info("Initialized.");
		}
		else
		{
			logger.warn("Already initialized.");
		}
	}
	
	public void destroy()
	{
		if (initialized)
		{
			// Sets the manager as not initialized
			initialized = false;
			
			try
			{
				nodePropertiesBundle.destroy();
			}
			catch (IOException IOe)
			{
				logger.error(IOe.getLocalizedMessage(), IOe);
			}			
			
			logger.info("Destroyed.");
		}
		else
		{
			logger.warn("Already destroyed.");
		}
	}
	
	public boolean isInitialized()
	{
		return initialized;
	}
	
	public void addFile(URL resource) throws IOException
	{
		nodePropertiesBundle.addFile(resource);
	}
	
	public void addFile(URL resource, String encoding) throws IOException
	{
		nodePropertiesBundle.addFile(resource, encoding);
	}
	
	public String getString(String key)
	{
		return nodePropertiesBundle.getString(key);
	}
	
	public Boolean getBoolean(String key)
	{
		return Boolean.parseBoolean(nodePropertiesBundle.getString(key));
	}
	
	public Short getShort(String key)
	{
		return Short.parseShort(getString(key));
	}
	
	public Integer getInteger(String key)
	{
		return Integer.parseInt(getString(key));
	}
	
	public Long getLong(String key)
	{
		return Long.parseLong(getString(key));
	}
	
	public Float getFloat(String key)
	{
		return Float.parseFloat(getString(key));
	}
	
	public Double getDouble(String key)
	{
		return Double.parseDouble(getString(key));
	}
}