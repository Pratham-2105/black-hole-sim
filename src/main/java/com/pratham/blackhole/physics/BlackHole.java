package com.pratham.blackhole.physics;

import com.pratham.blackhole.math.Vector2D;

import java.util.Vector;

public class BlackHole {
    private Vector2D position;
    private double mass;

    // Gravitational constant (scaled for simulation)
    private static final double G = 0.1;

    public BlackHole(Vector2D position, double mass) {
        this.position = position;
        this.mass = mass;
    }

    public Vector2D calculateForce(Particle particle) {
        // Direction from particle to black hole
        Vector2D direction = position.subtract(particle.getPosition());

        double distance = direction.magnitude();

        // Prevent division by zero
        if(distance == 0) {
            return  new Vector2D(0, 0);
        }

        // Normalize direction
        Vector2D normalized = direction.normalize();

        // Calculate strength
        double strength = (G * mass * particle.getMass()) / (distance * distance);

        // Force vector
        return normalized.multiply(strength);
    }
}
