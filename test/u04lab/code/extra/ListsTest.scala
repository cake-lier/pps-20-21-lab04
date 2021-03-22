package u04lab.code.extra

import org.junit.jupiter.api.{Assertions, Test}
import u04lab.code.Lists

object ListsTest {
    @Test
    def testListFactory(): Unit = {
        Assertions.assertEquals(Lists.List.Cons(1, Lists.List.Cons(2, Lists.List.Cons(3, Lists.List.Cons(4, Lists.List.nil)))),
                                List(1, 2, 3, 4))
        Assertions.assertEquals(Lists.List.nil, List())
    }
}
