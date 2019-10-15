package mdaros.exercises.scala.chapter2

object Exercise extends App {

  trait MyPredicate [ -T ] {

    def test ( elem: T ) : Boolean
  }

  trait MyTransformer [ -A, B ] {

    def transform ( elem: A ): B
  }

  class MyList [ +A ] {

    def map [ B ] ( transformer: MyTransformer [ A, B ] ): MyList [ B ] = {

      // TODO ...
      null
    }

  }
}