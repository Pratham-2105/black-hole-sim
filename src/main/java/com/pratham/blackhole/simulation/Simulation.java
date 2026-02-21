package com.pratham.blackhole.simulation;

import com.pratham.blackhole.math.Vector2D;
import com.pratham.blackhole.physics.BlackHole;
import com.pratham.blackhole.physics.Particle;


public class Simulation {

    public void run() {
        Particle particle = new Particle(
                new Vector2D(100, 0),
                new Vector2D(0, 2),
                10
        );

        BlackHole blackHole = new BlackHole(
                new Vector2D(0, 0),
                1000
        );

        for (int i = 0; i < 100; i++) {
            Vector2D force = blackHole.calculateForce(particle);

            particle.applyForce(force);

            particle.update();

            System.out.println(
                    "Position: "
                            + particle.getPosition().getX()
                            + ", "
                            + particle.getPosition().getY()
            );
        }
    }
}
