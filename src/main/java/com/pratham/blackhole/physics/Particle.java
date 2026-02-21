package com.pratham.blackhole.physics;

import com.pratham.blackhole.math.Vector2D;

public class Particle {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private double mass;

    public Particle(Vector2D position, Vector2D velocity, double mass) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.acceleration = new Vector2D(0, 0);
    }

    // Apply force using Newton's Second Law: F = ma -> a = F / m
    public void applyForce(Vector2D force){
        Vector2D forcePerMass = force.multiply(1 / mass);
        acceleration = acceleration.add(forcePerMass);
    }

    // Update particle motion each timestep
    public void update() {
        velocity = velocity.add(acceleration);
        position = position.add(velocity);

        // Reset acceleration after each update
        acceleration = new Vector2D(0, 0);
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public double getMass() {
        return mass;
    }
}
