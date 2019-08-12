import org.junit.Test;

public class Valleyball {
    @Test
    static int minPoints(int x, int y, int z) {
        if (Math.abs(x - y) == 1 && x > z - 1 && y > z - 1) return 1;
        else if (Math.abs(x - y) == 0 && x >= z - 1) return 2;
        else return z - x < z - y ? z - x : z - y;
    }
}
