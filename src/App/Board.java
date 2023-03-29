package App;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Board extends JPanel {
    public ArrayList<ArrayList<ArrayList<Object>>> board;
    private HashMap<Integer, String> dict = new HashMap<Integer, String>(){
        {
            put(1, "a"); put(2, "b"); put(3, "c"); put(4, "d"); put(5, "e"); put(6, "f");
            put(7, "g"); put(8, "h"); put(9, "i"); put(10, "k"); put(11, "l");
        }
    };
    private HashMap<String, Integer> revdict = new HashMap(){
        {
            put("a", 1); put("b", 2); put("c", 3); put("d", 4); put("e", 5); put("f", 6);
            put("g", 7); put("h", 8); put("i", 9); put("k", 10); put("l", 11);
        }
    };
    private HashMap<String, Integer> offset = new HashMap<String, Integer>(){
        {
            put("a", 0); put("b", 1); put("c", 2); put("d", 3); put("e", 4); put("f", 5);
            put("l", 0); put("k", 1); put("i", 2); put("h", 3); put("g", 4);
        }
    };
    public Board(){

        int size = 11;
        board = new ArrayList();
        for (int i = size; i > 0; i--){
            int start = 1;
            int len = size;
            if (i > ((size + 1) / 2)){
                start = i - ((size - 1) / 2);
                len = (size - i) * 2 + 1;
            }
            ArrayList<ArrayList<Object>> line = new ArrayList();
            for (int j = start; j < len + start; j++){
                ArrayList<Object> pos = new ArrayList();
                pos.add(i);
                pos.add(dict.get(j));
                line.add(pos);
            }
            board.add(line);
        }


    }

    @Override
    protected void paintComponent(Graphics g){
        setBackground(Color.BLACK);
        int height = -200;
        super.paintComponent(g);
        for (ArrayList<ArrayList<Object>> line: board){

            for (ArrayList<Object> pos: line){
                int offset = this.offset.get(pos.get(1)) * 50;
                int hoffset = ((int)(57.74 + 28.88)) * revdict.get(pos.get(1));
                Image hex = hex = new ImageIcon("src/Images/brown.png").getImage();
                switch ((height + offset) % 150){
                    case 0:{
                        hex = new ImageIcon("src/Images/light.png").getImage();
                    }; break;
                    case 50: {
                        hex = new ImageIcon("src/Images/dark.png").getImage();
                    }; break;
                }

                g.drawImage(hex, hoffset,height + offset, 115, 100,this);
                //g.drawImage(hex, 100, 100,   this);
            }
            height += 100;
        }
    }
}
