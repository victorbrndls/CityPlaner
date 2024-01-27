package com.victorbrndls.cityplanner.city;

public interface Industry {

    public void tick();

    public Long getResourceCount(Resource resource);

    public void consumeResource(Resource resource, int amount);
}
