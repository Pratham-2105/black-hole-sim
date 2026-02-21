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

    private boolean captured = false;
    private double opacity = 1.0;

    public Particle(Vector2D position, Vector2D velocity, double mass) {
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.acceleration = new Vector2D(0, 0);
        this.trail = new ArrayList<>();
    }

    // Apply force using Newton's Second Law: F = ma -> a = F / m
    public void applyForce(Vector2D force) {
        Vector2D forcePerMass = force.multiply(1.0 / mass);
        acceleration = acceleration.add(forcePerMass);
    }

    // Update particle motion each timestep
    public void update(double dt) {

        // Scale simulation time (tuning constant)
        double timeScale = 60;  // try 60 first

        double scaledDt = dt * timeScale;

        velocity = velocity.add(acceleration.multiply(scaledDt));

        double dampingFactor = Math.pow(0.98, scaledDt);
        velocity = velocity.multiply(dampingFactor);

        position = position.add(velocity.multiply(scaledDt));

        trail.add(position);

        if (trail.size() > MAX_TRAIL_LENGTH) {
            trail.remove(0);
        }

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

    public boolean isCaptured() {
        return captured;
    }

    public void setCaptured(boolean captured) {
        this.captured = captured;
    }

    public double getOpacity() {
        return opacity;
    }

    public void reduceOpacity(double amount) {
        opacity -= amount;
        if (opacity < 0) {
            opacity = 0;
        }
    }
}