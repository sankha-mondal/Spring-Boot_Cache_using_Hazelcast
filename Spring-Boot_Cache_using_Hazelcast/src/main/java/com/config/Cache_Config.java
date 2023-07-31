package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;

// ==================  Step:2. Create cache Configuration:- ==================

@Configuration
public class Cache_Config {
	
	@Bean
	public Config cacheConfig_allUser() {
		return new Config()
				.setInstanceName("hazel-instance_1")  // set a unique name, name can be anything
				.addMapConfig(new MapConfig()       // add the Configuration
				.setName("user-cache_all")  			// name of cache
				.setTimeToLiveSeconds(3000));       // amount of time of object to live in cache | (-1) to alive forever
	}
	
	@Bean
	public Config cacheConfig_singleUser() {
		return new Config()
				.setInstanceName("hazel-instance_2")  // set a unique name, name can be anything
				.addMapConfig(new MapConfig()       // add the Configuration
				.setName("user-cache_single")  			// name of cache
				.setTimeToLiveSeconds(3000));       // amount of time of object to live in cache | (-1) to alive forever
	}
}
