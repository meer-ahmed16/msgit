package error.handler;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**Implementation to handle scenarios when the pending beverage requests has already reached threshold*/
public class ErrorHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.printf("The beverage request %s has been rejected by coffee machine", r.toString());
    }
}