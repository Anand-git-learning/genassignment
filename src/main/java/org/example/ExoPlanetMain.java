package org.example;

import org.apache.commons.io.IOUtils;
import org.example.bean.ExoPlanetData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExoPlanetMain {
    public static void main(String[] args) throws Exception {
        final String dataSetURL = "https://gist.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets";
        InputStream inputStream = new URL(dataSetURL).openStream();

        String jsonText = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        JSONArray exoPlanetData = new JSONArray(jsonText);

        int count_orphan_planet = displayNoStarData(exoPlanetData);
        String hottest_star_planet_id = displayHottestStarData(exoPlanetData);
        List<ExoPlanetData> planetDataList = new ArrayList<>();
        for (int i = 0; i < exoPlanetData.length(); i++) {
            JSONObject planetObject = exoPlanetData.getJSONObject(i);

            ExoPlanetData planetData = new ExoPlanetData(
                    planetObject.getString("DiscoveryYear"),
                    planetObject.getString("RadiusJpt")
            );
            planetDataList.add(planetData);
        }

        System.out.println("The number of orphan planets (no star): " + count_orphan_planet);
        System.out.println("The name (planet identifier) of the planet orbiting the hottest star: " + hottest_star_planet_id);
        displayTimelineData(planetDataList);
    }

    public static int displayNoStarData(JSONArray exoPlanetData) throws JSONException {
        int count_orphan_planet = 0;
        if (exoPlanetData != null) {
            for (int i = 0; i < exoPlanetData.length(); i++) {
                JSONObject obj = exoPlanetData.getJSONObject(i);
                if (obj.has("TypeFlag") && obj.getInt("TypeFlag") == 3) {
                    count_orphan_planet++;
                }
            }
            // System.out.println("The number of orphan planets (no star): " + count_orphan_planet);
        }
        return count_orphan_planet;
    }

    public static String displayHottestStarData(JSONArray exoPlanetData) throws JSONException {
        Double max_temp = (double) 0;
        Double tempInt = (double) 0;
        String hottest_star_name = "";

        for (int i = 0; i < exoPlanetData.length(); i++) {
            JSONObject planet = exoPlanetData.getJSONObject(i);
            if (planet.has("HostStarTempK")) {
                String temp = planet.getString("HostStarTempK");
                if (temp != "") {
                    tempInt = Double.parseDouble(temp);
                }
                if (tempInt > max_temp) {
                    max_temp = tempInt;
                    hottest_star_name = planet.getString("PlanetIdentifier");
                }
            }
        }
        if (hottest_star_name != "") {
            //  System.out.println("The hottest star is " + hottest_star_name + " with a temperature of " + max_temp + " K");
        } else {
            System.out.println("No star temperature data found");
        }
        return hottest_star_name;
    }

    public static List<Object> displayTimelineData(List<ExoPlanetData> exoPlanetData) {
        List<Object> timelineData = new ArrayList<>(); // return the timeline data captured as an ArrayList

        if (exoPlanetData != null && exoPlanetData.size() > 0) {
            // Java Set to create new Set with matching unique filtered objects
            Set<String> unique = new HashSet<>(exoPlanetData.stream()
                    .filter(val -> !val.getDiscoveryYear().isEmpty())
                    .map(ExoPlanetData::getDiscoveryYear)
                    .toList());

            // sort based on the year of discovery year for timeline display
            List<String> sortedUnique = new ArrayList<>(unique);
            sortedUnique.sort(String::compareTo);

            System.out.println("--------------------------TIMELINE Data--------------------------");
            for (String year : sortedUnique) {
                int smallPlanetCount = (int) exoPlanetData.stream()
                        .filter(object -> object.getDiscoveryYear().equals(year) && !object.getRadiusJpt().isEmpty() && Double.parseDouble(object.getRadiusJpt()) < 1)
                        .count();
                int mediumPlanetCount = (int) exoPlanetData.stream()
                        .filter(object -> object.getDiscoveryYear().equals(year) && !object.getRadiusJpt().isEmpty() && Double.parseDouble(object.getRadiusJpt()) >= 1 && Double.parseDouble(object.getRadiusJpt()) < 2)
                        .count();
                int largePlanetCount = (int) exoPlanetData.stream()
                        .filter(object -> object.getDiscoveryYear().equals(year) && !object.getRadiusJpt().isEmpty() && Double.parseDouble(object.getRadiusJpt()) >= 2)
                        .count();
                System.out.printf("In %s we discovered %d small planets, %d medium planets and %d large planets%n", year, smallPlanetCount, mediumPlanetCount, largePlanetCount);
                timelineData.add(year);
                timelineData.add(smallPlanetCount);
                timelineData.add(mediumPlanetCount);
                timelineData.add(largePlanetCount);
            }
            System.out.println("-----------------------------------------------------------------");
        }
        return timelineData;
    }

}