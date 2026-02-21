package com.pratham.blackhole.physics;

import com.pratham.blackhole.math.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Particle {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private double mass;

    private List<Vector2D> trail;
    private static final int MAX_TRAIL_LENGTH = 25;

    public Particle(Vector2D position, Vector2D velocity, double mass) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.acceleration = new Vector2D(0, 0);

        this.trail = new ArrayList<>();
    }

    // Apply force using Newton's Second Law: F = ma -> a = F / m
    public void applyForce(Vector2D force){
        Vector2D forcePerMass = force.multiply(1.0 / mass);
        acceleration = acceleration.add(forcePerMass);
    }

    // Update particle motion each timestep
    public void update() {
        velocity = velocity.add(acceleration);
        position = position.add(velocity);

        // Add current position to trail
        trail.add(position);

        // Limit trail size
        if (trail.size() > MAX_TRAIL_LENGTH) {
            trail.remove(0);
        }

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

    public List<Vector2D> getTrail() {
        return trail;
    }
}