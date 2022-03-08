package maboutique.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import maboutique.model.ModelConnexion;
import maboutique.util.UtilJsf;

/**
 * Servlet Filter implementation class FilterLonggedIn
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD
		}, 
		urlPatterns = { "/pages/*", "/javax.faces.resource/pdf/*" })

public class FilterLoggedIn implements Filter {
	
	@Inject
	private ModelConnexion modelConnexion;

    /**
     * Default constructor. 
     */
    public FilterLoggedIn() {
        
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		if (modelConnexion.isLoggedIn()) {
			 chain.doFilter( request, response );
		} else {
			 request.setAttribute( UtilJsf.MSG_ERROR, "Autorisation insuffisante." );
			 request.getRequestDispatcher( "/login2.xhtml" ).forward( request, response );
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
