package com.activity.activity1;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.stereotype.Component;

import com.activity.activity1.people.resource.OutputPeopleResource;
import com.activity.activity1.resource.OutputResource;

@Component
public class JerseyConfig extends ResourceConfig{
	public JerseyConfig() {
		register(OutputResource.class);
		register(OutputPeopleResource.class);
		property(ServletProperties.FILTER_FORWARD_ON_404, true);
	}
}
