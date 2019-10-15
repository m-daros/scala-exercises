package mdaros.exercises.scala.chapter1

import scala.annotation.tailrec

object Factorial extends App {

  def factorial ( num: Int ) : Long = {

    if ( num < 0 )
      throw  new IllegalArgumentException ( "factorial ( " + num + " ) is not defined" )
    else if ( num < 2 )
      1
    else
      num * factorial ( num - 1 )
  }

  def tailRecFactorial ( num: Int ) : BigInt = {

    @tailrec
    def factorialHelper ( x: Int, accumulator: BigInt ) : BigInt = {

      if ( x <= 1 )
        accumulator
      else
        factorialHelper ( x - 1, x * accumulator )
    }

    factorialHelper ( num, 1 )
  }

  val number = 5
  println ( number + "! = " + factorial ( number  ) )

  val otherNumber = 100000
  println ( otherNumber + "! = " + tailRecFactorial ( otherNumber ) )
}