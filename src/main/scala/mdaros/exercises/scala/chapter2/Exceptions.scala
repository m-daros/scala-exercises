package mdaros.exercises.scala.chapter2

import java.lang.Exception

import scala.annotation.tailrec

object Exceptions extends App {

  class OverflowException extends Exception

  class UnderflowException extends Exception

  class MathCalculationException extends Exception

  object OutOfMemoryErrorProducer {

    def causeOutOfMemory ( str:Array [String] ): Array [String] = {

      @tailrec
      def causeOutOfMemoryWithAccum ( str: Array [String], accum: Array [String] ): Array [String] = {

        if ( str.isEmpty ) {

          str
        }
        else {

          println ( "accum.length (): " + accum.length )
          causeOutOfMemoryWithAccum ( str, accum ++ str )
        }
      }

      causeOutOfMemoryWithAccum ( Array.ofDim [ String ] ( 10000000 ), Array.ofDim [ String ] ( 0 ) )
    }
  }

  object StackOverflowErrorProducer {

    def causeStackOverflow ( str: String ) : String = {

      if ( str == "" ) {

        str
      }
      else {

        str + causeStackOverflow ( str + " " )
      }
    }
  }

  object Calculator {

    def add ( x: Int, y: Int ) : Int = {

      if ( x.toDouble + y.toDouble > Int.MaxValue  ) {

        throw new OverflowException (); // TODO Use message
      }
      else if ( x.toDouble + y.toDouble < Int.MinValue  ) {

        throw new UnderflowException ();
      }
      else {

        x + y
      }
    }

    def subtract ( x: Int, y: Int ) : Int = {

      if ( x.toDouble - y.toDouble > Int.MaxValue  ) {

        throw new OverflowException (); // TODO Use message
      }
      else if ( x.toDouble - y.toDouble < Int.MinValue  ) {

        throw new UnderflowException (); // TODO Use message
      }
      else {

        x - y
      }
    }

    def multiply ( x: Int, y: Int ) : Int = {

      if ( x.toDouble * y.toDouble > Int.MaxValue  ) {

        throw new OverflowException (); // TODO Use message
      }
      else if ( x.toDouble * y.toDouble < Int.MinValue  ) {

        throw new UnderflowException (); // TODO Use message
      }
      else {

        x * y
      }
    }

    def divide ( x: Int, y: Int ) : Double = {

      if ( y == 0  ) {

        val message : String = "Divide by 0"
        throw new MathCalculationException (); // TODO Use message
      }
      else if ( x.toDouble / y.toDouble > Int.MaxValue  ) {

        throw new OverflowException (); // TODO Use message
      }
      else if ( x.toDouble / y.toDouble < Int.MinValue  ) {

        throw new UnderflowException (); // TODO Use message
      }
      else {

        x.toDouble / y.toDouble
      }
    }
  }

  //val outOfMemryProducer = OutOfMemoryErrorProducer
  //outOfMemryProducer.causeOutOfMemory ( Array.ofDim [ String ] ( 10000000 ) )

  //val stackOverflowProducer = StackOverflowErrorProducer
  //stackOverflowProducer.causeStackOverflow ( " " )

  /*
  val calculator = Calculator

  println ( "calculator.add ( 1, 2 ) = " + calculator.add ( 1, 2 ) )
   */

  val calculator = Calculator

  println ( "calculator.sum ( 1, 2 ) = " + calculator.add ( 1, 2 ) )
  println ( "calculator.subtract ( 1, 2 ) = " + calculator.subtract ( 1, 2 ) )
  println ( "calculator.multiply ( 1, 2 ) = " + calculator.multiply ( 1, 2 ) )
  println ( "calculator.divide ( 1, 2 ) = " + calculator.divide ( 1, 2 ) )

//  println ( "calculator.sum ( 1, Int.MaxValue ) = " + calculator.add ( 1, Int.MaxValue ) )
  println ( "calculator.subtract ( -10, Int.MaxValue  ) = " + calculator.subtract ( -10, Int.MaxValue ) )
//  println ( "calculator.multiply ( Int.MaxValue, 2 ) = " + calculator.multiply ( Int.MaxValue, 2 ) )
//  println ( "calculator.divide ( 1, 0 ) = " + calculator.divide ( 1, 0 ) )

}