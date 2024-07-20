import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultiThreadedScanner {
    private static final int TIMEOUT = 200;
    private static final int THREAD_POOL_SIZE = 10;
    public static void main(String []args)
    {
        System.out.println("Starting executor!");
        ping_scan();
    }

    public static void ping_scan_range(String subnet)
    {
        int startIP = 1;
        int endIP = 254;
        ExecutorService exSvc = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        for(int i = startIP; i <= endIP; i++)
        {
            final String host = subnet + i;
            exSvc.submit(()->{
                try
                {
                    InetAddress inet = InetAddress.getByName(host);
                    if(inet.isReachable(TIMEOUT))
                        System.out.println("IP "+host+" is UP!");
                }
                catch(IOException e)
                {
                    System.err.println(e.toString());
                }
            });
        }
        exSvc.shutdown();
        try {
            if (!exSvc.awaitTermination(60, TimeUnit.SECONDS)) {
                exSvc.shutdownNow();
            }
        } catch (InterruptedException e) {
            exSvc.shutdownNow();
        }
    }
    public static void ping_scan()
    {
        String sIP = "192.168.86."; // this is my subnet, change to yours!
        int startIP = 1;
        int endIP = 254;
        ExecutorService exSVC = Executors.newFixedThreadPool(THREAD_POOL_SIZE); // max pool size is determined by
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
