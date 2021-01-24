package threadBase;

import java.util.concurrent.*;

/**
 * Created byX on 2021-01-24 15:18
 * Desc:
 */
public class ThreadPoolUseDemo {

    static class MyTask implements Runnable {
        private String name;

        @Override
        public void run() {
            System.out.println("执行线程："+Thread.currentThread().getName()+" task name :"+name +"正在执行！");
        }

        public MyTask(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 6, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4),new ThreadPoolExecutor.CallerRunsPolicy());
        try {
            for (int i = 0; i < 100; i++) {
                threadPool.submit(new MyTask("aaa"+i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        threadPool.shutdown();
    }
}
