package org.example.bean;

// ExoPlanetData class for holding planet data
public class ExoPlanetData {
    final private String  discoveryYear;
    final private String radiusJpt;


    public ExoPlanetData(String discoveryYear, String radiusJpt) {
        this.discoveryYear = discoveryYear;
        this.radiusJpt = radiusJpt;
    }

    public String getDiscoveryYear() {
        return discoveryYear;
    }

    public String getRadiusJpt() {
        return radiusJpt;
    }
}
