package com.kwsp.route;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {


    private final ProducerTemplate producerTemplate;

    public StartupRunner(ProducerTemplate producerTemplate) {
        this.producerTemplate = producerTemplate;
    }

  

	@Override
	public void run(String... args) throws Exception {
		
		 Object requestBody = ""; // Your request body
	        
	        // Use the ProducerTemplate to send the message to the REST API route
	       // Object response = producerTemplate.requestBody("direct:readMember", requestBody);

	        // Process the response if needed
	        // For example, log the response
	       // System.out.println("Response from REST API: " + response);
		// TODO Auto-generated method stub
		
	}
}
