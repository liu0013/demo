package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if(r == 0 || c == 0) 1
      else if (r == c) 1
      else pascal(c-1, r-1) + pascal(c, r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      var stack = List[Char]()
      var point = chars

      while(!point.isEmpty) {
          val char = point.head
          if(char == '(')  stack = '(' :: stack
          else if(char == ')' && stack.isEmpty) return false
          else if(char == ')' && !stack.isEmpty) stack = stack.init
          else {}
          point = point.tail
        }
        stack.isEmpty
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if (money < 0) 0
      else if (money == 0) 1
      else if (coins.isEmpty) 0
      else countChange(money - coins.head, coins) + countChange(money, coins.tail)
    }

  }
