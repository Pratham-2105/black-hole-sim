package com.pratham.blackhole.simulation;

import com.pratham.blackhole.math.Vector2D;
import com.pratham.blackhole.physics.BlackHole;
import com.pratham.blackhole.physics.Particle;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private List<Particle> particles;
    private BlackHole blackHole;

    public Simulation() {
        particles = new ArrayList<>();

        blackHole = new BlackHole(
                new Vector2D(400, 300),
                5000
        );

        double G = 0.1; // MUST match BlackHole.G
        int particleCount = 200;

        double minRadius = 80;
        double maxRadius = 200;

        for (int i = 0; i < particleCount; i++) {

            // Random angle
            double angle = Math.random() * 2 * Math.PI;

            // Random radius within band
            double radius = minRadius + Math.random() * (maxRadius - minRadius);

            // Compute position
            double x = blackHole.getPosition().getX() + radius * Math.cos(angle);
            double y = blackHole.getPosition().getY() + radius * Math.sin(angle);

            Vector2D position = new Vector2D(x, y);

            // Radial direction from black hole
            Vector2D radial = position.subtract(blackHole.getPosition());

            double r = radial.magnitude();

            // Tangential direction (rotate 90 degrees)
            Vector2D tangential = new Vector2D(-radial.getY(), radial.getX()).normalize();

            // Circular orbit speed
            double speed = Math.sqrt((G * blackHole.getMass()) / r);

            Vector2D velocity = tangential.multiply(speed);

            Particle p = new Particle(position, velocity, 10);

            particles.add(p);
        }
    }

    public void update() {
        for(Particle particle : particles) {
            Vector2D force = blackHole.calculateForce(particle);

            particle.applyForce(force);

            particle.update();
        }
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public BlackHole getBlackHole() {
        return blackHole;
    }
}
