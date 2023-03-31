package Entity.Tile;

import Entity.Entity;

import java.util.UUID;

public class Tile extends Entity {
    public String texture;
    public Tile(int rank, String file){
        super(rank, file);
    }
}
