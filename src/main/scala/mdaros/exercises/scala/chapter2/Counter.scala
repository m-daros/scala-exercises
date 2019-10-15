package mdaros.exercises.scala.chapter2

class Counter ( count: Int ) {

  def inc (): Counter = {

    new Counter ( count + 1 )
  }

  def inc ( n: Int ): Counter = {

    new Counter ( count + n )
  }

  def dec (): Counter = {

    new Counter ( count - 1 )
  }

  def dec ( n: Int ): Counter = {

    new Counter ( count - n )
  }
}