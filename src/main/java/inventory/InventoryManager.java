package inventory;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a thread-safe singleton implementation for an inventory management system.
 * This class takes care of handling all the inventory.
 */

public class InventoryManager {
    public Map<String, Integer> inventory = new HashMap<>();

    private InventoryManager() {
    }

    //Using Holder pattern ensures thread safe initialisation of the object,
    private static class InventoryManagerHolder {
        public static final InventoryManager instance = new InventoryManager();
    }

    public static InventoryManager getInstance() {
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
