package com.pratham.blackhole;

import com.pratham.blackhole.physics.Particle;
import com.pratham.blackhole.simulation.Simulation;
import com.pratham.blackhole.math.Vector2D;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;

public class Main extends Application {

    private Simulation simulation;

    @Override
    public void start(Stage stage) {
        simulation = new Simulation();

        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);

        stage.setTitle("Black Hole Simulation");
        stage.setScene(scene);
        stage.show();

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

                // Clear previous frame
                root.getChildren().clear();

                // Draw black hole

                //Outer Blue Glow
                Circle blueGlow = new Circle(
                        simulation.getBlackHole().getPosition().getX(),
                        simulation.getBlackHole().getPosition().getY(),
                        80
                );

                RadialGradient blueGradient = new RadialGradient(
                        0, 0,
                        0.5, 0.5,
                        0.5,
                        true,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(30, 144, 255, 0.6)),   // bright center
                        new Stop(1, Color.rgb(30, 144, 255, 0.0))    // transparent edge
                );

                blueGlow.setFill(blueGradient);
                root.getChildren().add(blueGlow);


                //Inner Orange Glow
                Circle orangeGlow = new Circle(
                        simulation.getBlackHole().getPosition().getX(),
                        simulation.getBlackHole().getPosition().getY(),
                        45
                );

                RadialGradient orangeGradient = new RadialGradient(
                        0, 0,
                        0.5, 0.5,
                        0.5,
                        true,
                        CycleMethod.NO_CYCLE,
                        new Stop(0, Color.rgb(255, 140, 0, 0.9)),  // hot center
                        new Stop(1, Color.rgb(255, 69, 0, 0.0))    // fade outward
                );

                orangeGlow.setFill(orangeGradient);
                root.getChildren().add(orangeGlow);


                //Core Black Hole
                Circle blackHoleCore = new Circle(
                        simulation.getBlackHole().getPosition().getX(),
                        simulation.getBlackHole().getPosition().getY(),
                        25
                );
                blackHoleCore.setFill(Color.BLACK);
                root.getChildren().add(blackHoleCore);

                // Draw particles + trails
                for (Particle particle : simulation.getParticles()) {

                    for (int i = 1; i < particle.getTrail().size(); i++) {
                        Vector2D p1 = particle.getTrail().get(i - 1);
                        Vector2D p2 = particle.getTrail().get(i);

                        Line line = new Line(
                                p1.getX(), p1.getY(),
                                p2.getX(), p2.getY()
                        );

                        double opacity = i / (double) particle.getTrail().size();
                        line.setStroke(Color.CYAN);
                        line.setOpacity(opacity * 0.6 * particle.getOpacity());

                        root.getChildren().add(line);
                    }

                    Circle circle = new Circle(
                            particle.getPosition().getX(),
                            particle.getPosition().getY(),
                            3
                    );
                    circle.setFill(Color.WHITE);
                    circle.setOpacity(particle.getOpacity());

                    root.getChildren().add(circle);

                    double pulse = 1 + 0.05 * Math.sin(now * 0.000000002);
                    orangeGlow.setRadius(45 * pulse);
                }
            }

        };

        timer.start();
    }

    public static void main(String[] args) {
        launch();
    }
}