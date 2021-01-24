package threadBase;

import com.sun.jmx.snmp.tasks.ThreadService;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.util.concurrent.*;

/**
 * Created byX on 2021-01-24 15:52
 * Desc:
 */
public class ThreadPooluse2Demo {
    public static void main(String[] args) throws InterruptedException {

        //缓存的线程池
        ExecutorService threadPool1 = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            threadPool1.submit(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + System.currentTimeMillis() + ":aaa");
            });
        }
        System.out.println("newCachedThreadPool执行完成");

        //固定线程数
        ExecutorService threadPool2 = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 100; i++) {
            threadPool1.submit(() -> {
                System.out.println(Thread.currentThread().getName() + ":bbb");
            });

        }
        //周期性调度
        ScheduledExecutorService threadPool3 = Executors.newScheduledThreadPool(2);
        System.out.println(System.currentTimeMillis());
        for (int i = 0; i < 10; i++) {
            threadPool3.schedule(() -> {
                System.out.println(Thread.currentThread().getName() + ":ccc");
            }, 3, TimeUnit.SECONDS);
        }
        threadPool3.scheduleAtFixedRate(() -> {
            System.out.println(Thread.currentThread().getName() + ":" + System.currentTimeMillis() + ":ccc");
        }, 4, 10, TimeUnit.SECONDS);

        //
        threadPool3.scheduleWithFixedDelay(() -> {
            System.out.println(Thread.currentThread().getName() + ":" + System.currentTimeMillis() + ":ccc");
        }, 4, 10, TimeUnit.SECONDS);


        ExecutorService threadService4 = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 100; i++) {
            threadService4.submit(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + System.currentTimeMillis() + ":ddd");
            });
        }
    }

}
