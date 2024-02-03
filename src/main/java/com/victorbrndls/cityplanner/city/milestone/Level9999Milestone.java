package com.victorbrndls.cityplanner.city.milestone;

import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.network.milestone.Level9999MilestoneMessage;

public class Level9999Milestone implements Milestone {

    private final City city;

    public Level9999Milestone(City city) {
        this.city = city;
    }

    @Override
    public double progress() {
        return 0.0;
    }

    @Override
    public Object getMessage() {
        return Level9999MilestoneMessage.create(city);
    }
}
