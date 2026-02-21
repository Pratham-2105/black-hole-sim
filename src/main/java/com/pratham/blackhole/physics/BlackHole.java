package com.pratham.blackhole.physics;

import com.pratham.blackhole.math.Vector2D;

import java.util.Vector;

public class BlackHole {
    private Vector2D position;
    private double mass;

    private static final double G = 0.1;

    private double eventHorizonRadius = 40;

    public BlackHole(Vector2D position, double mass) {
        this.position = position;
        this.mass = mass;
    }

    public Vector2D calculateForce(Particle particle) {
        Vector2D direction = position.subtract(particle.getPosition());
        double distance = direction.magnitude();

        if(distance == 0) {
            return new Vector2D(0, 0);
        }

        Vector2D normalized = direction.normalize();

        double softening = 25; // small constant
        double strength = (G * mass * particle.getMass()) /
                (distance * distance + softening);

        return normalized.multiply(strength);
    }

    public Vector2D getPosition() {
        return position;
    }

    public double getMass() {
        return mass;
    }

    public static double getG() {
        return G;
    }

    public double getEventHorizonRadius() {
        return eventHorizonRadius;
    }
}
