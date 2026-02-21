package com.pratham.blackhole;

import com.pratham.blackhole.physics.Particle;
import com.pratham.blackhole.simulation.Simulation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Simulation simulation;
    private List<Circle> particleNodes;

    @Override
    public void start(Stage stage) {

        simulation = new Simulation();
        System.out.println("Particle count: " + simulation.getParticles().size());

        Group root = new Group();

        particleNodes = new ArrayList<>();

        for (Particle particle : simulation.getParticles()) {

            Circle circle = new Circle(4, Color.WHITE);

            particleNodes.add(circle);

            root.getChildren().add(circle);
        }

        Scene scene = new Scene(root, 800, 600, Color.BLACK);

        stage.setTitle("Black Hole Simulation");

        stage.setScene(scene);

        stage.show();

        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long now) {

                simulation.update();

                for (int i = 0; i < particleNodes.size(); i++) {

                    Particle particle = simulation.getParticles().get(i);

                    Circle circle = particleNodes.get(i);

                    circle.setCenterX(particle.getPosition().getX());

                    circle.setCenterY(particle.getPosition().getY());
                }
            }
        };

        timer.start();
    }

    public static void main(String[] args) {

        launch();
    }
}