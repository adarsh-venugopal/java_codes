package multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;


public class ParkingLotWithVirtualThreadsDemo {
  public static void main(String[] args) {

    final int PARKING_SLOTS = 3;
    Semaphore permits = new Semaphore(PARKING_SLOTS, true);

    try(ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
      for (int i = 1; i <= 10; ++i) {
        final int id = i;
        executor.submit(() -> {
          try {
            System.out.println("Car ID " + id + " is waiting for a slot to park.");
            permits.acquire();
            System.out.println(
                "Car ID " + id + " has now parked. " + (PARKING_SLOTS - permits.availablePermits())
                    + " out of " + PARKING_SLOTS + " slots used");
            Thread.sleep(ThreadLocalRandom.current().nextInt(10_000, 15_000));
            System.out.println("Car ID " + id + " is now leaving the slot");
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Car ID " + id + " got interrupted");
          } finally {
            permits.release();
          }
        });
      }
    }
    // try-with-resources will call executor.close(),
    // which shuts down and waits for all tasks to finish.
  }
}