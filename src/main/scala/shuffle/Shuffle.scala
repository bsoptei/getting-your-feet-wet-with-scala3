package shuffle

trait Shuffle[A]:
  extension (a: A) def shuffle[T](xs: Iterable[T]): Iterable[T] 
