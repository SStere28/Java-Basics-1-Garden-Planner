package com.epam.training.garden.service;

import com.epam.training.garden.domain.GardenProperties;
import com.epam.training.garden.domain.PlantType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GardenService {

    private GardenProperties gardenProperties;

    public GardenService() {
    }

    private List<PlantType> getPlantTypes() {
        List<PlantType> plantTypes = new ArrayList<>();
        plantTypes.add(new PlantType("Corn", 0.4, 10));
        plantTypes.add(new PlantType("Pumpkin", 2, 5));
        plantTypes.add(new PlantType("Grape", 3, 5));
        plantTypes.add(new PlantType("Tomato", 0.3, 10));
        plantTypes.add(new PlantType("Cucumber", 0.4, 10));

        return plantTypes;
    }

    private Optional<PlantType> isPlantPresent(String name) {
        return getPlantTypes()
                .stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
    }

    public void setGardenProperties(GardenProperties gardenProperties) {
        this.gardenProperties = gardenProperties;
    }

    public Result evaluatePlan(Map<String, Integer> items) {

        double area = 0;
        double waterAmount = 0;
        Optional<PlantType> plantType;
        try {

            for (Map.Entry<String, Integer> entry : items.entrySet()) {

                plantType = isPlantPresent(entry.getKey());
                if (plantType.isEmpty()) {
                    area=0;
                    waterAmount=0;
                    throw new IllegalArgumentException("\n***Result***\n Unknown plant: " + entry.getKey());
                } else {
                    area += plantType.get().getArea() * entry.getValue();
                    waterAmount += plantType.get().getWaterAmount() * entry.getValue() * plantType.get().getArea();
                }
            }

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return new Result(
                area,
                waterAmount,
                area <= gardenProperties.getArea(),
                waterAmount <= gardenProperties.getWaterSupply());
    }
}
