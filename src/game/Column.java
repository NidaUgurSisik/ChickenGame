package game;

import java.util.Random;

public class Column {
    public boolean[] isColumnVisible = new boolean[5];
    public int catYLocation;

    public Column(int y) {
        int count = 0;
        for (int i = 0; i < 5; i++) {
            isColumnVisible[i] = new Random().nextBoolean();
            if (isColumnVisible[i]) {
                count++;
            }
        }
        if (count >= 4) {
            isColumnVisible[new Random().nextInt(3)] = false;
            isColumnVisible[new Random().nextInt(3)] = false;
        }
        catYLocation = y;
    }
}
