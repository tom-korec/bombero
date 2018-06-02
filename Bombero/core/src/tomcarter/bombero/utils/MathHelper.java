package tomcarter.bombero.utils;

/**
 * Helper static class
 * Contains some mathematical functions
 */
public class MathHelper {

    /**
     * Returns if number is in range
     * @param number - number to check
     * @param down - down border inc.
     * @param up - upper border inc.
     * @return true if number is in range down-up
     */
    public static boolean inRange(int number, int down, int up){
        return number >= down && number <= up;
    }

    /**
     * Returns fractional part of float number
     * @param number - float number
     * @return - fractional part of number
     */
    public static float fractionalPart(float number){
        return number - (int) number;
    }
}
