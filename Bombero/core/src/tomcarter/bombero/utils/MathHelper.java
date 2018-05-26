package tomcarter.bombero.utils;

public class MathHelper {

    public static boolean inRange(int number, int down, int up){
        return number >= down && number <= up;
    }

    public static float fractionalPart(float number){
        return number - (int) number;
    }
}
