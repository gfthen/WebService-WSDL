package com.isp.it.pshs0.adapter;

import javax.xml.ws.BindingProvider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.isp.it.pshs0.provider.JaxWebServiceProvider;

/**
 * 
 * @author TOSS
 *
 */
public abstract class BaseWebServiceAdapter {

    @Value("${ws.request.TimeOut}")
    private String requestTimeOut;

    @Value("${ws.response.TimeOut}")
    private String responseTimeOut;

    @Bean
    public JaxWebServiceProvider jaxWebServiceProvider(String endpoint, String serviceInterface, String serviceImplementation) {
	JaxWebServiceProvider provider = new JaxWebServiceProvider();
	provider.setEndPoint(endpoint);
	provider.setServiceInterface(serviceInterface);
	provider.setServiceImplementation(serviceImplementation);
	provider.setRequestTimeOut(requestTimeOut);
	provider.setResponseTimeOut(responseTimeOut);
	return provider;
    }

    public BindingProvider getBindingProvider(String endpoint, String serviceInterface, String serviceImplementation) throws ClassNotFoundException, NoSuchMethodException {
	return jaxWebServiceProvider(endpoint, serviceInterface, serviceImplementation).getWebServiceClient();
    }

}
