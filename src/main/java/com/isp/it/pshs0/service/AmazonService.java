package com.isp.it.pshs0.service;

import java.util.List;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.isp.it.pshs0.adapter.BaseWebServiceAdapter;
import com.isp.it.pshs0.model.AWSECommerceServicePortType;
import com.isp.it.pshs0.model.BrowseNodeLookupRequest;
import com.isp.it.pshs0.model.BrowseNodes;
import com.isp.it.pshs0.model.OperationRequest;
import com.isp.it.pshs0.provider.JaxWebServiceDecorator;

@Service
@Configuration
@PropertySource("classpath:application.properties")
public class AmazonService extends BaseWebServiceAdapter implements JaxWebServiceDecorator {

    @Value("${isp.otcv0.ws.amazon.url}")
    private String endpoint;

    @Value("com.isp.it.pshs0.AWSECommerceServicePortType")
    private String serviceInterface;

    @Value("com.isp.it.pshs0.AWSECommerceService")
    private String serviceImplementation;

    public void browseNodeLookup(Holder<OperationRequest> operationRequest, String validate, String xmlEscaping, String associateTag, List<BrowseNodeLookupRequest> request,
	    BrowseNodeLookupRequest shared, Holder<List<BrowseNodes>> browseNodes, String marketplaceDomain, String awsAccessKeyId) throws ClassNotFoundException,
	    NoSuchMethodException {
	getWebService().browseNodeLookup(marketplaceDomain, awsAccessKeyId, associateTag, validate, xmlEscaping, shared, request, operationRequest, browseNodes);
    }

    private AWSECommerceServicePortType getWebService() throws ClassNotFoundException, NoSuchMethodException {
	BindingProvider service = getBindingProvider(endpoint, serviceImplementation, serviceInterface);
	return ((AWSECommerceServicePortType) service);
    }

    @Override
    public String getEndPoint() {
	return endpoint;
    }

    @Override
    public String getService() {
	return serviceImplementation;
    }

    @Override
    public String getServiceInterface() {
	return serviceInterface;
    }
}
