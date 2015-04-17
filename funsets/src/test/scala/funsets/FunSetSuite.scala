package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }


  test("test diff") {
    new TestSets {
      val c1 = diff(s1, s2)
      assert(contains(c1, 1),  "s=1, t=2 1")
      assert(!contains(c1, 2), "s=1, t=2 2")
      assert(!contains(c1, 3),  "s=1, t=2 3")

      val c2 = diff(union(s1, s2), union(s1, s3))
      assert(!contains(c2, 1), "s =[1, 2] t=[1, 3] 1")
      assert(contains(c2, 2),  "s =[1, 2] t=[1, 3] 2")
      assert(!contains(c2, 3), "s =[1, 2] t=[1, 3] 3")
    }
  }


  test("test intersect") {
    new TestSets {
      val c2 = intersect(union(s1, s2), union(s1, s3))
      assert(contains(c2, 1),   "s =[1, 2] t=[1, 3] 1")
      assert(!contains(c2, 2),  "s =[1, 2] t=[1, 3] 2")
      assert(!contains(c2, 3),  "s =[1, 2] t=[1, 3] 3")
    }
  }

  test("test filter") {
    new TestSets {
      val c1 = filter(union(union(s1, s2),s3), p => p%2==0)
      assert(!contains(c1, 1), "[1, 2, 3] p => p%2==0 c1 = [2]" )
      assert(contains(c1, 2),  "[1, 2, 3] p => p%2==0 c1 = [2]" )
      assert(!contains(c1, 3), "[1, 2, 3] p => p%2==0 c1 = [2]" )
    }
  }

  test("test forall") {
    new TestSets {
      val s4 = singletonSet(100)
      val s5 = singletonSet(1001)
      val c1 = forall(union(union(union(union(s1, s2), s3),s4),s5), p => p%2==0)
      assert(!c1, "[1, 2, 3,100, 1001] p => p%2==0 false" )
      val c2 = forall(union(s2,s4), p => p%2==0)
      assert(c2, "[2, 100] p => p%2==0 true" )
    }
  }

  test("test exist") {
    new TestSets {
      val c1 = exists(union(s1,s2), p => p < 2)
      assert(c1, "[1, 2] p => p < 2 true" )
      val c2 = exists(union(s2,s3), p => p < 1)
      assert(!c2, "[2, 3] p => p < 1 false")
    }
  }

  test("test map") {
    new TestSets {
      val c1 = map(union(union(s1, s2),s3), p => p*2)
      assert(contains(c1, 6), "[2, 4, 6]")
      assert(contains(c1, 4), "[2, 4, 6]")
      assert(contains(c1, 2), "[2, 4, 6]")
      assert(!contains(c1, 1), "[2, 4, 6]")
      assert(!contains(c1, 3), "[2, 4, 6]")

    }
  }

}
