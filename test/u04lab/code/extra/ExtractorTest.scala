package u04lab.code.extra

import org.junit.jupiter.api.{Assertions, Test}
import u04lab.code.Course

object ExtractorTest {
    @Test
    def testTeacherExtractor(): Unit = {
        val course1 = Course("ABC", "Rossi")
        val course2 = Course("DEF", "Bianchi")
        val course3 = Course("GHI", "Rossi")
        val courses1 = List(course1, course2, course3)
        courses1 match {
            case sameTeacher(_) => Assertions.fail()
            case _ =>
        }
        val courses2 = List(course1, course3)
        courses2 match {
            case sameTeacher(t) => Assertions.assertEquals(t, "Rossi")
            case _ => Assertions.fail()
        }
    }
}
