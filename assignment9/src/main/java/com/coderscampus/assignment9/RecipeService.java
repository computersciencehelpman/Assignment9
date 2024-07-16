package com.coderscampus.assignment9;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.annotation.PostConstruct;

@Service
public class RecipeService {
    private List<Recipe> recipes = new ArrayList<>();

    @PostConstruct
    public void loadRecipes() {
        try (CSVReader reader = new CSVReader(new FileReader("recipes.txt"))) {
            List<String[]> lines = reader.readAll();

            // Skip the header line
            lines.remove(0);

            for (String[] parts : lines) {
                try {
                    Integer cookingMinutes = Integer.parseInt(parts[0].trim());
                    boolean dairyFree = Boolean.parseBoolean(parts[1].trim());
                    boolean glutenFree = Boolean.parseBoolean(parts[2].trim());
                    String instructions = parts[3].trim();
                    double preparationMinutes = Double.parseDouble(parts[4].trim());
                    double pricePerServing = Double.parseDouble(parts[5].trim());
                    Integer readyInMinutes = Integer.parseInt(parts[6].trim());
                    Integer servings = Integer.parseInt(parts[7].trim());
                    double spoonacularScore = Double.parseDouble(parts[8].trim());
                    String title = parts[9].trim();
                    boolean vegan = Boolean.parseBoolean(parts[10].trim());
                    boolean vegetarian = Boolean.parseBoolean(parts[11].trim());

                    recipes.add(new Recipe(cookingMinutes, dairyFree, glutenFree, instructions, preparationMinutes,
                            pricePerServing, readyInMinutes, servings, spoonacularScore, title, vegan, vegetarian));
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing line: " + String.join(",", parts));
                    e.printStackTrace();
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        // Print out the recipes
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
    }

    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    public List<Recipe> getGlutenFreeRecipes() {
        return recipes.stream()
                .filter(Recipe::getGlutenFree)
                .collect(Collectors.toList());
    }

    public List<Recipe> getVeganRecipes() {
        return recipes.stream()
                .filter(Recipe::getVegan)
                .collect(Collectors.toList());
    }

    public List<Recipe> getVeganAndGlutenFreeRecipes() {
        return recipes.stream()
                .filter(recipe -> recipe.getVegan() && recipe.getGlutenFree())
                .collect(Collectors.toList());
    }

    public List<Recipe> getVegetarianRecipes() {
        return recipes.stream()
                .filter(Recipe::getVegetarian)
                .collect(Collectors.toList());
    }
}

