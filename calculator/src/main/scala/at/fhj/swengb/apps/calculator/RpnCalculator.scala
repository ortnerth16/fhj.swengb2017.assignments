package at.fhj.swengb.apps.calculator

import java.util.NoSuchElementException

import scala.util.{Success, Try}

/**
  * Companion object for our reverse polish notation calculator.
  */
object RpnCalculator {

  val calc: RpnCalculator = RpnCalculator()

  /**
    * Returns empty RpnCalculator if string is empty, otherwise pushes all operations
    * on the stack of the empty RpnCalculator.
    *
    * @param s a string representing a calculation, for example '1 2 +'
    * @return
    */

  def apply(s: String): Try[RpnCalculator] = {

    if (s == "")
      Try(calc)
    else {
      val help: List[Op] = s.split(' ').map(a => Op(a)).toList
      calc.push(help)
    }

  }
}
  /**
    * Reverse Polish Notation Calculator.
    *
    * @param stack a datastructure holding all operations
    */
  case class RpnCalculator(stack: List[Op] = Nil) {

    /**
      * By pushing Op on the stack, the Op is potentially executed. If it is a Val, it the op instance is just put on the
      * stack, if not then the stack is examined and the correct operation is performed.
      *
      * @param op
      * @return
      */
    def push(op: Op): Try[RpnCalculator] = {
      op match {
        case x : Val => Try(RpnCalculator(stack :+ x))
        case bo : BinOp => {

          def getVal(calculator: RpnCalculator): Val = {
            val value = calculator.pop()._1
            value match {
              case x: Val => x
              case _ : BinOp => throw new NoSuchElementException
            }
          }

          val value1 = getVal(this)
          var remainingCalc = pop()._2
          val value2 = getVal(remainingCalc)
          remainingCalc = remainingCalc.pop()._2

          remainingCalc.push(bo.eval(value1, value2))
        }
      }

    }

    /**
      * Pushes val's on the stack.
      *
      * If op is not a val, pop two numbers from the stack and apply the operation.
      *
      * @param op
      * @return
      */
    def push(op: Seq[Op]): Try[RpnCalculator] = Try(op.foldLeft(RpnCalculator()) ((acc, operation) => acc.push(operation).get))

    /**
      * Returns an tuple of Op and a RevPolCal instance with the remainder of the stack.
      *
      * @return
      */
    def pop(): (Op, RpnCalculator) = (peek(), RpnCalculator(stack.init))

    /**
      * If stack is nonempty, returns the top of the stack. If it is empty, this function throws a NoSuchElementException.
      *
      * @return
      */
    def peek(): Op = {
      if (stack.nonEmpty)
        stack.last
      else
        throw new NoSuchElementException
    }

    /**
      * returns the size of the stack.
      *
      * @return
      */
    def size: Int = stack.size
  }

