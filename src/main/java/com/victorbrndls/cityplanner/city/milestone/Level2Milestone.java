package com.victorbrndls.cityplanner.city.milestone;

import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.network.milestone.Level2MilestoneMessage;

public class Level2Milestone implements Milestone {

    public static int POPULATION_REQUIRED = 20;
    public static int SATISFACTION_REQUIRED = 70;

    private final City city;

    public Level2Milestone(City city) {
        this.city = city;
    }

    @Override
    public double progress() {
        return city.getResidentCount() / 20.0;
    }

    @Override
    public Object getMessage() {
        return Level2MilestoneMessage.create(city);
    }
}
