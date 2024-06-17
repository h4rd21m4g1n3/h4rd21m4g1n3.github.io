package cz.cvut.fel.nss.transactions.transactionmodule.interseptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


/**
 * Intercepts requests related to expenses and logs information during the handling of these requests.
 */
@Component
public class ExpenseInterceptor implements HandlerInterceptor {

    private Logger LOG = LoggerFactory.getLogger(ExpenseInterceptor.class);


    /**
     * Invoked before the actual handler is executed. Logs the request URI and method.
     *
     * @param request  the current HTTP request
     * @param response the current HTTP response
     * @param handler  the chosen handler to execute
     * @return {@code true} to continue processing the request; {@code false} to abort
     * @throws Exception in case of errors
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOG.info("preHandle invoked...{}:{}" + request.getRequestURI(), request.getMethod());
        return true;
    }

    /**
     * Invoked after the handler is executed, but before the view is rendered. Logs the request URI and method.
     *
     * @param request      the current HTTP request
     * @param response     the current HTTP response
     * @param handler      the executed handler
     * @param modelAndView the {@code ModelAndView} that the handler returned
     * @throws Exception in case of errors
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOG.info("postHandle invoked...{}:{}" + request.getRequestURI(), request.getMethod());
    }

    /**
     * Invoked after the complete request has finished. Logs any exception that occurred and the request URI and method.
     *
     * @param request  the current HTTP request
     * @param response the current HTTP response
     * @param handler  the executed handler
     * @param ex       any exception thrown on handler execution, if any
     * @throws Exception in case of errors
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (ex != null) {
            LOG.error("Exception inside afterCompletion " + ex.getMessage());
        }
        LOG.info("afterHandle invoked...{}:{}" + request.getRequestURI(), request.getMethod());
    }
}
