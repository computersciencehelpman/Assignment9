package com.coderscampus.assignment9;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import jakarta.annotation.PostConstruct;

@Service
public class RecipeService {
    private List<Recipe> recipes = new ArrayList<>();

    @PostConstruct
    public void loadRecipes() {
        try (Scanner scanner = new Scanner(new File("recipes.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                boolean dairyFree = Boolean.parseBoolean(parts[1].trim());
                boolean glutenFree = Boolean.parseBoolean(parts[2].trim());
                boolean vegan = Boolean.parseBoolean(parts[10].trim());
                boolean vegetarian = Boolean.parseBoolean(parts[11].trim());
                recipes.add(new Recipe(dairyFree, glutenFree, vegan, vegetarian));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    public List<Recipe> getGlutenFreeRecipes() {
        return recipes.stream()
                      .filter(recipe -> recipe.getGlutenFree())
                      .collect(Collectors.toList());
    }

    public List<Recipe> getVeganRecipes() {
        return recipes.stream()
                      .filter(recipe -> recipe.getVegan())
                      .collect(Collectors.toList());
    }

    public List<Recipe> getVeganAndGlutenFreeRecipes() {
        return recipes.stream()
                      .filter(recipe -> recipe.getVegan() && recipe.getGlutenFree())
                      .collect(Collectors.toList());
    }

    public List<Recipe> getVegetarianRecipes() {
        return recipes.stream()
                      .filter(recipe -> recipe.getVegetarian())
                      .collect(Collectors.toList());
    }
}
