/*
I certify, that this computer program submitted by me is all of my own work.
Signed: Dylan Theis 9/6/2024

Author: Dylan Theis
Date: Fall 2024
Class: CSC420
Project: Random Name Connections
Description: Generate new names, display those names sorting by first name, then sort by last name.
Finally, display shared first names and their last name connections.
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// Main class
public class theisRandomNameGenerator {
    public static void main(String[] args) {
        // Display my info
        System.out.println("Dylan Theis");
        System.out.println("theisd@csp.edu");

        // Load first names from file and place names into a list
        List<String> firstNames = loadNamesFromFile("firstNames.txt");
        // Load last names from a file and place names into a list
        List<String> lastNames = loadNamesFromFile("lastNames.txt");

        // Generate 20 names using the firstNames and lastNames we got from the lists
        Set<String> listOfNames = generateNames(firstNames, lastNames, 20);

        // Convert set to a list for display purposes
        List<String> generatedNames = new ArrayList<>(listOfNames);

        // Print 1-20 of the generated names
        System.out.println("Generated Names:");
        int index = 1;
        for (String name : generatedNames) {
            System.out.println("\t" + index + ". " + name);
            index++;
        }

        // Sort by first name
        // Sort the names by first characters in first name
        generatedNames.sort(Comparator.comparing(name -> name.split(" ")[0]));
        System.out.println("\nSorted by First Name:");
        // Print the sorted and compared names
        generatedNames.forEach(name -> System.out.println("\t" + name));

        // Sort by last name and display
        // Sort the names by first characters in last name
        generatedNames.sort(Comparator.comparing(name -> name.split(" ")[1]));
        System.out.println("\nSorted by Last Name:");
        // Print the sorted and compared names
        generatedNames.forEach(name -> System.out.println("\t" + name));

        // Find and display names that share a common first name
        Map<String, List<String>> lastNamesByFirstName = groupLastNamesByFirstName(generatedNames);
        System.out.println("\nNames with the same First Name:");
        // Make the map with the key as first name and value is the last name list
        lastNamesByFirstName.forEach((firstName, lastNamesList) -> {
            String charLastNames = String.join("\n\t\t", lastNamesList);
            System.out.println("\t" + firstName + "\n\t\t" + charLastNames);
        });
    }

    // Loading from a file
    private static List<String> loadNamesFromFile(String fileName) {
        // Read from file
        try {
            return Files.readAllLines(Paths.get(fileName));
        // Catch error of empty or blank file
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Generate names method
    private static Set<String> generateNames(List<String> firstNames, List<String> lastNames, int numberOfNames) {
        // Make a new set(to avoid duplicates) with our generated names
        Set<String> generatedNames = new HashSet<>();
        Random random = new Random();

        // Wile our generated names is still less than our required names
        while (generatedNames.size() < numberOfNames) {
            // Select random first name
            String firstName = firstNames.get(random.nextInt(firstNames.size()));
            // Select random last name
            String lastName = lastNames.get(random.nextInt(lastNames.size()));
            // Add first name and last name together in the set as a generated name
            generatedNames.add(firstName + " " + lastName);
        }

        return generatedNames;
    }

    // Group names by their corresponding first name
    private static Map<String, List<String>> groupLastNamesByFirstName(List<String> names) {
        // Create Hashmap storing first names as keys and the last names are lists
        Map<String, List<String>> lastNamesByFirstName = new HashMap<>();

        // For each name in our list of names
        for (String name : names) {
            // Split the full name into first and last names
            String[] parts = name.split(" ");
            String firstName = parts[0];
            String lastName = parts[1];
            // In our HashMap it takes the first name as the key, and adds the lastname as a list value
            lastNamesByFirstName.computeIfAbsent(firstName, k -> new ArrayList<>()).add(lastName);
        }

        return lastNamesByFirstName;
    }
}

