package model;

import java.util.Objects;

public class Vector {
    private final City c1;
    private final City c2;
    private final int distance;
    public Vector(City c1, City c2, int distance) {
        this.c1 = c1;
        this.c2 = c2;
        this.distance = distance;
    }

    public City getC1() {
        return this.c1;
    }

    public City getC2() {
        return c2;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.distance, distance) == 0 && Objects.equals(c1, vector.c1) && Objects.equals(c2, vector.c2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(c1, c2, distance);
    }

    @Override
    public String toString() {
        return "(" + this.c1.getName() + ", " + this.c2.getName() + ", " + this.distance + ")";
    }
}