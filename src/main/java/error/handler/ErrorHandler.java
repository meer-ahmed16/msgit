package error.handler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This error is thrown when a single outlet has orders queued more than predefined threshold.
 * */

public class ErrorHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.printf("The beverage request %s has been rejected by coffee machine\n", r.toString());
    }
}