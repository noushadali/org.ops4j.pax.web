/* Copyright 2007 Alin Dreghiciu.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ops4j.pax.web.service;

import java.util.Dictionary;
import java.util.EventListener;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;

/**
 * WebContainer allows bundles to dynamically:<br/>
 * * register and unregister event listeners, for better control over the life cycle of ServletContext, HttpSession and
 * ServletRequest;<br/>
 * * register and unregister filters into the URI namespace of Http Service
 *
 * @author Alin Dreghiciu
 * @since 0.5.2
 */
public interface WebContainer
    extends HttpService
{

    /**
     * Registers a servlet.
     *
     * @param servlet     a servlet. Cannot be null.
     * @param urlPatterns url patterns this servlet maps to
     * @param initParams  initialization arguments for the servlet or null if there are none. This argument is used by
     *                    the servlet�s ServletConfig object.
     * @param httpContext the http context this servlet is for. If null a default http context will be used.
     *
     * @throws IllegalArgumentException if servlet is null, urlPattern is null or empty, or urlPattern is invalid
     * @throws ServletException         if servlet was already registered
     */
    void registerServlet( Servlet servlet,
                          String[] urlPatterns,
                          Dictionary initParams,
                          HttpContext httpContext )
        throws ServletException;

    /**
     * Unregisters a previously registered servlet.
     *
     * @param servlet the servlet to be unregistered
     *
     * @throws IllegalArgumentException if the servlet is null
     */
    void unregisterServlet( Servlet servlet );

    /**
     * Registers an event listener.
     * Depending on the listener type, the listener will be notified on different life cycle events. The following
     * listeners are supported:<br/>
     * HttpSessionActivationListener, HttpSessionAttributeListener, HttpSessionBindingListener, HttpSessionListener,
     * ServletContextListener, ServletContextAttributeListener, ServletRequestListener, ServletRequestAttributeListener.
     * Check out Servlet specification for details on what type of event the registered listener will be notified.
     *
     * @param listener    an event listener to be registered. If null an IllegalArgumentException is thrown.
     * @param httpContext the http context this listener is for. If null a default http context will be used.
     */
    void registerEventListener( EventListener listener, HttpContext httpContext );

    /**
     * Unregisters a previously registered listener.
     *
     * @param listener the event listener to be unregistered.
     *
     * @throws IllegalArgumentException if the listener is unknown to the http service (never registered or unregistered
     *                                  before) or the listener is null
     */
    void unregisterEventListener( EventListener listener );

    /**
     * Registers a servlet filter.
     *
     * @param filter       a servlet filter. If null an IllegalArgumentException is thrown.
     * @param urlPatterns  url patterns this filter maps to
     * @param servletNames servlet names this filter maps to
     * @param initparams   initialization arguments for the filter or null if there are none. This argument is used by
     *                     the filters�s FilterConfig object.
     * @param httpContext  the http context this filter is for. If null a default http context will be used.
     */
    void registerFilter( Filter filter,
                         String[] urlPatterns,
                         String[] servletNames,
                         Dictionary initparams,
                         HttpContext httpContext );

    /**
     * Unregisters a previously registered servlet filter.
     *
     * @param filter the servlet filter to be unregistered
     *
     * @throws IllegalArgumentException if the filter is unknown to the http service (never registered or unregistered
     *                                  before) or the filter is null
     */
    void unregisterFilter( Filter filter );

    /**
     * Sets context paramaters to be used in the servlet context corresponding to specified http context.
     * This method must be used before any register method that uses the specified http context, otherwise an
     * IllegalStateException will be thrown.
     *
     * @param params      context parameters for the servlet context corresponding to specified http context
     * @param httpContext http context. Cannot be null.
     *
     * @throws IllegalArgumentException if http context is null
     * @throws IllegalStateException    if the call is made after the http context was already used into a registration
     */
    void setContextParam( Dictionary params, HttpContext httpContext );

    /**
     * Enable jsp support.
     *
     * @param urlPatterns an array of url patterns this jsp support maps to. If null, a default "*.jsp" will be used
     * @param httpContext the http context for which the jsp context should be enabled. If null a default http context
     *                    will be used.
     *
     * @throws UnsupportedOperationException if optional org.ops4j.pax.web.jsp package is not resolved
     * @since 0.3.0, January 07, 2007
     */
    void registerJsps( String[] urlPatterns, HttpContext httpContext );

    /**
     * Unregister jsps and disable jsp support.
     *
     * @param httpContext the http context for which the jsp context should be disabled
     *
     * @throws IllegalArgumentException      if http context is null or jsp support was not enabled for the http context
     * @throws UnsupportedOperationException if optional org.ops4j.pax.web.jsp package is not resolved
     * @since 0.3.0, January 07, 2007
     */
    void unregisterJsps( HttpContext httpContext );

}
