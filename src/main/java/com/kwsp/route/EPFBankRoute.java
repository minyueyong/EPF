package com.kwsp.route;


import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.kwsp.model.EPFAccDetails;
import com.kwsp.repository.EPFAccRepository;
import com.kwsp.service.EPFBankService;


@Component
public class EPFBankRoute extends RouteBuilder {
    
	@Value("${camel.servlet.mapping.context-path}")
	private String contextPath;



	@Autowired
	private Environment env;



	  @Autowired
	  private EPFBankService bankService;


    @Override
    public void configure() throws Exception {
    	

		 
		// @formatter:off
	        
	        // this can also be configured in application.properties
	        restConfiguration()
	            .component("servlet")
	            //.bindingMode(RestBindingMode.json)   //global configuration, incoming and outgoing request are json
	            .dataFormatProperty("prettyPrint", "true")
	            .enableCORS(true)
	            .port(env.getProperty("server.port", "8080"))
	            .contextPath(contextPath.substring(0, contextPath.length() - 2))   //change it to /epf
	           ;

	        rest().description("Actor REST service")
	           // .consumes("application/json")  //incoming request is json
	            //.produces("application/json")   //outgoing request is also json

	            .get("/bank/details")
                .to("direct:readBankDetails");
                
             
		        
	        
	  
	        
	        
	        
	        from("direct:readBankDetails")
	        .log("get into route")
	        .bean( bankService, "getBankAccountDetails")
	        .marshal().json(JsonLibrary.Jackson) // Serialize MyViewModel objects to JSON
            .log("Bank data: ${body}"); // Log the JSON data
            

	       

    }
    
 
    	
    	
}
