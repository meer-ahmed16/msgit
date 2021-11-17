package ingredientInventory;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton design to store and manage ingredient inventory.
 */

public class IngredientManager {
    public Map<String, Integer> inventory = new HashMap<>();

    private IngredientManager() {
    }

    //Using Holder pattern ensures thread safe initialisation of the object,
    private static class InventoryManagerHolder {
        public static final IngredientManager instance = new IngredientManager();
    }

    public static IngredientManager getInstance() {
        return InventoryManagerHolder.instance;
    }

    public int get(String ingredient) {
        return inventory.getOrDefault(ingredient, -1);
    }

    public void reduceQuantity(String ingredient, int quantityToDeduct) {
        inventory.put(ingredient, inventory.getOrDefault(ingredient, 0) - quantityToDeduct);
    }

    public void addInventory(String ingredient, int quantity) {
        int existingInventory = inventory.getOrDefault(ingredient, 0);
        inventory.put(ingredient, existingInventory + quantity);
    }

    public void resetInventory() {
        inventory.clear();
    }
}
