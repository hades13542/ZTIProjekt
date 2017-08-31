package rest;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Base method for rest interface. 
 * In getClasses() method we specify with resources will be handled via REST interface. 
 */
@ApplicationPath("rest")
public class RestBase extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new java.util.HashSet<>();
		resources.add(RestBook.class);
		return resources;
	}
}