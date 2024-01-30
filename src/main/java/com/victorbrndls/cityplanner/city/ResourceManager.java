package com.victorbrndls.cityplanner.city;

import java.util.List;

public class ResourceManager {

    private final List<Industry> industries;

    public ResourceManager(List<Industry> industries) {
        this.industries = industries;
    }

    public boolean tryConsume(Resource resource, int amount) {
        for (Industry industry : industries) {
            long available = industry.getResourceCount(resource);
            // TODO: consume from multiple industries
            if (available >= amount) {
                industry.consumeResource(resource, amount);
                return true;
            }
        }

        return false;
    }

    public long getResourceCount(Resource resource) {
        // TODO: improve this
        return industries.stream()
                .mapToLong(industry -> industry.getResourceCount(resource))
                .sum();
    }

}
