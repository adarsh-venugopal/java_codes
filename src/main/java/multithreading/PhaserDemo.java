package multithreading;

import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;


public class PhaserDemo {

  public static void main(String[] args) {
    Phaser phaser = new Phaser(1);

    // 5 workers excluding main
    for(int worker = 1; worker <= 5; worker++) {
      phaser.register();
      new Thread(() -> simulateWork(phaser), "Worker-" + worker).start();
    }

    // 3 phases in total
    for(int phase = 1; phase <= 3; phase++) {
      phaser.arriveAndAwaitAdvance(); // wait for all phases/arrivals
      int activeParties = phaser.getRegisteredParties(); // now see updated party count
      System.out.println("Main Worker, Phase - " + phase + " complete. Active Parties - " + activeParties);
    }
    phaser.arriveAndDeregister();
    System.out.println("All phases complete!");
  }

  private static void simulateWork(Phaser phaser) {
    try {
      // phase 1
      Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 5_000));
      System.out.println(Thread.currentThread().getName() + ", Phase - " + 1 + " complete.");
      phaser.arriveAndAwaitAdvance();

      // phase 2
      Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 5_000));
      System.out.println(Thread.currentThread().getName() + ", Phase - " + 2 + " complete.");
      phaser.arriveAndAwaitAdvance();

      if(ThreadLocalRandom.current().nextBoolean()) {
        System.out.println(Thread.currentThread().getName() + " is unable to continue. Deregistering...");
        phaser.arriveAndDeregister();
        return;
      }

      // phase 3
      Thread.sleep(ThreadLocalRandom.current().nextInt(1_000, 5_000));
      System.out.println(Thread.currentThread().getName() + ", Phase - " + 3 + " complete.");
      phaser.arriveAndAwaitAdvance();

      phaser.arriveAndDeregister();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      System.out.println(Thread.currentThread().getName() + " was interrupted");
    }
  }
}
