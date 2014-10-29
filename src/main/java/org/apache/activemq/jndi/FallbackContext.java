package org.apache.activemq.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author ftroya
 *
 */
public class FallbackContext extends ReadOnlyContext {

	static String INITIAL_CONTEXT_FACTORY_FALLBACK = "java.naming.factory.initial.fallback";
	
	public FallbackContext(ReadOnlyContext createContext)
			throws NamingException {
		super(createContext, createContext.getEnvironment());
	}

	@Override
	public Object lookup(String name) throws NamingException {
		Object lookup = null;
		try{
			lookup = super.lookup(name);
		}
		catch(NamingException e){
			Hashtable environment2 = (Hashtable<?, ?>) this.getEnvironment().clone();
			String initialContextFactoryFallback=(String)this.getEnvironment().get(INITIAL_CONTEXT_FACTORY_FALLBACK);
			environment2.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactoryFallback);
            lookup = new InitialContext(environment2).lookup(name);			
		}
		return lookup;
	}

}
