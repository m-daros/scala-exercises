package mdaros.exercises.scala.chapter2.lesson011

object Lesson001 extends App {

  val carloCollodi = new Writer ( "Carlo", "Collodi", 1826 )

  val pinocchio = new Novel ( "Pinocchio", 1883, carloCollodi )
  val pinocchioRel2 = pinocchio.copy ( 1885 )

  println ( s"${pinocchio.name} was written by ${carloCollodi.fullName ()}? ${pinocchio.isWrittenBy ( carloCollodi )}" )
  println ( s"${carloCollodi.fullName ()} age when he written ${pinocchio.name}: ${pinocchio.authorAge()}" )
  println ( s"${carloCollodi.fullName ()} age when he written ${pinocchioRel2.name} (second release): ${pinocchioRel2.authorAge()}" )

  val counter = new Counter ()

  println ( s"Counter count: ${counter.count ()}" )

  val conter2 = counter.inc ()

  println ( s"Counter count: ${conter2.count ()}" )

  val conter3 = counter.inc ( 10 )

  println(s"Counter count: ${conter3.count ()}")

  val conter4 = counter.dec ()

  println(s"Counter count: ${conter4.count ()}")

  val conter5 = counter.dec ( 10 )

  println(s"Counter count: ${conter5.count ()}")
}