package generator;

import model.City;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.floor;
import static java.lang.Math.log;

public class CityGenerator {
    public static List<City> generateCities(int numberOfCities, int maxDistance) {
        List<City> cities = new ArrayList<>();
        for (int i = 1; i <= numberOfCities; i++) {
            String name = generateAlphabeticStringFromNumber(i);
            cities.add(new City(name));
        }
        return cities;
    }

    private static String generateAlphabeticStringFromNumber(int n) {
        char[] buf = new char[(int) floor(log(25 * (n + 1)) / log(26))];
        for (int i = buf.length - 1; i >= 0; i--) {
            n--;
            buf[i] = (char) ('A' + n % 26);
            n /= 26;
        }
        return new String(buf);
    }
}
