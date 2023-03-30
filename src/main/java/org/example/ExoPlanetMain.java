package org.example;

import org.apache.commons.io.IOUtils;
import org.example.bean.ExoPlanetData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ExoPlanetMain {
    public static void main(String[] args) throws Exception {
        final String dataSetURL = "https://gist.githubusercontent.com/joelbirchler/66cf8045fcbb6515557347c05d789b4a/raw/9a196385b44d4288431eef74896c0512bad3defe/exoplanets";
        InputStream inputStream = new URL(dataSetURL).openStream();

        String jsonText = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        JSONArray exoPlanetData = new JSONArray(jsonText);

        int count_orphan_planet = showNoStarData(exoPlanetData);
        String hottest_star_planet_id = showHottestStarData(exoPlanetData);
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
        showTimelineData(planetDataList);
    }

    public static int showNoStarData(JSONArray exoPlanetData) throws JSONException {
        int orphanPlanetCount = 0;
        if (exoPlanetData != null) {
            for (int i = 0; i < exoPlanetData.length(); i++) {
                JSONObject obj = exoPlanetData.getJSONObject(i);
                if (obj.has("TypeFlag") && obj.getInt("TypeFlag") == 3) {
                    orphanPlanetCount++;
                }
            }
        }
        return orphanPlanetCount;
    }

    public static String showHottestStarData(JSONArray exoPlanetData) throws JSONException {
        double max_temp = 0;
        double tempInt = 0;
        String hottestStar = "";

        for (int i = 0; i < exoPlanetData.length(); i++) {
            JSONObject planet = exoPlanetData.getJSONObject(i);
            if (planet.has("HostStarTempK")) {
                String temp = planet.getString("HostStarTempK");
                if (!Objects.equals(temp, "")) {
                    tempInt = Double.parseDouble(temp);
                }
                if (tempInt > max_temp) {
                    max_temp = tempInt;
                    hottestStar = planet.getString("PlanetIdentifier");
                }
            }
        }
        return hottestStar;
    }

    public static List<Object> showTimelineData(List<ExoPlanetData> exoPlanetData) {
        List<Object> timelineData = new ArrayList<>(); // return the timeline data captured as an ArrayList

        if (exoPlanetData != null && exoPlanetData.size() > 0) {
            Set<String> unique = new HashSet<>(exoPlanetData.stream()
                    .map(ExoPlanetData::getDiscoveryYear)
                    .filter(discoveryYear -> !discoveryYear.isEmpty())
                    .toList());

            List<String> sortedUnique = new ArrayList<>(unique);
            sortedUnique.sort(String::compareTo);

            System.out.println("***********Timeline of the number of planets discovered per year**************");
            for (String year : sortedUnique) {
                int countOfSmallPlanets = (int) exoPlanetData.stream()
                        .filter(object -> object.getDiscoveryYear().equals(year) && !object.getRadiusJpt().isEmpty() && Double.parseDouble(object.getRadiusJpt()) < 1)
                        .count();
                int countOfMediumPlanets = (int) exoPlanetData.stream()
                        .filter(object -> object.getDiscoveryYear().equals(year) && !object.getRadiusJpt().isEmpty() && Double.parseDouble(object.getRadiusJpt()) >= 1 && Double.parseDouble(object.getRadiusJpt()) < 2)
                        .count();
                int countOfLargePlanets = (int) exoPlanetData.stream()
                        .filter(object -> object.getDiscoveryYear().equals(year) && !object.getRadiusJpt().isEmpty() && Double.parseDouble(object.getRadiusJpt()) >= 2)
                        .count();
                System.out.printf("In %s we discovered %d small planets, %d medium planets and %d large planets%n", year, countOfSmallPlanets, countOfMediumPlanets, countOfLargePlanets);
                timelineData.add(year);
                timelineData.add(countOfSmallPlanets);
                timelineData.add(countOfMediumPlanets);
                timelineData.add(countOfLargePlanets);
            }
            System.out.println("******************************************************************************");
        }
        return timelineData;
    }

}