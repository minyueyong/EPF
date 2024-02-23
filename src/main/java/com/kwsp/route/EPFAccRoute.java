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


@Component
public class EPFAccRoute extends RouteBuilder {
    
	@Value("${camel.servlet.mapping.context-path}")
	private String contextPath;



	@Autowired
	private Environment env;

	@Autowired
	private EPFAccRepository repository;

	

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
	            .contextPath(contextPath.substring(0, contextPath.length() - 2))   //change it to /api
	           ;

	        rest().description("Actor REST service")
	           // .consumes("application/json")  //incoming request is json
	            //.produces("application/json")   //outgoing request is also json

	            .get("/account/details")
                .to("direct:readMember");
                
             
		        
	        
	  
	        
	        
	        
	        from("direct:readMember")
	        .log("get into route")
	        .bean(this, "readMember")
	        .marshal().json(JsonLibrary.Jackson) // Serialize MyViewModel objects to JSON
            .log("Member data: ${body}"); // Log the JSON data
            

	       

    }
    
    public List<EPFAccDetails> readMember() {
        return repository.findAll(); // Return the list of EPFAccDetails objects
    }
    	
    	
    	
}
