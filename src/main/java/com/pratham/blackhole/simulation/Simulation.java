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

        // Create multiple particles
        for(int i = 0; i < 200; i++) {
            Particle p = new Particle(
                    new Vector2D(400 + i, 300),
                    new Vector2D(0, 1.5),
                    10
            );

            particles.add(p);
        }

        blackHole = new BlackHole(
                new Vector2D(400, 300),
                5000
        );
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
