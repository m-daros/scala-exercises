package mdaros.exercises.scala.chapter1

import scala.annotation.tailrec

object Primes extends App {

  def isPrime ( num: Int ) : Boolean = {

    if ( num <= 0 )
      throw new IllegalArgumentException ( "isPrime ( " + num + " ) is not defined" )

    @tailrec
    def isPrimeUntil ( limit: Int ) : Boolean = {

      if ( limit <= 1 )
        true
      else num % limit != 0 && isPrimeUntil ( limit - 1 )
    }

    isPrimeUntil ( num / 2 )
  }

  val number = 143
  println ( "isPrime ( " + number + " ) = " + isPrime ( number ) )
}