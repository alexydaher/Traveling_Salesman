package algorithms;


import model.City;
import model.State;
import model.Graph;
import model.Vector;

import java.util.*;

public class Algorithms {
    public static int heuristic(Graph graph, List<City> remainingCities) {
        int heuristic = 0;
        if (remainingCities.size() <= 1) {
            return heuristic;
        }
        List<Vector> prim = prim(graph, remainingCities);
        for (Vector vector : prim) {
            heuristic += vector.getDistance();
        }

        City start = remainingCities.get(0);
        City last = prim.get(prim.size() - 1).getC2();
        return heuristic + graph.distanceFromCityTo(start, last);
    }

    public static List<Vector> prim(Graph graph, List<City> remainingCities) {
        List<Vector> bestVectors = new ArrayList<>();
        List<Vector> allVectors = new ArrayList<>();
        City first = remainingCities.get(0);
        List<City> visitedCities = new ArrayList<>();
        visitedCities.add(first);
        if (remainingCities.size() <= 1) {
            return Collections.emptyList();
        }
        for (City city : remainingCities) {
            if (visitedCities.contains(city) && !(visitedCities.size() == remainingCities.size())) {
                for (City neighbor : remainingCities) {
                    if (!city.equals(neighbor)) {
                        Vector vector1 = new Vector(city, neighbor, graph.distanceFromCityTo(city, neighbor));
                        Vector vector2 = new Vector(neighbor, city, graph.distanceFromCityTo(city, neighbor));
                        if (!allVectors.contains(vector2) && !allVectors.contains(vector1)) {
                            allVectors.add(vector1);
                        }
                    }
                }
                int count = 0;
                Vector bestVector = allVectors.get(count);
                while (visitedCities.contains(bestVector.getC1()) && visitedCities.contains(bestVector.getC2())) {
                    count += 1;
                    bestVector = allVectors.get(count);
                }
                for (Vector vector : allVectors) {
                    if (!visitedCities.contains(vector.getC1()) || !visitedCities.contains(vector.getC2())) {
                        if (vector.getDistance() < bestVector.getDistance()) {
                            bestVector = vector;
                        }
                    }
                }
                if (!visitedCities.contains(bestVector.getC1())) {
                    visitedCities.add(bestVector.getC1());
                }
                if (!visitedCities.contains(bestVector.getC2())) {
                    visitedCities.add(bestVector.getC2());
                }
                bestVectors.add(bestVector);
                allVectors.remove(bestVector);
            }
        }
        while (visitedCities.size() < remainingCities.size()) {
            int count = 0;
            Vector bestVector = allVectors.get(count);
            while (visitedCities.contains(bestVector.getC1()) && visitedCities.contains(bestVector.getC2())) {
                count += 1;
                bestVector = allVectors.get(count);
            }
            City city = bestVectors.get(bestVectors.size() - 1).getC2();
            for (City neighbor : remainingCities) {
                if (!city.equals(neighbor)) {
                    Vector vector1 = new Vector(city, neighbor, graph.distanceFromCityTo(city, neighbor));
                    Vector vector2 = new Vector(neighbor, city, graph.distanceFromCityTo(city, neighbor));
                    if (!allVectors.contains(vector2) && !allVectors.contains(vector1)) {
                        allVectors.add(vector1);
                    }
                }
            }

            for (Vector vector : allVectors) {
                if (!visitedCities.contains(vector.getC1()) || !visitedCities.contains(vector.getC2())) {
                    if (vector.getDistance() < bestVector.getDistance()) {
                        bestVector = vector;
                    }
                }
            }
            bestVectors.add(bestVector);
            allVectors.remove(bestVector);
            if (!visitedCities.contains(bestVector.getC1())) {
                visitedCities.add(bestVector.getC1());
            }
            if (!visitedCities.contains(bestVector.getC2())) {
                visitedCities.add(bestVector.getC2());
            }

        }
        return bestVectors;
    }

