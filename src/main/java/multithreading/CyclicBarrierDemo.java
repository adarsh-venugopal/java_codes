package multithreading;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;


public class CyclicBarrierDemo {

  public static void main(String[] args) {
    final int WORKERS = 4;
    CyclicBarrier cyclicBarrier = new CyclicBarrier(WORKERS,
        () -> System.out.println("All workers have reached the barrier. Proceeding to the next phase..."));

    for (int i = 1; i <= WORKERS; i++) {
      new Thread(() -> workerTask(cyclicBarrier), "Worker-" + i).start();
    }
  }

  private static void workerTask(CyclicBarrier barrier) {
    try {
      System.out.println(Thread.currentThread().getName() + " starts Phase 1");
      Thread.sleep(ThreadLocalRandom.current().nextInt(50, 200));
      System.out.println(Thread.currentThread().getName() + " completes Phase 1");

      barrier.await();

      System.out.println(Thread.currentThread().getName() + " starts Phase 2");
      Thread.sleep(ThreadLocalRandom.current().nextInt(50, 200));
      System.out.println(Thread.currentThread().getName() + " completes Phase 2");

      barrier.await();

      System.out.println(Thread.currentThread().getName() + " completes all phases");
    } catch (InterruptedException | BrokenBarrierException e) {
      Thread.currentThread().interrupt();
      System.out.println(Thread.currentThread().getName() + " encountered InterruptedException or BrokenBarrierException");
    }
  }
}
