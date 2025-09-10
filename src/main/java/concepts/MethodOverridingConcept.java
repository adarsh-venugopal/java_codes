package concepts;

public class MethodOverridingConcept {

  public static void main(String[] args) {
    System.out.println("Hello, World!");
    //new Parent().myFunc();
    new Child().myFunc();
  }
}

class Parent {
  void myFunc() throws Exception {
    System.out.println("Parent's myFunc");
  }
}

class Child extends Parent {
  /*
  'myFunc()' in 'concepts.Child' clashes with 'myFunc()' in 'concepts.Parent';
  attempting to assign weaker access privileges ('private'); was 'package-private
  private void myFunc() {
    System.out.println("Child's myFunc");
  }*/

  /* 'myFunc()' in 'concepts.Child' clashes with 'myFunc()' in 'concepts.Parent';
  incompatible return type
  int myFunc() {
    System.out.println("Child's myFunc");
    return 1;
  }*/

  void myFunc() {
    System.out.println("Child's myFunc");
  }
}

class AnotherChild extends Parent {
  void myFunc() {
    System.out.println("AnotherChild's myFunc");
  }
}
