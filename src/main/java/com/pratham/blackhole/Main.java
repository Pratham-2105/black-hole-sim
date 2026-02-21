package com.pratham.blackhole;

import com.pratham.blackhole.physics.Particle;
import com.pratham.blackhole.simulation.Simulation;
import com.pratham.blackhole.math.Vector2D;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.*;

import javafx.scene.shape.Polyline;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    private Simulation simulation;

    private Map<Particle, Circle> particleCircles = new HashMap<>();
    private Map<Particle, Polyline> particleTrails = new HashMap<>();

    @Override
    public void start(Stage stage) {
        simulation = new Simulation();

        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);

        stage.setTitle("Black Hole Simulation");
        stage.setScene(scene);
        stage.show();

        double cx = simulation.getBlackHole().getPosition().getX();
        double cy = simulation.getBlackHole().getPosition().getY();

        // Orange Glow
        Circle orangeGlow = new Circle(cx, cy, 80);
        orangeGlow.setFill(new RadialGradient(
                0, 0,
                0.5, 0.5,
                0.5,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.rgb(255, 170, 90, 0.5)),
                new Stop(0.4, Color.rgb(255, 120, 40, 0.25)),
                new Stop(1, Color.TRANSPARENT)
        ));
        root.getChildren().add(orangeGlow);

        // Core
        Circle core = new Circle(cx, cy, 25);
        core.setFill(Color.BLACK);
        root.getChildren().add(core);

        AnimationTimer timer = new AnimationTimer() {

            private long lastTime = 0;

            @Override
            public void handle(long now) {

                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                // Convert nanoseconds to seconds
                double dt = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                // Update physics with delta time
                simulation.update(dt);


                for (Particle particle : simulation.getParticles()) {

                    // Create circle if missing
                    if (!particleCircles.containsKey(particle)) {
                        Circle circle = new Circle(3, Color.WHITE);
                        particleCircles.put(particle, circle);
                        root.getChildren().add(circle);

                        Polyline trail = new Polyline();
                        trail.setStroke(Color.CYAN);
                        trail.setOpacity(0.6);
                        particleTrails.put(particle, trail);
                        root.getChildren().add(trail);
                    }

                    Circle circle = particleCircles.get(particle);
                    circle.setCenterX(particle.getPosition().getX());
                    circle.setCenterY(particle.getPosition().getY());
                    circle.setOpacity(particle.getOpacity());

                    Polyline trail = particleTrails.get(particle);
                    trail.getPoints().clear();

                    for (Vector2D pos : particle.getTrail()) {
                        trail.getPoints().addAll(pos.getX(), pos.getY());
                    }

                    trail.setOpacity(0.6 * particle.getOpacity());
                }

                for (Particle p : new ArrayList<>(particleCircles.keySet())) {
                    if (!simulation.getParticles().contains(p)) {
                        root.getChildren().remove(particleCircles.get(p));
                        root.getChildren().remove(particleTrails.get(p));
                        particleCircles.remove(p);
                        particleTrails.remove(p);
                    }
                }
            }
        };

        timer.start();
    }

    public static void main(String[] args) {
        launch();
    }
}