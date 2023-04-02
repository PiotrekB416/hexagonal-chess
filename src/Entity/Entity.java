package Entity;

import App.IHashMaps;

import java.util.ArrayList;
import java.util.HashMap;

abstract public class Entity implements IHashMaps {

    public int getRank() {
        return rank;
    }
    public String getFile() {
        return file;
    }

    protected int rank;
    protected String file;

    protected Entity(int rank, String file){
        this.rank = rank;
        this.file = file;
    }
}
