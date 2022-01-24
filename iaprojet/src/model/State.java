package model;

import algorithms.Algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class State implements Comparable<State> {
    private final Graph graph;
    private final City current;
    private List<City> remainingCities;
    private List<City> explored;
    private int distance;
    private State previousState;

    public State(Graph graph, City current, List<City> explored, int distance, State previousState) {
        this.graph = graph;
        this.current = current;
        this.remainingCities = remainingCities();
        this.explored = explored;
        this.distance = distance;
        this.previousState = previousState;
    }

    public List<City> remainingCities() {
        List<City> remainingCities = new ArrayList<>(graph.getCities());
        if (explored == null) {
            return remainingCities;
        }
        for (City city : explored) {
            remainingCities.remove(city);
        }
        return remainingCities;
    }

    @Override
    public int compareTo(State other) {
        return Integer.compare(this.distance, other.distance);
    }

    public City getCurrent() {
        return current;
    }

    public State getPreviousState() {
        return previousState;
    }

    public List<City> getExplored() {
        return explored;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return distance == state.distance && Objects.equals(graph, state.graph) && Objects.equals(current, state.current) && Objects.equals(remainingCities, state.remainingCities) && Objects.equals(previousState, state.previousState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph, current, remainingCities, distance, previousState);
    }

    public List<City> getRemainingCities() {
        return remainingCities;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "(" + current + ", " + distance + ")";
    }
}
