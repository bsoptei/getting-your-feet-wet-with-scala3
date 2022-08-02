package shuffle

trait Shuffle[A]:
  extension (a: A) def shuffle[T](xs: Seq[T]): Seq[T] 
