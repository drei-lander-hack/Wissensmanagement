package org.dreilaenderhack.app;

import static spark.Spark.halt;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.after;
import static spark.Spark.staticFileLocation;
import spark.Filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.dreilaenderhack.model.Document;
import org.dreilaenderhack.model.Person;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.aad.adal4j.AuthenticationResult;



public class WebService {

	public static void main(String[] args) {

		
		 staticFileLocation("/htdocs");

		 
		 after((Filter) (request, response) -> {
	            response.header("Access-Control-Allow-Origin", "*");
	            response.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
	        });
		
		 
		 options("/*",
			        (request, response) -> {

			            String accessControlRequestHeaders = request
			                    .headers("Access-Control-Request-Headers");
			            if (accessControlRequestHeaders != null) {
			                response.header("Access-Control-Allow-Headers",
			                        accessControlRequestHeaders);
			            }

			            String accessControlRequestMethod = request
			                    .headers("Access-Control-Request-Method");
			            if (accessControlRequestMethod != null) {
			                response.header("Access-Control-Allow-Methods",
			                        accessControlRequestMethod);
			            }

			            return "OK";
			        });

		 
		 get("/search",(request, response) -> {
			 
			
			 
			    String bearerToken = null;
				String auth = request.headers("Authorization");
				if(auth != null && auth.startsWith("Bearer")) {
					bearerToken = auth.substring("Bearer".length()).trim();
				}
				
				System.out.println("token "+bearerToken);
				
				if(bearerToken == null || bearerToken.isEmpty())
				{
					response.header("WWW-Authenticate", "Bearer");
			        halt(401, "You need a Bearer token");
				}
				
				PublicClient client = new PublicClient();
				
					AuthenticationResult result = client.getAccessTokenFromUserCredentials(bearerToken);
					System.out.println("Access Token - " + result.getAccessToken());
			        System.out.println("Refresh Token - " + result.getRefreshToken());
			        System.out.println("ID Token - " + result.getIdToken());
					String system = client.getUserInfoFromGraph(result.getAccessToken());
					System.out.println(system);
				
				
			
			 
			    response.type("application/json");
			  
			    List<Person> data = new ArrayList<Person>();
			    {
					Person p1 = new Person();
					
					p1.setName("Julia");
					p1.setEducation("Magister");
					p1.setCompany("SBB");
					p1.addSkillsItem("Networking");
					p1.addSkillsItem("Controlling");
					p1.addSkillsItem("Innovation");
					p1.addSkillsItem("Digitalisierung");
	
					p1.addProjectsItem("Pünktlichkeit");
					p1.addProjectsItem("Smart Rail");
					p1.setRank(0.1);
					p1.setImage("Julia.jpg");
					
					p1.addDocumentsItem(new Document("WLAN_Zug.pptx","05.07.2017"));
												
				    data.add(p1);
			    }
			    
			    {
					Person p1 = new Person();
					
					p1.setName("Romeo");
					p1.setEducation("Ingenieur");
					p1.setCompany("ÖBB");
					p1.addSkillsItem("Projektmananagement");
					p1.addSkillsItem("Networking");
				
	
					p1.addProjectsItem("Wlan im Zug");
					p1.addProjectsItem("Bahnhof");
					p1.setRank(99.8);
					p1.setImage("Romeo.jpg");
					p1.addDocumentsItem(new Document("WLAN_Bahnhof.pptx","12.12.2018"));
					
				    data.add(p1);
			    }
			    
			    {
					Person p1 = new Person();
					
					p1.setName("Hans");
					p1.setEducation("Bachelor of Science in Business");
					p1.setCompany("DB");
					p1.addSkillsItem("Sprachen");
					p1.addSkillsItem("Projekte");
				
	
					p1.addProjectsItem("Zug");
					p1.setRank(54.8);
					p1.setImage("Hans.jpg");
					p1.addDocumentsItem(new Document("WLAN_passwort.docx","23.01.2019"));
				    data.add(p1);
			    }
			    
			    {
					Person p1 = new Person();
					
					p1.setName("Vreni");
					p1.setEducation("PhD in Finance");
					p1.setCompany("SBB");
					p1.addSkillsItem("Zahlen");
					p1.addSkillsItem("Projekte");
				
	
					p1.addProjectsItem("Wlan");
					p1.setRank(14.8);
					p1.setImage("Vreni.jpg");
					p1.addDocumentsItem(new Document("WLAN.docx","26.12.2018"));

				    data.add(p1);
			    }
			
				
				
			    
			    return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data);
		 });
		 
		 
		 System.out.println("ready");
	}
		

}
