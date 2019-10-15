package mdaros.exercises.scala.chapter1

import scala.annotation.tailrec

object Fibonacci extends App {

  def fibonacci ( num: Int ) : Long = {

    if ( num < 0 )
      throw new IllegalArgumentException ( "fibonacci ( " + num + " ) is not defined" )
    else if ( num == 0 )
      0
    else if ( num == 1 )
      1
    else
      fibonacci ( num - 1 ) + fibonacci ( num - 2 )
  }

  val number = 7

  println ( "fibonacci ( " + number + " ) = " + fibonacci ( number ) )
}