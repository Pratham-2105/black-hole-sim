package com.pratham.blackhole.simulation;

import com.pratham.blackhole.math.Vector2D;
import com.pratham.blackhole.physics.BlackHole;
import com.pratham.blackhole.physics.Particle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Simulation {

    private List<Particle> particles;
    private BlackHole blackHole;

    private static final int TARGET_PARTICLE_COUNT = 300;

    private static final double INTERACTION_RADIUS = 15;
    private static final double REPULSION_STRENGTH = 5;
    private static final double TURBULENCE_STRENGTH = 0.2;

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

    private void spawnParticle() {

        double minRadius = 180;
        double maxRadius = 260;

        double angle = Math.random() * 2 * Math.PI;
        double radius = minRadius + Math.random() * (maxRadius - minRadius);

        double x = blackHole.getPosition().getX() + radius * Math.cos(angle);
        double y = blackHole.getPosition().getY() + radius * Math.sin(angle);

        Vector2D position = new Vector2D(x, y);

        Vector2D radial = position.subtract(blackHole.getPosition());
        double r = radial.magnitude();

        Vector2D tangential = new Vector2D(-radial.getY(), radial.getX()).normalize();

        double speed = Math.sqrt((BlackHole.getG() * blackHole.getMass()) / r);

        Vector2D velocity = tangential.multiply(speed);

        Particle particle = new Particle(position, velocity, 10);
        particles.add(particle);
    }

    public void update(double dt) {

        // --------- FIRST PASS: compute forces ---------
        List<Vector2D> forces = new ArrayList<>(particles.size());

        for (int i = 0; i < particles.size(); i++) {

            Particle p1 = particles.get(i);

            Vector2D totalForce = blackHole.calculateForce(p1);

            // --- Local repulsion ---
            for (int j = 0; j < particles.size(); j++) {
                if (i == j) continue;

                Particle p2 = particles.get(j);

                Vector2D diff = p1.getPosition().subtract(p2.getPosition());
                double distance = diff.magnitude();

                if (distance > 0 && distance < INTERACTION_RADIUS) {
                    Vector2D repulsion = diff.normalize()
                            .multiply(REPULSION_STRENGTH / distance);
                    totalForce = totalForce.add(repulsion);
                }
            }

            // --- Micro turbulence ---
            double noiseX = (Math.random() - 0.5) * TURBULENCE_STRENGTH;
            double noiseY = (Math.random() - 0.5) * TURBULENCE_STRENGTH;
            totalForce = totalForce.add(new Vector2D(noiseX, noiseY));

            forces.add(totalForce);
        }

        // --------- SECOND PASS: apply + update + removal ---------
        Iterator<Particle> iterator = particles.iterator();
        int index = 0;

        while (iterator.hasNext()) {

            Particle particle = iterator.next();

            particle.applyForce(forces.get(index));
            particle.update(dt);

            double distance = particle.getPosition()
                    .subtract(blackHole.getPosition())
                    .magnitude();

            if (distance < blackHole.getEventHorizonRadius()) {
                particle.setCaptured(true);
            }

            if (particle.isCaptured()) {
                particle.reduceOpacity(0.5 * dt * 60);

                if (particle.getOpacity() <= 0) {
                    iterator.remove();
                }
            }

            index++;
        }

        // --------- THIRD PASS: spawn ---------
        while (particles.size() < TARGET_PARTICLE_COUNT) {
            spawnParticle();
        }
    }
    public List<Particle> getParticles() {
        return particles;
    }

    public BlackHole getBlackHole() {
        return blackHole;
    }
}
