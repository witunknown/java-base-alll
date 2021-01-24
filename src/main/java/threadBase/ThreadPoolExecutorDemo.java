package threadBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created byX on 2021-01-24 10:15
 * Desc:
 */
public class ThreadPoolExecutorDemo {

    static class SunThreadPool {
        private BlockingQueue<Runnable> workQueue;
        private List<WorkThread> workThreads = new ArrayList<WorkThread>();
        private int corePoolSize;
        private int maxPoolSize;
        private int timeOut;


        public SunThreadPool(BlockingQueue<Runnable> workQueue, int corePoolSize) {
            this.workQueue = workQueue;
            this.corePoolSize = corePoolSize;
            this.workQueue = workQueue;

            init(corePoolSize);
        }

        private void init(int corePoolSize) {
            for (int i = 0; i < corePoolSize; i++) {
                WorkThread thread = new WorkThread();
                thread.start();
                workThreads.add(thread);
            }
        }


        class WorkThread extends Thread {

            public void run() {
                try {
                    while (true) {
                        Runnable task = workQueue.take();
                        task.run();
                        TimeUnit.SECONDS.sleep(2);
                        System.out.println(Thread.currentThread().getName() + "执行任务完成!");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        public void execute(Runnable runnable) {
            try {
                workQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        public boolean shutDown() {
            ReentrantLock lock = new ReentrantLock();
            try {
                lock.lock();
                for (int i = 0; i < workThreads.size(); i++) {
                    try {
                        if (!workThreads.get(i).isInterrupted()) {
                            workThreads.get(i).interrupt();
                        }
                    } catch (Exception e) {
                        System.out.println("catch 打断异常信息");
                    }
                }
            } finally {
                lock.unlock();
                System.out.println("线程池终止");
            }

            return true;
        }
    }


    public static void main(String[] args) throws InterruptedException {

        SunThreadPool threadPool = new SunThreadPool(new ArrayBlockingQueue<Runnable>(4), 4);
        for (int i = 0; i < 10; i++) {
            threadPool.execute(new Runnable() {
                public void run() {
                    System.out.println("执行任务：" + System.currentTimeMillis());
                }
            });
        }

        TimeUnit.SECONDS.sleep(4);

        System.out.println(threadPool.shutDown());

    }
}
