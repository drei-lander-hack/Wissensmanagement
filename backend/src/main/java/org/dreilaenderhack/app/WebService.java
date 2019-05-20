package org.dreilaenderhack.app;

import static spark.Spark.halt;
import static spark.Spark.get;
import static spark.Spark.staticFileLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dreilaenderhack.model.Person;

import com.fasterxml.jackson.databind.ObjectMapper;



public class WebService {

	public static void main(String[] args) {

		
		 staticFileLocation("/htdocs");

		
		 get("/search",(request, response) -> {
			 

			 /*
			    String bearerToken = null;
				String auth = request.headers("Authorization");
				if(auth != null && auth.startsWith("Bearer")) {
					bearerToken = auth.substring("Bearer".length()).trim();
				}
			 
				if(bearerToken == null || bearerToken.isEmpty())
				{
					response.header("WWW-Authenticate", "Bearer");
			        halt(401, "You need a Bearer token");
				}
			 */
			 
			    response.type("application/json");
			  
			    List<Person> data = new ArrayList<Person>();
				Person p1 = new Person();
				p1.setName("Romeo");
				p1.setRank(99.8);
				p1.setImage("Romeao.jpg");
				
			    data.add(p1);
			    
			    return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data);
		 });
		 
		 
		 System.out.println("ready");
	}
		

}
