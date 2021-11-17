
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import model.CoffeeMachineDetails;
import service.CoffeeMachine;
import service.FileLoader;

@Slf4j
public class CoffeeMachineApp {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            log.error("Input file name required");
        }
        String fileName = args[0];
        CoffeeMachineDetails coffeeMachineDetails = new FileLoader<CoffeeMachineDetails>().loadData(fileName, CoffeeMachineDetails.class);
        Map<String, HashMap<String, Integer>> beverages = coffeeMachineDetails.getMachine().getBeverages();

        int outlet = coffeeMachineDetails.getMachine().getOutlets().getCount();

        CoffeeMachine coffeeMachine = new CoffeeMachine(outlet);
        coffeeMachine.fillInventory(coffeeMachineDetails);
        coffeeMachine.serveOrders(beverages);
        coffeeMachine.reset();
    }
}
