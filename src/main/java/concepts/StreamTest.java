package concepts;

import io.grpc.internal.Stream;
import java.util.Arrays;
import java.util.List;


public class StreamTest {

  public static void main(String[] args) {
    List<String> words = Arrays.asList("apple", "banana", "grape", "cherry", "kiwi");
    // Intermediate operations are not executed yet
    System.out.println("--- Building the stream pipeline ---");
    var filteredAndMappedStream = words.stream()
        .filter(s -> {
          System.out.println("Filtering: " + s); // This print statement will only execute when needed
          return s.length() > 5;
        })
        .map(s -> {
          System.out.println("Mapping: " + s); // This print statement will only execute for filtered elements
          return s.toUpperCase();
        });

    System.out.println(filteredAndMappedStream);
  }
}
