package com.victorbrndls.cityplanner.city.milestone;

public interface Milestone {

    default boolean isCompleted() {
        return progress() >= 1.0;
    }

    // 0.0 - 1.0
    public double progress();

}
