package mdaros.exercises.scala.chapter2.lesson011

class Counter ( count: Int = 0 ) {

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

  def count (): Int = {

    this.count
  }
}