package com.helper;

import android.annotation.SuppressLint;
import android.content.Context;

import com.R;
import com.item.Animal;

import java.util.ArrayList;
import java.util.List;

public class AnimalHelper {

    @SuppressLint("StaticFieldLeak")
    private static AnimalHelper instance;

    private Context context;

    private final List<Animal> animals = new ArrayList<>();

    public static AnimalHelper getInstance(Context context) {
        if (instance == null) {
            instance = new AnimalHelper();
            instance.context = context;
        }
        return instance;
    }

    public List<Animal> getAnimals() {
        if (animals.size() == 0) {
            animals.add(new Animal(context.getString(R.string.bat), R.drawable.bat));
            animals.add(new Animal(context.getString(R.string.bear), R.drawable.bear));
            animals.add(new Animal(context.getString(R.string.camel), R.drawable.camel));
            animals.add(new Animal(context.getString(R.string.cat), R.drawable.cat));
            animals.add(new Animal(context.getString(R.string.chick), R.drawable.chick));
            animals.add(new Animal(context.getString(R.string.chicken), R.drawable.chicken));
            animals.add(new Animal(context.getString(R.string.cock), R.drawable.cock));
            animals.add(new Animal(context.getString(R.string.cow), R.drawable.cow));
            animals.add(new Animal(context.getString(R.string.crocodile), R.drawable.crocodile));
            animals.add(new Animal(context.getString(R.string.dog), R.drawable.dog));
            animals.add(new Animal(context.getString(R.string.donkey), R.drawable.donkey));
            animals.add(new Animal(context.getString(R.string.duck), R.drawable.duck));
            animals.add(new Animal(context.getString(R.string.eagle), R.drawable.eagle));
            animals.add(new Animal(context.getString(R.string.elephant), R.drawable.elephant));
            animals.add(new Animal(context.getString(R.string.fish), R.drawable.fish));
            animals.add(new Animal(context.getString(R.string.fox), R.drawable.fox));
            animals.add(new Animal(context.getString(R.string.frog), R.drawable.frog));
            animals.add(new Animal(context.getString(R.string.kangaroo), R.drawable.kangaroo));
            animals.add(new Animal(context.getString(R.string.lamb), R.drawable.lamb));
            animals.add(new Animal(context.getString(R.string.lemur), R.drawable.lemur));
            animals.add(new Animal(context.getString(R.string.leon), R.drawable.leon));
            animals.add(new Animal(context.getString(R.string.monkey), R.drawable.monkey));
            animals.add(new Animal(context.getString(R.string.mountain_goat), R.drawable.mountain_goat));
            animals.add(new Animal(context.getString(R.string.mouse), R.drawable.mouse));
            animals.add(new Animal(context.getString(R.string.octopus), R.drawable.octopus));
            animals.add(new Animal(context.getString(R.string.ostrich), R.drawable.ostrich));
            animals.add(new Animal(context.getString(R.string.owl), R.drawable.owl));
            animals.add(new Animal(context.getString(R.string.panda), R.drawable.panda));
            animals.add(new Animal(context.getString(R.string.parrot), R.drawable.parrot));
            animals.add(new Animal(context.getString(R.string.peacock), R.drawable.peacock));
            animals.add(new Animal(context.getString(R.string.penguin), R.drawable.penguin));
            animals.add(new Animal(context.getString(R.string.polar_bear), R.drawable.polar_bear));
            animals.add(new Animal(context.getString(R.string.rabbit), R.drawable.rabbit));
            animals.add(new Animal(context.getString(R.string.rhino), R.drawable.rhino));
            animals.add(new Animal(context.getString(R.string.scorpion), R.drawable.scorpion));
            animals.add(new Animal(context.getString(R.string.seagull), R.drawable.seagull));
            animals.add(new Animal(context.getString(R.string.seal), R.drawable.seal));
            animals.add(new Animal(context.getString(R.string.snail), R.drawable.snail));
            animals.add(new Animal(context.getString(R.string.snake), R.drawable.snake));
            animals.add(new Animal(context.getString(R.string.sparrow), R.drawable.sparrow));
            animals.add(new Animal(context.getString(R.string.squirrel), R.drawable.squirrel));
            animals.add(new Animal(context.getString(R.string.tiger), R.drawable.tiger));
            animals.add(new Animal(context.getString(R.string.turkey), R.drawable.turkey));
            animals.add(new Animal(context.getString(R.string.turtle), R.drawable.turtle));
            animals.add(new Animal(context.getString(R.string.water_turtle), R.drawable.water_turtle));
            animals.add(new Animal(context.getString(R.string.wolf), R.drawable.wolf));
            animals.add(new Animal(context.getString(R.string.zebra), R.drawable.zebra));
            animals.add(new Animal(context.getString(R.string.gazelle), R.drawable.gazelle));
            animals.add(new Animal(context.getString(R.string.giraffe), R.drawable.giraffe));
            animals.add(new Animal(context.getString(R.string.horse), R.drawable.horse));
        }
        return animals;
    }

}
