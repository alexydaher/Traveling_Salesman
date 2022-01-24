package generator;

import model.City;
import model.Graph;

import java.util.ArrayList;
import java.util.List;

public class PathGenerator {

    public static Graph generatePath(int numberOfCities, int maxDistance) {
        List<City> cities = CityGenerator.generateCities(numberOfCities, maxDistance);
        return new Graph((ArrayList<City>) cities, maxDistance);
    }

    public static Graph generateExample() {
        Graph graph = generatePath(5, 21);
        graph.setDistance(0, 1, 21);
        graph.setDistance(0, 2, 9);
        graph.setDistance(0, 3, 21);
        graph.setDistance(0, 4, 1);
        graph.setDistance(1, 2, 4);
        graph.setDistance(1, 3, 8);
        graph.setDistance(1, 4, 20);
        graph.setDistance(2, 3, 6);
        graph.setDistance(2, 4, 7);
        graph.setDistance(3, 4, 11);
        return graph;
    }
}
