package com.shannonsandlin;

import java.util.*;

public class RecipeFinder {

    private static final String FIELD_DELIMITER = "\\|";
    private static final int RECIPE_TITLE = 0;
    private static final int CREATOR_NAME = 1;
    private static final int FAMILY_NAME = 2;
    private static final int COOK_TIME = 3;

    private final Scanner keyboard = new Scanner(System.in);

    private List<String> recipeNamesList = new ArrayList<>();
    private List<String> creatorNameList = new ArrayList<>();
    private List<String> familyNameList = new ArrayList<>();
    private List<Integer> cookTimeList = new ArrayList<>();

    public static void main(String[] args) {

        RecipeFinder app = new RecipeFinder();
        app.loadData();
        app.run();

    }

    private void loadData() {

        String[] recipeList = RecipeList.load();
        String[] recipeInformationArray;

        for (int i=0; i<recipeList.length; i++) {
            String recipe = recipeList[i];

            recipeInformationArray = recipe.split("\\|");

            recipeNamesList.add(recipeInformationArray[RECIPE_TITLE]);
            creatorNameList.add(recipeInformationArray[CREATOR_NAME]);
            familyNameList.add(recipeInformationArray[FAMILY_NAME]);
            cookTimeList.add(Integer.parseInt(recipeInformationArray[COOK_TIME]));
        }
    }

    private void run() {

        while (true) {
            // Main menu loop
            printMainMenu();
            int mainMenuSelection = promptForMenuSelection("Please choose an option: ");
            if (mainMenuSelection == 1) {
                // Display data and subsets loop
                while (true) {
                    printDataAndSubsetsMenu();
                    int dataAndSubsetsMenuSelection = promptForMenuSelection("Please choose an option: ");
                    if (dataAndSubsetsMenuSelection == 1) {
                        displayRecipes(RecipeList.load());
                    } else if (dataAndSubsetsMenuSelection == 2) {
                        displayRecipeTitleList(recipeNamesList);
                    } else if (dataAndSubsetsMenuSelection == 3) {
                        displayRecipeCreatorList(creatorNameList);
                    } else if (dataAndSubsetsMenuSelection == 4) {
                        displayFamilyNameList(familyNameList);
                    } else if (dataAndSubsetsMenuSelection == 5) {
                        displayPricesList(cookTimeList);
                    } else if (dataAndSubsetsMenuSelection == 0) {
                        break;
                    }
                }
            }
            else if (mainMenuSelection == 2) {
                while (true) {
                    printSearchBooksMenu();
                    int searchRecipeMenuSelection = promptForMenuSelection("Please choose an option: ");
                    if (searchRecipeMenuSelection == 1) {
                        // Search by title
                        String filterTitle = promptForString("Enter recipe title: ");
                        List<Integer> searchIndexes= filterByTitle(filterTitle);
                        displaySearchResults(searchIndexes);


                    } else if (searchRecipeMenuSelection == 2) {
                        // Search by author
                        String filterAuthor = promptForString("Enter recipe creator: ");
                        List<Integer> searchIndexes= filterByAuthor(filterAuthor);
                        displaySearchResults(searchIndexes);


                    } else if (searchRecipeMenuSelection == 3) {
                        // Search by last Name
                        String lastName = promptForString("Enter family name: ");

                        List<Integer> searchIndexes= filterByFamilyName(lastName);
                        displaySearchResults(searchIndexes);

                    } else if (searchRecipeMenuSelection == 4) {
                        // Search by cook time
                        int filterMinimumCookTime = promptForNumberInput("Enter minimum cook time (minutes): ");
                        int filterMaxCookTime = promptForNumberInput("Enter maximum cook time (maximum): ");

                        List<Integer> searchIndexes = filterByCookTime(filterMinimumCookTime, filterMaxCookTime);
                        displaySearchResults(searchIndexes);

                    } else if (searchRecipeMenuSelection == 5) {
                        //search fastest recipe
                        List<Integer> searchIndexes = findFastestsRecipe();
                        displaySearchResults(searchIndexes);


                    } else if (searchRecipeMenuSelection == 0) {
                        break;
                    }
                }
            } else if (mainMenuSelection == 0) {
                break;
            }
        }

    }


    private void displaySearchResults(List<Integer> indexes){
        String display= "";
        for(Integer displayAtThatIndex: indexes){
            display= (recipeNamesList.get(displayAtThatIndex)+": "+ creatorNameList.get(displayAtThatIndex)+": "+ familyNameList.get(displayAtThatIndex)+ cookTimeList.get(displayAtThatIndex));
            System.out.println(display);
        }

    }
    private List<Integer> filterByTitle(String filterTitle) {
        List<Integer> titleFilters= new ArrayList<>();
        //create a variable to house the lower case title that was given to you
        String lowerTitles= filterTitle.toLowerCase();
        for(int i = 0; i< recipeNamesList.size(); i++){
            if (recipeNamesList.get(i).toLowerCase().contains(lowerTitles)){
                titleFilters.add(i);
            }
         }return titleFilters;
    }

