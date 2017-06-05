package com.isp.it.pshs0.provider;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.isp.it.pshs0.util.WebServiceConstants;

/**
 * 
 * @author U0I2747
 * 
 */
@Component
public class JaxWebServiceProvider implements InitializingBean {

    private String endPoint;

    private String serviceInterface;

    private String serviceImplementation;

    private String requestTimeOut;

    private String responseTimeOut;

    private static Map<String, Object> cacheServiceProvider = new ConcurrentHashMap<String, Object>();

    public BindingProvider getWebServiceClient() throws ClassNotFoundException, NoSuchMethodException {
	try {
	    Service serviceProvider = null;
	    URL url = new URL(getEndPoint());

	    BindingProvider bindingProvider = (BindingProvider) cacheServiceProvider.get(url.getPath());
	    if (bindingProvider == null) {
		Class<?> serviceClass = Class.forName(getServiceImplementation());
		Constructor<?> baseConstructor = serviceClass.getConstructor(URL.class);
		serviceProvider = (Service) baseConstructor.newInstance(url);

		Class<?> serviceInterface = Class.forName(getServiceInterface());

		// Check and eventually set the cache
		bindingProvider = (BindingProvider) serviceProvider.getPort(serviceInterface);
		// Set webservice timeout
		bindingProvider.getRequestContext().put(WebServiceConstants.RESPONSE_TIMEOUT, Integer.parseInt(getResponseTimeOut()));
		bindingProvider.getRequestContext().put(WebServiceConstants.RESPONSE_CONNECT_TIMEOUT, Integer.parseInt(getResponseTimeOut()));
		bindingProvider.getRequestContext().put(WebServiceConstants.REQUES_TIMEOUT, Integer.parseInt(getRequestTimeOut()));
		bindingProvider.getRequestContext().put(WebServiceConstants.REQUEST_CONNECT_TIMEOUT, Integer.parseInt(getRequestTimeOut()));
		bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getEndPoint().split("[?]")[0]);
		cacheServiceProvider.put(url.getPath(), bindingProvider);

	    }

	    return bindingProvider;
	} catch (MalformedURLException e) {
	    return null;
	} catch (InstantiationException e) {

	    return null;
	} catch (IllegalAccessException e) {

	    return null;
	} catch (IllegalArgumentException e) {

	    return null;
	} catch (InvocationTargetException e) {

	    return null;
	}

    }

    public String getEndPoint() {
	return endPoint;
    }

    public void setEndPoint(String endPoint) {
	this.endPoint = endPoint;
    }

    public String getServiceInterface() {
	return serviceInterface;
    }

    public void setServiceInterface(String serviceInterface) {
	this.serviceInterface = serviceInterface;
    }

    public String getServiceImplementation() {
	return serviceImplementation;
    }

    public void setServiceImplementation(String serviceImplementation) {
	this.serviceImplementation = serviceImplementation;
    }

    public String getRequestTimeOut() {
	return requestTimeOut;
    }

    public void setRequestTimeOut(String requestTimeOut) {
	this.requestTimeOut = requestTimeOut;
    }

    public String getResponseTimeOut() {
	return responseTimeOut;
    }

    public void setResponseTimeOut(String responseTimeOut) {
	this.responseTimeOut = responseTimeOut;
    }

    public static Map<String, Object> getCacheServiceProvider() {
	if (cacheServiceProvider == null)
	    cacheServiceProvider = new HashMap<String, Object>();

	return cacheServiceProvider;
    }

    public void afterPropertiesSet() throws Exception {
	// TODO
    }

}
