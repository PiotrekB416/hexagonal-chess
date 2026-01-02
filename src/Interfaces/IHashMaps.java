package Interfaces;

import java.util.HashMap;

public interface IHashMaps {
    HashMap<Integer, String> dict = new HashMap<>(){
        {
            put(1, "a"); put(2, "b"); put(3, "c"); put(4, "d"); put(5, "e"); put(6, "f");
            put(7, "g"); put(8, "h"); put(9, "i"); put(10, "k"); put(11, "l");
        }
    };
    HashMap<String, Integer> revdict = new HashMap<>(){
        {
            put("a", 1); put("b", 2); put("c", 3); put("d", 4); put("e", 5); put("f", 6);
            put("g", 7); put("h", 8); put("i", 9); put("k", 10); put("l", 11);
        }
    };
    HashMap<String, Integer> offset = new HashMap<>(){
        {
            put("a", 0); put("b", 1); put("c", 2); put("d", 3); put("e", 4); put("f", 5);
            put("l", 0); put("k", 1); put("i", 2); put("h", 3); put("g", 4);
        }
    };
}
