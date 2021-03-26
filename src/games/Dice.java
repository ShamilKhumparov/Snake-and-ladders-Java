package games;

public class Dice {
    private static final int MAX = 6;
    public static int roll() {
        return (int)((Math.random() * MAX +1));
    }
}
