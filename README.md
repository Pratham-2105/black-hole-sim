# black-hole-sim

Black Hole Simulation (2D Newtonian Accretion Disk)

A real-time 2D gravitational simulation engine written in Java using JavaFX.

This project simulates particles orbiting a central massive object using Newtonian gravity, delta-time integration, spatial partitioning, and interaction forces to form a dynamic accretion disk.

It is designed with clean architectural layering and professional Git workflow practices.

## Project Overview

This simulation models:

- A central massive body (black hole)
- Hundreds of orbiting particles
- Newtonian gravitational attraction
- Tangential orbital velocity initialization
- Inter-particle soft repulsion
- Turbulence for disk texture
- Event horizon capture and fade-out
- Continuous particle spawning
- Real-time rendering using JavaFX

The system is CPU-driven and operates fully in 2D.

## Architecture

The project follows a layered structure separating rendering, simulation logic, physics, and math utilities.

```
Main (JavaFX + Render Loop)
        ↓
Simulation (World Manager + Physics Loop)
        ↓
Physics Layer
    ├ Particle
    ├ BlackHole
        ↓
Math Layer
    └ Vector2D
```

### Layer Responsibilities

| Layer | Responsibility |
|-------|----------------|
| Main | Rendering, AnimationTimer loop |
| Simulation | Physics orchestration, spawning, spatial grid |
| Particle | Motion state, trail, damping, capture logic |
| BlackHole | Gravitational force calculations |
| Vector2D | Vector math abstraction |

## Core Physics Model

### 1. Gravitational Force

Newtonian gravity:

```
F = G * (m1 * m2) / r²
```

Particles experience gravitational pull from the black hole.

### 2. Orbital Velocity Initialization

Particles are spawned with tangential velocity:

```
v = sqrt(GM / r)
```

This ensures near-circular orbits at initialization.

### 3. Delta-Time Integration

Motion is frame-rate independent:

```
dt = (now - lastTime) / 1e9
```

Physics is scaled using:

```
scaledDt = dt * timeScale
```

### 4. Velocity Damping

To stabilize the disk:

```
velocity *= dampingFactor
```

Prevents runaway energy growth.

### 5. Event Horizon

If:

```
distance < eventHorizonRadius
```

Particle is marked captured and fades out smoothly before removal.

### 6. Particle Interaction (Spatial Grid Optimized)

To simulate disk thickness and density behavior:

- Particles repel softly at short distances
- Uses a spatial hashing grid
- Checks only 9 neighboring cells
- Reduces O(n²) complexity

Interaction includes:

- Soft repulsion (linear falloff)
- Micro turbulence noise

## Rendering System (2D JavaFX)

Rendering is handled using:

- Circle for particles
- Polyline for particle trails
- RadialGradient for accretion glow
- AnimationTimer for real-time updates

Important design decisions:

- Trail nodes are reused
- No per-frame node recreation
- Visual layer separated from physics

## File Structure

```
black-hole-sim/
│
├ build.gradle
├ settings.gradle
├ gradlew
├ gradlew.bat
├ .gitignore
│
├ src/
│   └ main/
│       └ java/
│           └ com/
│               └ pratham/
│                   └ blackhole/
│                       │
│                       ├ Main.java
│                       │
│                       ├ math/
│                       │   └ Vector2D.java
│                       │
│                       ├ physics/
│                       │   ├ Particle.java
│                       │   └ BlackHole.java
│                       │
│                       └ simulation/
│                           └ Simulation.java
```

## Implemented Features

- Circular orbital initialization
- Delta-time physics
- Spatial partitioning grid
- Soft particle repulsion
- Micro turbulence noise
- Continuous particle spawning
- Event horizon fade-out
- Accretion disk glow
- Trail rendering
- Professional Git branching structure

## Git Workflow

**Branching strategy:**

- `main` → stable releases
- `develop` → integration branch
- `feature/*` → isolated features

**Examples:**

- feature/javafx-renderer
- feature/orbital-velocity
- feature/delta-time-integration
- feature/particle-trails
- feature/continuous-spawn
- feature/spatial-grid
- feature/particle-interaction

**Workflow:**

```
feature → develop → main
```

Only stable milestones are merged into main.

## Performance Considerations

- Spatial grid reduces neighbor checks
- Force computation separated from state update
- Iterator-based safe removal
- Limited trail length
- No object recreation inside tight loops

## How to Run

### Prerequisites

- Java 17+
- Gradle (or use included wrapper)

### Run with Gradle

```bash
./gradlew run
```

On Windows:

```bash
gradlew.bat run
```

## Current State

This project represents a:

- 2D Newtonian particle accretion disk simulator.

It is not:

- Relativistic
- 3D
- GPU accelerated
- Ray traced

All computation runs on CPU using Java and JavaFX.

## Future Evolution (Planned)

- 3D rendering layer
- Vector3D migration
- Camera controls
- LWJGL/OpenGL renderer
- Spacetime surface visualization
- Shader-based disk rendering

## Learning Objectives

This project demonstrates:

- Numerical integration
- Spatial partitioning
- Real-time rendering
- Engine layering
- Physics-to-visual separation
- Clean Git workflow
- Systems-oriented project design