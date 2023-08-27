package mdaros.exercises.scala.chapter2.lesson013

import scala.language.postfixOps

object Lesson013 extends App {

  class Person ( val name: String, val age: Int, val favouriteMovie: String ) {

    def apply (): String = {

      s"Hello, my name is $name, I'm $age years old and I like $favouriteMovie"
    }

    def apply ( times: Int ): String = {

      s"$name whatched $favouriteMovie $times times"
    }

    def + ( nickName: String ): Person = {

      new Person ( s"$name ($nickName)", this.age, this.favouriteMovie )
    }

    def unary_+ : Person = {

      new Person ( this.name, this.age + 1, this.favouriteMovie )
    }

    def learns ( language: String ): String = {

      s"$name learns $language"
    }

    def learnsScala: String = {

      learns ( "Scala" )
    }

    override def toString: String = {

      s"Person ( name: $name, age: $age, favouriteMovie: $favouriteMovie )"
    }
  }

  val mary = new Person ( "Mary", 26, "The lord of the ring - The two towers" )

  val maryTheSmart = mary + "the smart"           // Infix notation, available for methods having exactly 1 parameter

  val maryAge27 = +mary                           // Prefix notation, available for unary operators, methods defined with unary_ prefix name. Allowed only for + - ! and ~


  println ( mary )
  println ( maryTheSmart )
  println ( maryAge27 )

  println ( mary () )                               // Apply method lets call an object like a function

  println ( mary.learns ( "French" ) )
  println ( mary learns "English" )                 // Infix notation, available for methods having exactly 1 parameter
  println ( mary learnsScala )                      // Postfix notation, available for methods having no parameters

  println ( mary.apply ( 1 ) )                      // Apply method lets call an object like a function
  println ( mary ( 2 ) )                            // Apply method lets call an object like a function
  println ( mary ( 5 ) )                            // Apply method lets call an object like a function
}