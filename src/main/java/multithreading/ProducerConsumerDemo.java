package multithreading;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


class BoundedBuffer<T> {

  private final T[] items;
  int putPtr;
  int takePtr;
  int count;
  ReentrantLock lock = new ReentrantLock();
  Condition canConsume = lock.newCondition();
  Condition canProduce = lock.newCondition();

  @SuppressWarnings("unchecked")
  public BoundedBuffer(int capacity) {
    items = (T[]) new Object[capacity];
    putPtr = takePtr = count = 0;
  }

  public void put(T item) throws InterruptedException {
    lock.lock();
    try {
      while(count == items.length) {
        canProduce.await();
      }
      items[putPtr] = item;
      putPtr = (putPtr + 1) % items.length;
      count++;
      canConsume.signal();
    } finally {
      lock.unlock();
    }
  }

  public T take() throws InterruptedException {
    lock.lock();
    try {
      while(count == 0) {
        canConsume.await();
      }
      T item = items[takePtr];
      takePtr = (takePtr + 1) % items.length;
      count--;
      canProduce.signal();
      return item;
    } finally {
      lock.unlock();
    }
  }

}

public class ProducerConsumerDemo {

  public static void main(String[] args) {
    BoundedBuffer<Integer> buffer = new BoundedBuffer<>(5);

    // create 5 producer threads
    for(int i = 0; i < 5; i++) {
      int finalId = i + 1;
      new Thread(
          () -> producer(buffer, finalId),
          "Producer#" + finalId
      ).start();
    }

    // create 2 consumer threads
    for(int i = 0; i < 2; i++) {
      int finalId = i + 1;
      new Thread(
          () -> consumer(buffer),
          "Consumer#" + finalId
      ).start();
    }

  }

  private static void producer(BoundedBuffer<Integer> buffer, int producerId) {
    try {
      for (int i = 1; i <= 10; i++) {
        Thread.sleep(ThreadLocalRandom.current().nextInt(50, 200));
        int item = producerId * 100 + i;
        buffer.put(item);
        System.out.println("Producer " + Thread.currentThread().getName() + " has produced item " + item);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  private static void consumer(BoundedBuffer<Integer> buffer) {
    try {
      for (int i = 1; i <= 25; i++) {
        Thread.sleep(ThreadLocalRandom.current().nextInt(100, 300));
        int item = buffer.take();
        System.out.println("Consumer " + Thread.currentThread().getName() + " has consumed item " + item);
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

}
