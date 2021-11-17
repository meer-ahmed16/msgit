
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import model.CoffeeMachineDetails;
import service.CoffeeMachine;
import utility.FileLoader;

@Slf4j
public class CoffeeMachineApp {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            log.error("Input fileName");
        }
        String fileName = args[0];
        CoffeeMachineDetails coffeeMachineDetails = new FileLoader<CoffeeMachineDetails>().loadData(fileName, CoffeeMachineDetails.class);
        Map<String, HashMap<String, Integer>> beverages = coffeeMachineDetails.getMachine().getBeverages();

        int outlet = coffeeMachineDetails.getMachine().getOutlets().getCount();

        /**
         * Create coffee machine
         */
        CoffeeMachine coffeeMachine = new CoffeeMachine(outlet);
        /**
         * fill Ingredient Quantities
         */
        coffeeMachine.fillInventory(coffeeMachineDetails);
        /**
         * Serve orders
         */
        coffeeMachine.serveOrders(beverages);
        /**
         * Reset the Machine
         */
        coffeeMachine.reset();
    }
}