    private List<Integer> filterByAuthor(String filterAuthor) {
       List<Integer> authorFilters= new ArrayList<>();
       String lowerAuthors= filterAuthor.toLowerCase();
       for (int i = 0; i< creatorNameList.size(); i++){
           if (creatorNameList.get(i).toLowerCase().contains(lowerAuthors)){
               authorFilters.add(i);
           }
       }return authorFilters;
    }

    private List<Integer> filterByFamilyName(String lastName) {
        List<Integer> recipesByFamilyName= new ArrayList<>();
        String lowerLastName=lastName.toLowerCase();
        for (int i = 0; i< familyNameList.size(); i++){
            if (familyNameList.get(i).toLowerCase().contains(lowerLastName)){
                recipesByFamilyName.add(i);
            }
        } return recipesByFamilyName;
    }

    private List<Integer> filterByCookTime(int minCookTime, int maxCookTime) {
        List<Integer> recipesByCookTime = new ArrayList<>();
        for (int i = 0; i< cookTimeList.size(); i++){
            if (cookTimeList.get(i)>= minCookTime && cookTimeList.get(i)<=maxCookTime){
                recipesByCookTime.add(i);
            }
        }return recipesByCookTime;
    }


    private List<Integer> findFastestsRecipe(){
        List<Integer> quickestRecipes= new ArrayList<>();
        int currentQuickest=0;

        for (int i = 0; i< recipeNamesList.size(); i++) {
            if (cookTimeList.get(i) >= currentQuickest) {
                currentQuickest = cookTimeList.get(i);
            }
        }
        return quickestRecipes;
    }

    // UI methods

    private void printMainMenu() {
        System.out.println("1: View Recipes");
        System.out.println("2: Search Recipes");
        System.out.println("0: Exit");
        System.out.println();
    }

    private void printDataAndSubsetsMenu() {
        System.out.println("1: View full recipes");
        System.out.println("2: View recipe names");
        System.out.println("3: View recipe creators");
        System.out.println("4: View recipe families");
        System.out.println("5: View cook times");
        System.out.println("0: Return to main menu");
        System.out.println();
    }

    private void printSearchBooksMenu() {
        System.out.println("1: Search by recipe");
        System.out.println("2: Search by recipe creator");
        System.out.println("3: Search by recipe family group");
        System.out.println("4: Search by recipe cook time");
        System.out.println("5: Find fastest recipe");
        System.out.println("0: Return to main menu");
        System.out.println();
    }

    private void displayRecipes(String[] recipes) {
        System.out.println("Recipes");
        System.out.println("-------");
        for (String recipe : recipes) {
            System.out.println(recipe);
        }
        System.out.println();
        promptForReturn();
    }

    private void displayRecipeTitleList(List<String> titles) {
        System.out.println("Recipe Titles");
        System.out.println("-------");
        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i + ": " + titles.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private void displayRecipeCreatorList(List<String> recipeCreators) {
        System.out.println("Recipe Creators");
        System.out.println("-------");
        for (int i = 0; i < recipeCreators.size(); i++) {
            System.out.println(i + ": " + recipeCreators.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private void displayFamilyNameList(List<String> familyName) {
        System.out.println("Family Names");
        System.out.println("---------------");
        for (int i = 0; i < familyName.size(); i++) {
            System.out.println(i + ": " + familyName.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private void displayPricesList(List<Integer> cookTimes) {
        System.out.println("Cook Times");
        System.out.println("------");
        for (int i = 0; i < cookTimes.size(); i++) {
            System.out.println(i + ": " + cookTimes.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private int promptForMenuSelection(String prompt) {
        System.out.print(prompt);
        int menuSelection;
        try {
            menuSelection = Integer.parseInt(keyboard.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    private String promptForString(String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private int promptForNumberInput(String prompt) {
        int year;
        while (true) {
            System.out.println(prompt);
            try {
                year = Integer.parseInt(keyboard.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("The year provided is not well-formed. It must be YYYY.");
            }
        }
        return year;
    }

    private double promptForPrice(String prompt) {
        double price;
        while (true) {
            System.out.println(prompt);
            try {
                price = Double.parseDouble(keyboard.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("The price provided is not a valid monetary value.");
            }
        }
        return price;
    }

    private void promptForReturn() {
        System.out.println("Press RETURN to continue.");
        keyboard.nextLine();
    }
}
