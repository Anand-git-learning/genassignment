package org.example;

import org.example.bean.ExoPlanetData;
import org.example.ExoPlanetMain;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExoplanetTest {

    @Test
    public void testDisplayNoStarData() throws JSONException {
        // Create a sample JSON array with three planets, one of which is an orphan planet
        JSONArray exoPlanetData = new JSONArray();
        JSONObject planet1 = new JSONObject();
        planet1.put("Name", "Kepler-186f");
        planet1.put("TypeFlag", 0);
        exoPlanetData.put(planet1);
        JSONObject planet2 = new JSONObject();
        planet2.put("Name", "Kepler-438b");
        planet2.put("TypeFlag", 1);
        exoPlanetData.put(planet2);
        JSONObject planet3 = new JSONObject();
        planet3.put("Name", "PSR B1257+12 c");
        planet3.put("TypeFlag", 3);
        exoPlanetData.put(planet3);

        // Call the method to test
        int count = ExoPlanetMain.showNoStarData(exoPlanetData);

        // Assert that the count of orphan planets is correct
        Assert.assertEquals(1, count);
    }

        @Test
        public void testDisplayHottestStarData() throws JSONException {
            // Create a sample JSON array with three planets, each with different star temperatures
            JSONArray exoPlanetData = new JSONArray();
            JSONObject planet1 = new JSONObject();
            planet1.put("PlanetIdentifier", "Kepler-186f");
            planet1.put("HostStarTempK", "5000");
            exoPlanetData.put(planet1);
            JSONObject planet2 = new JSONObject();
            planet2.put("PlanetIdentifier", "Kepler-438b");
            planet2.put("HostStarTempK", "7000");
            exoPlanetData.put(planet2);
            JSONObject planet3 = new JSONObject();
            planet3.put("PlanetIdentifier", "PSR B1257+12 c");
            planet3.put("HostStarTempK", "6000");
            exoPlanetData.put(planet3);

            // Call the method to test
            String result = ExoPlanetMain.showHottestStarData(exoPlanetData);

            // Assert that the hottest star name returned is "Kepler-438b"
            Assert.assertEquals("Kepler-438b", result);
        }

    @Test
    public void testDisplayTimelineData() {
        List<ExoPlanetData> exoPlanetData = new ArrayList<>();
        exoPlanetData.add(new ExoPlanetData("1995", "0.02"));
        exoPlanetData.add(new ExoPlanetData("1995", "1.05"));
        exoPlanetData.add(new ExoPlanetData("1995", "2.09"));
        exoPlanetData.add(new ExoPlanetData("1996", ""));
        exoPlanetData.add(new ExoPlanetData("1997", ""));
        exoPlanetData.add(new ExoPlanetData("1997", "0.02"));
        exoPlanetData.add(new ExoPlanetData("1997", ""));
        exoPlanetData.add(new ExoPlanetData("1997", ""));

        List<Object> expectedTimelineData = new ArrayList<>();
        expectedTimelineData.add("1995");
        expectedTimelineData.add(1);
        expectedTimelineData.add(1);
        expectedTimelineData.add(1);
        expectedTimelineData.add("1996");
        expectedTimelineData.add(0);
        expectedTimelineData.add(0);
        expectedTimelineData.add(0);
        expectedTimelineData.add("1997");
        expectedTimelineData.add(1);
        expectedTimelineData.add(0);
        expectedTimelineData.add(0);

        List<Object> actualTimelineData = ExoPlanetMain.showTimelineData(exoPlanetData);

        Assert.assertEquals(expectedTimelineData, actualTimelineData);
    }
    }



