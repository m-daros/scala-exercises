package mdaros.exercises.scala.chapter2

import mdaros.exercises.scala.chapter2.Exercise.{MyList, MyTransformer}
import mdaros.exercises.scala.chapter2.ExerciseMyList.{Cons, Empty, MyList}
import mdaros.exercises.scala.chapter2.InheritanceExecrices.{Cons, Empty}

object InheritanceExecrices extends App {

  trait MyPredicate [ -T ] {

    def test ( elem: T ) : Boolean
  }

  trait MyTransformer [ -A, B ] {

    def transform ( elem: A ) : B
  }

  abstract class MyList [ +A ] {

    def head () : A

    def tail () : MyList [ A ]

    def isEmpty () : Boolean

    def add [ B >: A ] ( element: B ): MyList [ B ]

    override def toString (): String = "[" + printElements () + "]"

    def printElements (): String

    // higher-order functions
    def map [ B ] ( transformer: A => B ): MyList [ B ]

    def flatMap [ B ] ( transformer: A => MyList [ B ] ): MyList [ B ]

    def filter ( predicate: A => Boolean ): MyList [ A ]

    // concatenation
    def ++ [ B >: A ] ( list: MyList [ B ] ): MyList [ B ]
  }

  // This is an empty list
  case object Empty extends MyList [ Nothing ] {

    def head (): Nothing = throw new NoSuchElementException

    def tail (): MyList [ Nothing ] = throw new NoSuchElementException

    def isEmpty (): Boolean = true

    def add [ B >: Nothing ] ( element: B ) : MyList [ B ] = new Cons ( element, Empty )

    def printElements (): String = ""

    def map [ B ] ( transformer: Nothing => B ): MyList [ B ] = Empty

    def flatMap [ B ] ( transformer: Nothing => MyList [ B ] ): MyList [ B ] = Empty

    def filter ( predicate: Nothing => Boolean ): MyList [ Nothing ] = Empty

    // concatenation
    def ++ [ B >: Nothing ] ( list: MyList [ B ] ): MyList [ B ] = list
  }

  // By definitions this is a non empty list
  case class Cons [ +A ] ( h: A, t: MyList [ A ] ) extends MyList [ A ] {

    def head (): A = h

    def tail (): MyList [ A ] = t

    def isEmpty (): Boolean = false

    def add [ B >: A ] ( element: B ): MyList [ B ] = new Cons ( element, this )

    def printElements (): String = {

      if ( t.isEmpty () ) {

        "" + h
      }
      else {

        h + " " + t.printElements ()
      }
    }

    def filter ( predicate: A => Boolean ): MyList [ A ] = {

      if ( predicate ( h ) ) {

        new Cons ( h, t.filter ( predicate ) )
      }
      else {

        t.filter ( predicate )
      }
    }

    def map [ B ] ( transformer: A => B ): MyList [ B ] = {

      new Cons ( transformer ( h ), t.map ( transformer ) )
    }

    def ++ [ B >: A ] ( list: MyList [ B ] ): MyList [ B ] = new Cons ( h, t ++ list )

    def flatMap [ B ] ( transformer: A => MyList [ B ] ): MyList [ B ] = {

      transformer ( h ) ++ t.flatMap ( transformer )
    }
  }

  val oneTwoThreeList = new Cons  ( 1, new Cons ( 2, new Cons ( 3, Empty ) ) )

  println ( "oneTwoThreeList: " + oneTwoThreeList );
  println ( "oneTwoThreeList head: " + oneTwoThreeList.head () );

  val twoThreeList = oneTwoThreeList.tail ()

  println ( "twoThreeList: " + twoThreeList );

  val myListOfIntegers = new Cons [ Int ]  ( 1, new Cons ( 2, new Cons ( 3, new Cons ( 4, Empty ) ) ) )
  val myListOfIntegersDoubled = myListOfIntegers.map ( e => e * 2 )
  val myListOfEvenIntegers = myListOfIntegers.filter ( e => e % 2 == 0 )
  val myListOfOddIntegers = myListOfIntegers.filter ( e => e % 2 == 1 )
  val myListOfIntegersFlatMapped = myListOfIntegers.flatMap (e => new Cons ( e, new Cons ( e + 1, new Cons ( e + 2, Empty ) ) ) )

  println ( "myListOfIntegers: " + myListOfIntegers.toString () )
  println ( "myListOfIntegersDoubled: " + myListOfIntegersDoubled.toString () )
  println ( "myListOfEvenIntegers: " + myListOfEvenIntegers.toString () )
  println ( "myListOfOddIntegers: " + myListOfOddIntegers.toString () )
  println ( "myListOfIntegersFlatMapped: " + myListOfIntegersFlatMapped.toString () )

}
