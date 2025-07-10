package multithreading;

import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;


public class ParkingLotDemo {
  public static void main(String[] args) {

    final int PARKING_SLOTS = 3;
    Semaphore permits = new Semaphore(PARKING_SLOTS, true);

    for (int i = 1; i <= 10; ++i) {
      new Thread(() -> {
        try {
          System.out.println(Thread.currentThread().getName() + " is waiting for a slot to park.");
          permits.acquire();
          System.out.println(Thread.currentThread().getName() + " has now parked. "
              + (PARKING_SLOTS - permits.availablePermits()) + " out of " + PARKING_SLOTS + " slots used");
          Thread.sleep(ThreadLocalRandom.current().nextInt(10_000, 15_000));
          System.out.println(Thread.currentThread().getName() + " is now leaving the slot");
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          System.out.println(Thread.currentThread().getName() + " got interrupted");
        } finally {
          permits.release();
        }
      }, "Car-" + i).start();
    }
  }
}