    // Recherche informée
    public static List<Vector> aStar(Graph graph, City start) {
        PriorityQueue<State> frontier = new PriorityQueue<>();
        State state = new State(graph, start, new ArrayList<City>(), heuristic(graph, graph.getCities()), null);
        frontier.add(state);
        State state1 = aux(graph, frontier, new ArrayList<City>(), state);
        List<City> path = stateToPath(state1);
        Collections.reverse(path);
        path.add(start);
        return citiesToVectors(path, graph);
    }

    public static State aux(Graph graph, PriorityQueue<State> frontier, List<City> explored, State currentState) {
        boolean retu = true;
        for (City city : graph.getCities()) {
            if (!explored.contains(city)) {
                retu = false;
                break;
            }
        }
        if (retu) {
            return currentState;
        }

        State bestState = frontier.peek();
        frontier.remove(bestState);
        City current = bestState.getCurrent();
        explored = bestState.getExplored();
        explored.add(current);
        System.out.println("explored : " + explored);
        System.out.println("frontier : " + frontier);
        for (City neighbor : graph.getCities()) {
            if (!explored.contains(neighbor)) {
                List<City> remainingCities = new ArrayList<>(graph.getCities());
                for (City city : graph.getCities()) {
                    if (explored.contains(city)) {
                        remainingCities.remove(city);
                    }
                }
                frontier.add(new State(graph, neighbor, explored, graph.distanceFromCityTo(current, neighbor) + heuristic(graph, remainingCities), bestState));
            }
        }
        return aux(graph, frontier, explored, bestState);
    }

    private static List<Vector> citiesToVectors(List<City> path, Graph graph) {
        List<Vector> vectors = new ArrayList<>();
        for (int i = 0; i < path.size() - 1; i++) {
            vectors.add(new Vector(path.get(i), path.get(i + 1), graph.distanceFromCityTo(path.get(i), path.get(i + 1))));
        }
        return vectors;
    }

    public static String vectorsToString(List<Vector> vectors) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < vectors.size(); i++) {
            str.append(vectors.get(i).getC1().getName());
            str.append("->");
        }
        str.append(vectors.get(vectors.size() - 1).getC2().getName());
        return str.toString();
    }

    public static List<City> stateToPath(State state) {
        List<City> cities = new ArrayList<>();
        while (state != null) {
            cities.add(state.getCurrent());
            state = state.getPreviousState();
        }
        return cities;
    }

    // Recherche locale
    public static List<Vector> hillClimbing(Graph graph, List<Vector> vectors, City goal, City current, List<City> remainingCities) {
        if ((current.equals(goal) && vectors.size() != 0) || remainingCities.size() == 0) {
            return vectors;
        }
        System.out.println("current vectors : " + vectors);
        double minimumHeuristic = heuristic(graph, remainingCities);
        City minimumCity = current;
        for (City neighbor : graph.getCities()) {
            if (!neighbor.equals(current) && remainingCities.contains(neighbor)) {
                List<City> newRemainingCities = new ArrayList<>(remainingCities);
                newRemainingCities.remove(neighbor);
                double newHeuristic = heuristic(graph, newRemainingCities);
                if (newHeuristic < minimumHeuristic) {
                    minimumCity = neighbor;
                    minimumHeuristic = newHeuristic;
                }
            }
        }
        Vector vector = new Vector(current, minimumCity, graph.distanceFromCityTo(current, minimumCity));
        if (vector.getDistance() == 0) {
            vector = new Vector(current, goal, graph.distanceFromCityTo(current, goal));
        }
        vectors.add(vector);
        remainingCities.remove(current);
        return hillClimbing(graph, vectors, goal, minimumCity, remainingCities);
    }

    public static int weight(List<Vector> vectors) {
        int weight = 0;
        for (Vector vector : vectors) {
            weight += vector.getDistance();
        }
        return weight;
    }
}