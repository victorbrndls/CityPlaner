package com.victorbrndls.cityplanner.city.milestone;

import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.network.milestone.Level1MilestoneMessage;

public class Level1Milestone implements Milestone {

    public static int POPULATION_REQUIRED = 10;

    private final City city;

    public Level1Milestone(City city) {
        this.city = city;
    }

    @Override
    public double progress() {
        return city.getResidentCount() / 10.0;
    }

    @Override
    public Object getMessage() {
        return Level1MilestoneMessage.create(city);
    }
}
