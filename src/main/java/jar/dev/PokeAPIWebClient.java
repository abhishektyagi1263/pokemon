package jar.dev;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


@Configuration
public class PokeAPIWebClient {

	
	@Bean
	public WebClient webClient(WebClient.Builder builder) {
		
		return builder.baseUrl("https://pokeapi.co/api/v2/pokemon")
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024))
				.defaultHeader("Accept", "application/json").build();
	}
	
	
	
}
