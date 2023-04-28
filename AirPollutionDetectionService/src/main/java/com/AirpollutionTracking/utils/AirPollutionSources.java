package com.AirpollutionTracking.utils;


import healthadvice.Healthadvice;

import java.util.HashMap;
import java.util.Map;

public class AirPollutionSources {
    Map<String, String> sourcesMap = new HashMap<>();
    String pollutant1 = "SO2";
    String pollutant2 = "NO2";
    String pollutant3 = "PM10";
    String pollutant4 = "PM2.5";
    String pollutant5 = "O3";
    String pollutant6 = "CO";
    // Dummy health advice data
    private   Map<String, String> healthAdviceMap = new HashMap<>();


        // Add more entries for other pollutants


    // Method to get health advice based on pollutant
    public  String getHealthAdvice(String pollutant) {
        healthAdviceMap.put("SO2", "Limit outdoor activity. Wear a mask when going outside.");
        healthAdviceMap.put("NO2", "Limit outdoor activity. Stay indoors with windows closed.");
        healthAdviceMap.put("PM10", "Limit outdoor activity, especially for vulnerable groups. Use air purifiers indoors.");
        healthAdviceMap.put("PM2.5", "Avoid outdoor activity, especially for vulnerable groups. Use air purifiers indoors.");
        healthAdviceMap.put("O3", "Limit outdoor activity during peak hours. Avoid strenuous activities.");
        healthAdviceMap.put("CO", "Limit outdoor activity. Avoid congested areas with heavy traffic.");
        String source = healthAdviceMap.get(pollutant);
        if (source == null) {
            source = "Unknown";
        }
        return source;    }


    public  String getAirPollutionSource(String pollutant) {
        sourcesMap.put("SO2", "Burning of fossil fuels, smelting of ores, volcanic eruptions, industrial processes");
        sourcesMap.put("NO2", "Burning of fossil fuels, agricultural activities, industrial processes, forest fires, lightning, aircraft emissions");
        sourcesMap.put("PM10", "Dust and dirt from construction sites and unpaved roads, agricultural activities, combustion of fossil fuels, industrial processes, residential heating and cooking, wildfires");
        sourcesMap.put("PM2.5", "Combustion of fossil fuels, residential heating and cooking, forest fires, agricultural activities, industrial processes, dust and dirt from construction sites and unpaved roads");
        sourcesMap.put("O3", "Emissions from industrial processes, vehicle exhaust, chemical reactions between nitrogen oxides (NOx) and volatile organic compounds (VOCs) in the presence of sunlight, power plants and other stationary sources, wildfires, fugitive emissions from oil and gas operations");
        sourcesMap.put("CO", "Incomplete combustion of fossil fuels in vehicles, residential heating and cooking, forest fires, tobacco smoke, industrial processes, some natural processes");

        String source = sourcesMap.get(pollutant);
        if (source == null) {
            source = "Unknown";
        }
        return source;
    }
}