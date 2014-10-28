/**
 * 
 */
package ar.com.bunge;

import java.util.Hashtable;
import java.util.Map;

import javax.naming.NamingException;

import org.apache.activemq.jndi.ActiveMQInitialContextFactory;
import org.apache.activemq.jndi.ReadOnlyContext;

/**
 * @author ftroya
 *
 */
public class BungeActiveMQInitialContextFactory extends
		ActiveMQInitialContextFactory {

	@Override
	protected ReadOnlyContext createContext(Hashtable environment,
			Map<String, Object> data) {
		FallbackContext createContext = null;
		try {
			createContext = new FallbackContext(super.createContext(environment, data));
		} catch (NamingException e) {
		}
		return createContext;
	}

	
	
}
