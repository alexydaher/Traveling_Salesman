import algorithms.Algorithms;
import generator.PathGenerator;
import model.Graph;
import model.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Random generator (0) or Example from project (1): ");
        int choice = sc.nextInt();

        Graph graph;
        if (choice == 0) {
            graph = PathGenerator.generatePath(5, 10);
        } else {
            graph = PathGenerator.generateExample();
        }

        System.out.println("Graph :");
        System.out.println(graph);
        long startAStar, endAStar, durationAStar, startHill, endHill, durationHill;

        System.out.println();
        System.out.println("Astar :");
        startAStar = System.nanoTime();
        List<Vector> vectorsAStar = Algorithms.aStar(graph, graph.getCities().get(0));
        endAStar = System.nanoTime();
        System.out.println("vectors  : " + vectorsAStar);
        System.out.println("path  : " + Algorithms.vectorsToString(vectorsAStar));
        durationAStar = (endAStar - startAStar) / 1000;
        System.out.println("Weight : " + Algorithms.weight(vectorsAStar));
        System.out.println("Duration : " + durationAStar + " microseconds");

        System.out.println();

        System.out.println("Hill Climb :");
        startHill = System.nanoTime();
        List<Vector> vectorsHill = Algorithms.hillClimbing(graph, new ArrayList<>(), graph.getCities().get(0), graph.getCities().get(0), graph.getCities());
        endHill = System.nanoTime();
        durationHill = (endHill - startHill) / 1000;
        System.out.println("vectors  : " + vectorsHill);
        System.out.println("path  : " + Algorithms.vectorsToString(vectorsHill));
        System.out.println("Weight : " + Algorithms.weight(vectorsHill));
        System.out.println("Duration : " + durationHill + " microseconds");
    }
}
