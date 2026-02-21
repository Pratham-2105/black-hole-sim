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
            @Override
            public void handle(long now) {

                // Update physics
                simulation.update();

                // Clear previous frame
                root.getChildren().clear();

                // Draw black hole
                Circle blackHole = new Circle(
                        simulation.getBlackHole().getPosition().getX(),
                        simulation.getBlackHole().getPosition().getY(),
                        25
                );
                blackHole.setFill(Color.GRAY);
                root.getChildren().add(blackHole);

                // Draw particles + trails
                for (Particle particle : simulation.getParticles()) {

                    // Draw trail lines
                    for (int i = 1; i < particle.getTrail().size(); i++) {
                        Vector2D p1 = particle.getTrail().get(i - 1);
                        Vector2D p2 = particle.getTrail().get(i);

                        Line line = new Line(
                                p1.getX(), p1.getY(),
                                p2.getX(), p2.getY()
                        );

                        // Fade older segments
                        double opacity = i / (double) particle.getTrail().size();
                        line.setStroke(Color.CYAN);
                        line.setOpacity(opacity * 0.6);

                        root.getChildren().add(line);
                    }

                    // Draw particle
                    Circle circle = new Circle(
                            particle.getPosition().getX(),
                            particle.getPosition().getY(),
                            3
                    );
                    circle.setFill(Color.WHITE);
                    circle.setOpacity(particle.getOpacity());
                    circle.setOpacity(particle.getOpacity());
                    root.getChildren().add(circle);
                }
            }
        };

        timer.start();
    }

    public static void main(String[] args) {
        launch();
    }
}