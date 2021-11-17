package service;

import error.handler.ErrorHandler;
import inventory.InventoryManager;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.Beverage;
import model.CoffeeMachineDetails;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import tasks.BeverageMakerTask;

/**
 * Assumptions:
 * 1) The input JSON will not have duplicate beverage names. (Workaround would be to use MultiMap, custom deserialiser. Skipping it as it might be out of scope given time constraints)
 * 2) The input JSON contains correct input. Keeping check on the range of values seems to be out of scope given time constraints(Workaround would be add  javax validations in the input Models itself).
 * <p>
 * Algorithm:
 * A multithreaded system with n threads is invoked to represent n nozzled coffee machine.
 * It queues up all the requests from the input and tries to create the beverages. Importance has been given to thread safety to ensure two drinks do not use same ingredient.
 * Feature to add new ingredient in our inventory is given, as well as to add new Beverage Requests at any given point of time.
 */

/**Represents a physical Coffee Machine, which can serve PARALLELY, using multi threading.
 * Singleton Class to simulate a CoffeeMachine
 * Supports adding beverage requests, with a maximum pending queue size = MAX_QUEUED_REQUEST*/

@Slf4j
public class CoffeeMachine {

    private static CoffeeMachine coffeeMachine;
    private static final int MAX_QUEUED_REQUEST = 100;
    private ThreadPoolExecutor executor;
    private Object lock = new Object();

    public CoffeeMachine(int outlet) {
        System.out.println("New Machine");
        executor = new ThreadPoolExecutor(outlet, outlet, 5000L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(MAX_QUEUED_REQUEST));
        executor.setRejectedExecutionHandler(new ErrorHandler());
    }

    public void fillInventory(CoffeeMachineDetails coffeeMachineDetails) {
        InventoryManager inventoryManager = InventoryManager.getInstance();
        Map<String, Integer> ingredients = coffeeMachineDetails.getMachine().getIngredientQuantityMap();
        for (String key : ingredients.keySet()) {
            inventoryManager.addInventory(key, ingredients.get(key));
        }
    }
    public void serveOrders(Map<String, HashMap<String, Integer>> beverages) {
        for (String key : beverages.keySet()) {
            Beverage beverage = new Beverage(key, beverages.get(key));
            addBeverageRequest(beverage);
        }
    }

    private void addBeverageRequest(Beverage beverage) {
        BeverageMakerTask task = new BeverageMakerTask(beverage) {
            @SneakyThrows
            @Override
            public void run() {
                if (checkAndUpdateInventory(beverage)) {
                    System.out.println(beverage.getName() + " is prepared");
                }
            }
        };
        executor.execute(task);
    }

    public void stopMachine() {
        executor.shutdown();
    }

    /**Resetting inventory and stopping coffee machine. This is only used for testing. In real world, no need for resetting unless machine is stopped.*/
    public void reset() {
        log.info("Resetting");
        this.stopMachine();
        InventoryManager.getInstance().resetInventory();
    }

    //Making this thread safe by synchronizing
    private boolean checkAndUpdateInventory(Beverage beverage) {
        InventoryManager inventory = InventoryManager.getInstance();
        Map<String, Integer> requiredIngredientMap = beverage.getIngredientQuantityMap();
        boolean isPossible = true;

        synchronized (lock) {
            for (String ingredient : requiredIngredientMap.keySet()) {
                int ingredientInventoryCount = inventory.get(ingredient);
                if (ingredientInventoryCount == -1 || ingredientInventoryCount == 0) {
                    System.out.println(
                        beverage.getName() + " cannot be prepared because " + ingredient
                            + " is not available");
                    isPossible = false;
                    break;
                } else if (requiredIngredientMap.get(ingredient) > ingredientInventoryCount) {
                    System.out.println(
                        beverage.getName() + " cannot be prepared because " + ingredient
                            + " is not sufficient");
                    isPossible = false;
                    break;
                }
            }

            if (isPossible) {
                for (String ingredient : requiredIngredientMap.keySet()) {
                    inventory.reduceQuantity(ingredient, requiredIngredientMap.get(ingredient));
                    if(inventory.get(ingredient) == 0) {
                        log.warn(ingredient+" is empty. Please refill");
                    }
                }
            }
        }

        return isPossible;
    }
}
