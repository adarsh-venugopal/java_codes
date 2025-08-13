package concepts;

import java.util.concurrent.CompletableFuture;


public class CompletableFutureTest {

  public static void main(String[] args) {
    CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
      System.out.println("Inside of task 1");
//      return "Task 1 complete";
      throw new RuntimeException("Error inside of task 1");
    });

    CompletableFuture<String> task2 = CompletableFuture.supplyAsync(() -> {
      System.out.println("Inside of task 2");
      return "Task 2 complete";
//      throw new RuntimeException("Error inside of task 2");
    });

    CompletableFuture.allOf(task1, task2).join();

    System.out.println(task1.join());
    System.out.println(task2.join());
  }
}
