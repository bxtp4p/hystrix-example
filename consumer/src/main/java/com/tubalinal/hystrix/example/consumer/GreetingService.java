package com.tubalinal.hystrix.example.consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class GreetingService {
	
	@Value("${producer.endpoint:localhost}")
	private String producerEndpoint;
	
	@Value("${producer.port:9090}")
	private String producerPort;
	
    @HystrixCommand(fallbackMethod = "defaultGreeting")
    public String getGreeting(String username) {
    	
    	String endpoint = String.format("%s:%s", producerEndpoint, producerPort);
    	
        return new RestTemplate()
          .getForObject(endpoint + "/greeting/{username}", 
          String.class, username);
    }
  
    private String defaultGreeting(String username) {
        return "Hello User!";
    }

}
