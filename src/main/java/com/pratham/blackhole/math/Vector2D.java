package com.pratham.blackhole.math;

import java.util.Vector;

public class Vector2D {
    private double x;
    private double y;

    // Constructor
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Add Two Vectors
    public Vector2D add(Vector2D other) {
        return new Vector2D(
                this.x + other.x,
                this.y + other.y
        );
    }

    // Subtract Two Vectors
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(
                this.x - other.x,
                this.y - other.y
        );
    }

    // Multiply vector by scalar
    public Vector2D multiply(double scalar) {
        return new Vector2D(
                this.x * scalar,
                this.y * scalar
        );
    }

    // Get magnitude (length)
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    // Normalize (unit vector)
    public Vector2D normalize() {

        double mag = magnitude();

        if (mag == 0) {
            return new Vector2D(0, 0);
        }

        return new Vector2D(
                x / mag,
                y / mag
        );
    }

    // Getters

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
