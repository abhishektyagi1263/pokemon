package jar.dev.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PokeAPIResponse implements Serializable{

	Sprites sprites;
	String name;
	public Sprites getSprites() {
		return sprites;
	}
	public void setSprites(Sprites sprites) {
		this.sprites = sprites;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "PokeAPIResponse [sprites=" + sprites + ", name=" + name + "]";
	}
	
	
}
