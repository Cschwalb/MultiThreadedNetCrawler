import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadedScanner {
    private static final int TIMEOUT = 200;
    private static final int THEAD_POOL_SIZE = 10;
    public static void main(String []args)
    {
        System.out.println("Starting executor!");
        ping_scan();
    }
    public static void ping_scan()
    {
        String sIP = "192.168.86."; // this is my subnet, change to yours!
        int startIP = 1;
        int endIP = 254;
        ExecutorService exSVC = Executors.newFixedThreadPool(THEAD_POOL_SIZE); // max pool size is determined by
        // your thread size
        for(int i = startIP; i <= endIP; i++)
        {
            final String host = sIP + i;
            exSVC.submit(()->{ // lambda!
                try
                {
                    InetAddress inet = InetAddress.getByName(host); // take the string and
                    if(inet.isReachable(TIMEOUT))
                        System.out.println("IP "+host+" is UP!");
                }
                catch(IOException e)
                {
                    System.err.println(e.toString());
                }
            });
        }
        exSVC.shutdown();
        try {
            if (!exSVC.awaitTermination(60, TimeUnit.SECONDS)) {
                exSVC.shutdownNow();
            }
        } catch (InterruptedException e) {
            exSVC.shutdownNow();
        }
    }
}
