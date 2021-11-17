package tasks;

import lombok.AllArgsConstructor;
import model.Beverage;

/**This class represents an atomic task to make any Beverage.
 * Uses Runnable interface to support multithreading */

@AllArgsConstructor
public abstract class BeverageMakerTask implements Runnable {
    private Beverage beverage;

    public abstract void run();

    @Override
    public String toString() {
        return beverage.getName();
    }
}
