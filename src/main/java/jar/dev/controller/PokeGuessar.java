package jar.dev.controller;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import jar.dev.dto.Other;
import jar.dev.dto.PokeAPIResponse;


@Controller
public class PokeGuessar {
	
	@Autowired WebClient webClient;


	
	@GetMapping("/v1")
	public String generateBlurImage(Model model) {
		int pokeid = getRandomNumber();
		PokeAPIResponse pokeAPIResponse=webClient.get().uri("/"+pokeid+"/").retrieve().bodyToMono(PokeAPIResponse.class).block();
		
		RestTemplate restTemplate= new RestTemplate();		
		
		System.out.println(pokeAPIResponse.getSprites().other.dream_world.front_default);
		Other other=pokeAPIResponse.getSprites().other;
//		String imgURL=(other.showdown.front_default == null )? other.officialArtwork.front_default: other.showdown.front_default;
		String imgURL= other.officialArtwork.front_default;	
		byte[] sprite= restTemplate.getForEntity(imgURL, byte[].class).getBody();
		
		 String base64Image = Base64.getEncoder().encodeToString(sprite);
		 
		   model.addAttribute("imageData", base64Image);
		return "index";
	}
	
    public static int getRandomNumber() {
        return ThreadLocalRandom.current().nextInt(1, 1026); // Upper bound is exclusive
    }
    
	@GetMapping
	public String generateBlurImageV2(Model model) {
		
	PokeAPIResponse apiResponse = getPokeAPIResponse();
	
	  
	try {
		URL svgUrl = new URL(apiResponse.getSprites().other.dream_world.front_default);
	
		String svgContent = new String(svgUrl.openStream().readAllBytes(), StandardCharsets.UTF_8);
		 // Modify the SVG content (e.g., change color)
	      String modifiedSvgContent = svgContent.replace("fill=\"#000000\"", "fill=\"#FF0000\"");
	      
	      String maskedIMG = changeSvgColorToBlack(modifiedSvgContent);
	      // Add the modified SVG to the model
	      model.addAttribute("svgContent", maskedIMG);
	      model.addAttribute("answerIMG",modifiedSvgContent);	
	      model.addAttribute("name",apiResponse.getName());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      
     

      return "whose-that-pokemon";

	}
	
	public PokeAPIResponse getPokeAPIResponse() {
		int pokeid = getRandomNumber();
		PokeAPIResponse pokeAPIResponse=webClient.get().uri("/"+pokeid+"/").retrieve().bodyToMono(PokeAPIResponse.class).block();
		
		RestTemplate restTemplate= new RestTemplate();		
		
		System.out.println(pokeAPIResponse.getSprites().other.dream_world.front_default);
		Other other=pokeAPIResponse.getSprites().other;
		String imgURL= other.dream_world.front_default;	
		if(imgURL==null) {
			 pokeAPIResponse=getPokeAPIResponse();
		}
		return pokeAPIResponse;
	}

	
	public static String changeSvgColorToBlack(String svgContent) {
        // Regular expression to match fill attributes in SVG
        Pattern pattern = Pattern.compile("fill=\"(#[0-9a-fA-F]{6}|[a-zA-Z]+)\"");
        Matcher matcher = pattern.matcher(svgContent);

        // Replace the fill color with black
        return matcher.replaceAll("fill=\"#000000\"");
    }

}
