package ar.com.bunge;


import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.jndi.ReadOnlyContext;

public class FallbackContext extends ReadOnlyContext {

	private Context context;
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
			environment2.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
			lookup = new InitialContext(environment2).lookup(name);
			
		}
		return lookup;
	}

}
