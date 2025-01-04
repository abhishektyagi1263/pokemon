package jar.dev.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Other{
    public DreamWorld dream_world;
    @JsonProperty("official-artwork")
    public Home officialArtwork;
    public Showdown showdown;
}
