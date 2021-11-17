import inventory.InventoryManager;
import java.util.HashMap;
import java.util.Map;
import model.CoffeeMachineDetails;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.CoffeeMachine;

import java.lang.reflect.Field;
import service.FileLoader;

public class CoffeeMachineFunctionalTest {

    CoffeeMachine coffeeMachine;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
        coffeeMachine.reset();
        resetSingleton(CoffeeMachine.class, "coffeeMachine");
    }

    public static void resetSingleton(Class clazz, String fieldName) {
        Field instance;
        try {
            instance = clazz.getDeclaredField(fieldName);
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Test
    public void test3OutletsValidInput() throws Exception {
        final String fileName = "input.json";
        CoffeeMachineDetails coffeeMachineDetails = new FileLoader<CoffeeMachineDetails>().loadData(fileName, CoffeeMachineDetails.class);
        int outlet = coffeeMachineDetails.getMachine().getOutlets().getCount();
        coffeeMachine = new CoffeeMachine(outlet);
        coffeeMachine.fillInventory(coffeeMachineDetails);
        Map<String, HashMap<String, Integer>> beverages = coffeeMachineDetails.getMachine().getBeverages();
        coffeeMachine.serveOrders(beverages);
        Assert.assertEquals(4, beverages.size());
    }

    // Test for insufficient ingredients for all drinks
    @Test
    public void test1OutletValidInputInsufficientIngredient() throws Exception {
        final String fileName = "input_2.json";
        CoffeeMachineDetails coffeeMachineDetails = new FileLoader<CoffeeMachineDetails>().loadData(fileName, CoffeeMachineDetails.class);
        int outlet = coffeeMachineDetails.getMachine().getOutlets().getCount();
        coffeeMachine = new CoffeeMachine(outlet);
        coffeeMachine.fillInventory(coffeeMachineDetails);
        Map<String, HashMap<String, Integer>> beverages = coffeeMachineDetails.getMachine().getBeverages();
        coffeeMachine.serveOrders(beverages);
        Assert.assertEquals(4, beverages.size());
    }

    // Test for drink with no ingredients
    @Test
    public void test4OutletsValidInput() throws Exception {
        final String fileName = "input_3.json";
        CoffeeMachineDetails coffeeMachineDetails = new FileLoader<CoffeeMachineDetails>().loadData(fileName, CoffeeMachineDetails.class);
        int outlet = coffeeMachineDetails.getMachine().getOutlets().getCount();
        coffeeMachine = new CoffeeMachine(outlet);
        coffeeMachine.fillInventory(coffeeMachineDetails);
        Map<String, HashMap<String, Integer>> beverages = coffeeMachineDetails.getMachine().getBeverages();
        coffeeMachine.serveOrders(beverages);
        Assert.assertEquals(7, beverages.size());
    }

    // Negative test for beverages with duplicate names
    @Test
    public void test10OutletsValidInput() throws Exception {
        final String fileName = "input_4.json";
        CoffeeMachineDetails coffeeMachineDetails = new FileLoader<CoffeeMachineDetails>().loadData(fileName, CoffeeMachineDetails.class);
        int outlet = coffeeMachineDetails.getMachine().getOutlets().getCount();
        coffeeMachine = new CoffeeMachine(outlet);
        coffeeMachine.fillInventory(coffeeMachineDetails);
        Map<String, HashMap<String, Integer>> beverages = coffeeMachineDetails.getMachine().getBeverages();
        coffeeMachine.serveOrders(beverages);
        Assert.assertEquals(4, beverages.size());
    }

}