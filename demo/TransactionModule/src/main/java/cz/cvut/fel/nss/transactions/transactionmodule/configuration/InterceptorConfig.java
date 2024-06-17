package cz.cvut.fel.nss.transactions.transactionmodule.configuration;

import cz.cvut.fel.nss.transactions.transactionmodule.interseptor.ExpenseInterceptor;
import cz.cvut.fel.nss.transactions.transactionmodule.interseptor.IncomeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up interceptors in the Spring MVC context.
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private ExpenseInterceptor generalInterseptor;

    @Autowired
    private IncomeInterceptor incomeInterceptor;

    /**
     * Adds interceptors to the registry.
     *
     * @param registry the interceptor registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(generalInterseptor).addPathPatterns("/transactions/expenses/**");
        registry.addInterceptor(incomeInterceptor).addPathPatterns("/transactions/incomes/**");
    }
}
