package concepts;

public class StringConcept {

  public static void main(String[] args) {
    String s1 = "Hello, World";
    String s2 = "Hello, World";
    String s3 = s1;
    String s4 = new String("Hello, World");

    System.out.println(s1 == s2);
    System.out.println(s1 == s3);
    System.out.println(s1 == s4);
  }
}
