package u04lab.code.extra

import u04lab.code.Lists.List
import u04lab.code.Lists.List.{Cons, nil}

object List {
    def apply[A](elements: A*): List[A] = elements.foldRight(nil[A])(Cons[A])
}
