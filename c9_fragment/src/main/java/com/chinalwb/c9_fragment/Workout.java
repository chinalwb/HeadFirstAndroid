package com.chinalwb.c9_fragment;

public class Workout {
    private String name;
    private String description;

    public static final Workout[] workouts = {
            new Workout("The Limb Loosener", "5 Handstand push-ups\n10 1-legged squats\\n15 " +
                    "Pull-ups"),
            new Workout("Core Agony", "Pull-ups\n100 Push-ups"),
            new Workout("The Wimp Special", "Pull-ups\n15 Push-ups")
    };


    private Workout(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }
}