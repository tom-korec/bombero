package tomcarter.bombero.utils;

/**
 * Helper class.
 * Represent point on the map - two integers: x, y
 */
public class Int2D {
    public int x;
    public int y;

    public Int2D() {
    }

    public Int2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Int2D(float x, float y){
        this.x = (int) x;
        this.y = (int) y;
    }

    public Int2D(Int2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Computes distance between 2 points
     * @param other - other point
     * @return - distance between this and other point
     */
    public float distance(Int2D other){
        final int x_d = other.x - x;
        final int y_d = other.y - y;
        return (float)Math.sqrt(x_d * x_d + y_d * y_d);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Int2D int2D = (Int2D) o;

        if (x != int2D.x) return false;
        return y == int2D.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
