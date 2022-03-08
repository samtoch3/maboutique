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
 * Servlet Filter implementation class FilterIsAdmin
 */
@WebFilter(dispatcherTypes = {
		DispatcherType.REQUEST, 
		DispatcherType.FORWARD
}, 
urlPatterns = { "/pages/admin/*" })
public class FilterIsAdmin implements Filter {
	
	@Inject
	private ModelConnexion modelConnexion;

    /**
     * Default constructor. 
     */
    public FilterIsAdmin() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (modelConnexion.isAdmin()) {
			 chain.doFilter( request, response );
		} else {
			 request.setAttribute( UtilJsf.MSG_ERROR, "Autorisation insuffisante." );
			 request.getRequestDispatcher( "/pages/home2.xhtml" ).forward( request, response );
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
