package tasks;

import lombok.AllArgsConstructor;
import model.Beverage;

/**
 * This class mimics an order to make any Beverage.
 */

@AllArgsConstructor
public abstract class BeverageMakerTask implements Runnable {
    private Beverage beverage;

    public abstract void run();

    @Override
    public String toString() {
        return beverage.getName();
    }
}
