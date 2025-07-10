package multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;


public class CountDownLatchDemo {
  public static void main(String[] args) throws InterruptedException {

    CountDownLatch latch = new CountDownLatch(5);

    for(int i = 1; i <= 5; i++) {
      new Thread(() -> {
        try {
          Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
          System.out.println(Thread.currentThread().getName() + " finished it's task");
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        } finally {
          latch.countDown();
        }
      }, "Worker-" + i).start();
    }

    System.out.println(Thread.currentThread().getName() + " waits for all threads to finish");
    latch.await();
    System.out.println(Thread.currentThread().getName() + " now resumes");

  }
}
