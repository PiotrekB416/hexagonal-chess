import App.App;
import App.Board;

public class Main {
    public static void main(String[] args){
        double scale = 1;
        App app = new App(scale);
        Board board = new Board(scale);
        for (int i = 0; i < 11; i++){
            for (int j = 0; j < 11; j++){
                System.out.print(board.pieces[i * 11 + j] + "\t");
            }
            System.out.print("\n");
        }

    }
}