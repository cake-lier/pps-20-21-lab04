package u04lab.code.extra

import u04lab.code.Course
import u04lab.code.Lists.List
import u04lab.code.Lists.List.{Cons, foldLeft, map}

object sameTeacher {
    def unapply(courses: List[Course]): Option[String] = {
        map(courses)(_.teacher) match {
            case Cons(h, t) => foldLeft[String, Option[String]](t)(Some(h))((option, teacher) => option match {
                case Some(n) if n == teacher => Some(n)
                case _ => None
            })
            case _ => None
        }
    }
}
