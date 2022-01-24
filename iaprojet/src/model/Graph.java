package model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Graph {
    private final List<City> cities;
    private final int[][] distances;
    private static final DecimalFormat df = new DecimalFormat("000");

    public Graph(List<City> cities, int maxDistance) {
        this.cities = cities;
        this.distances = new int[cities.size()][cities.size()];
        Random random = new Random();
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                if (i == j) {
                    this.distances[i][j] = 0;
                } else {
                    if (this.distances[j][i] != (double) 0) {
                        this.distances[i][j] = this.distances[j][i];
                    } else {
                        this.distances[i][j] = random.nextInt(1, maxDistance);
                    }
                }
            }
        }
    }

    public List<City> getCities() {
        return new ArrayList<>(cities);
    }

    public int[][] getDistances() {
        return distances;
    }

    public int getIndexFromCity(City city) {
        for (int i = 0; i < this.cities.size(); i++) {
            if (this.cities.get(i).equals(city)) {
                return i;
            }
        }
        return -1;
    }

    public void setDistance(int start, int destination, int distance) {
        distances[start][destination] = distance;
        distances[destination][start] = distance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("   ");
        for (City city : this.getCities()) {
            sb.append(city.getName()).append("   ");
        }
        sb.append("\n");
        for (int x = 0; x < this.cities.size(); x++) {
            sb.append(this.cities.get(x).getName()).append(" ");
            for (int y = 0; y < this.cities.size(); y++) {
                double distance = this.getDistances()[x][y];
                sb.append(df.format(distance)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int distanceFromCityTo(City current, City neighbor) {
        return distances[getIndexFromCity(current)][getIndexFromCity(neighbor)];
    }
}
