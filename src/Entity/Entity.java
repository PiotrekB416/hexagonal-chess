package Entity;

import java.util.ArrayList;
abstract public class Entity {
    protected int rank;
    protected String file;
    public ArrayList<Object> getPosition(){
        ArrayList<Object> list = new ArrayList();
        list.add(this.rank);
        list.add(this.file);
        return list;
    }
    protected Entity(int rank, String file){
        this.rank = rank;
        this.file = file;
    }
}
