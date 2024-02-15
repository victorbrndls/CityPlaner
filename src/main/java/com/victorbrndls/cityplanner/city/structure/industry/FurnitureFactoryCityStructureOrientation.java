package com.victorbrndls.cityplanner.city.structure.industry;

import com.mojang.blaze3d.vertex.PoseStack;
import com.victorbrndls.cityplanner.city.structure.CityStructureOrientation;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.phys.Vec3;

public class FurnitureFactoryCityStructureOrientation implements CityStructureOrientation {

    private final Direction direction;

    public FurnitureFactoryCityStructureOrientation(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void updateSettings(StructurePlaceSettings settings) {
        switch (direction) {
            case NORTH -> settings.setRotation(Rotation.CLOCKWISE_180);
            case SOUTH -> settings.setRotation(Rotation.NONE);
            case WEST -> settings.setRotation(Rotation.CLOCKWISE_90);
            case EAST -> settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
            default -> {
            }
        }
    }

    @Override
    public Vec3 getCenter() {
        return new Vec3(2, 0, 3);
    }

    @Override
    public Vec3 getOffset() {
        switch (direction) {
            case NORTH -> {
                return new Vec3(2, 0, 0);
            }
            case SOUTH -> {
                return new Vec3(-2, 0, 0);
            }
            case WEST -> {
                return new Vec3(0, 0, -2);
            }
            case EAST -> {
                return new Vec3(0, 0, 2);
            }
            default -> {
                return Vec3.ZERO;
            }
        }
    }

    @Override
    public void translate(PoseStack ms) {
        switch (direction) {
            case NORTH -> {
                ms.translate(0.5, 0, -2.5);
            }
            case SOUTH -> {
                ms.translate(-0.5, 0, 2.5);
            }
            case WEST -> {
                ms.translate(-2.5, 0, -0.5);
            }
            case EAST -> {
                ms.translate(2.5, 0, 0.5);
            }
            default -> {
            }
        }
    }

    @Override
    public double rotationDegrees() {
        switch (direction) {
            case NORTH -> {
                return 180;
            }
            case SOUTH -> {
                return 0;
            }
            case WEST -> {
                return -90;
            }
            case EAST -> {
                return 90;
            }
            default -> {
                return 0;
            }
        }
    }
}
