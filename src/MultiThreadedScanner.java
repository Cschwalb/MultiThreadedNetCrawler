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
        String sIP = "192.168.86.";
        int startIP = 1;
        int endIP = 254;
        ExecutorService exSVC = Executors.newFixedThreadPool(THEAD_POOL_SIZE);
        for(int i = startIP; i <= endIP; i++)
        {
            final String host = sIP + i;
            exSVC.submit(()->{
                try
                {
                    InetAddress inet = InetAddress.getByName(host);
                    if(inet.isReachable(TIMEOUT))
                        System.out.println("IP "+host+" is UP!");
                }
                catch(IOException e)
                {
                    System.out.println(e.toString());
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
