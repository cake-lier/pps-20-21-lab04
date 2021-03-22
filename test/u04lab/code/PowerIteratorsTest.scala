package u04lab.code

import org.junit.jupiter.api.Assertions._
import org.junit.jupiter.api.Test
import u04lab.code.Lists.List.map
import u04lab.code.Lists._
import u04lab.code.Optionals._

class PowerIteratorsTest {
  val factory = new PowerIteratorsFactoryImpl()

  @Test
  def testIncremental() {
    val pi = factory.incremental(5, _ + 2) // pi produce 5,7,9,11,13,...
    assertEquals(Option.of(5), pi.next())
    assertEquals(Option.of(7), pi.next())
    assertEquals(Option.of(9), pi.next())
    assertEquals(Option.of(11), pi.next())
    assertEquals(List.Cons(5, List.Cons(7, List.Cons(9, List.Cons(11, List.nil)))), pi.allSoFar()) // elementi già prodotti
    (0 until 10).foreach(_ => pi.next()) // procedo in avanti per un po'..
    assertEquals(Option.of(33), pi.next()) // sono arrivato a 33
  }

  @Test
  def testRandom(): Unit = {
    val pi = factory.randomBooleans(4) // pi produce 4 booleani random
    val b1 = pi.next()
    val b2 = pi.next()
    val b3 = pi.next()
    val b4 = pi.next()
    assertEquals(Option.empty, pi.next()) // ne ho già prodotti 4, quindi il prossimo è un opzionale vuoto
    // ho prodotto proprio b1, b2, b3, b4
    assertEquals(List.Cons(b1, List.Cons(b2, List.Cons(b3, List.Cons(b4, List.nil)))), map(pi.allSoFar())(Option.of))
  }

  @Test
  def testFromList(): Unit = {
    val list = List.Cons("a", List.Cons("b", List.Cons("c", List.nil)))
    val pi = factory.fromList(list) // pi produce a,b,c
    assertEquals(Option.of("a"), pi.next())
    assertEquals(Option.of("b"), pi.next())
    assertEquals(List.Cons("a", List.Cons("b", List.nil)), pi.allSoFar()) // fin qui a,b
    assertEquals(Option.of("c"), pi.next())
    assertEquals(list, pi.allSoFar()) // fin qui a,b,c
    assertEquals(Option.empty, pi.next()) // non c'è più niente da produrre
  }

  @Test
  def optionalTestReversedOnList(): Unit = {
    val pi = factory.fromList(List.Cons("a", List.Cons("b", List.Cons("c", List.nil)))) // pi produce a,b,c
    assertEquals(Option.of("a"), pi.next())
    assertEquals(Option.of("b"), pi.next())
    val pi2 = pi.reversed() //pi2 itera su b,a
    assertEquals(Option.of("c"), pi.next()) // c viene prodotto da pi normalmente
    assertEquals(Option.empty, pi.next())
    assertEquals(Option.of("b"), pi2.next())
    assertEquals(Option.of("a"), pi2.next())
    assertEquals(List.Cons("b", List.Cons("a", List.nil)), pi2.allSoFar()) // pi2 ha prodotto b,a
    assertEquals(Option.empty, pi2.next())
  }

  @Test
  def optionalTestReversedOnIncremental(): Unit = {
    val pi = factory.incremental(0, _ + 1) // 0,1,2,3,...
    assertEquals(Option.of(0), pi.next())
    assertEquals(Option.of(1), pi.next())
    assertEquals(Option.of(2), pi.next())
    assertEquals(Option.of(3), pi.next())
    val pi2 = pi.reversed() // pi2 itera su 3,2,1,0
    assertEquals(Option.of(3), pi2.next())
    assertEquals(Option.of(2), pi2.next())
    val pi3 = pi2.reversed() // pi2 ha prodotto 3,2 in passato, quindi pi3 itera su 2,3
    assertEquals(Option.of(2), pi3.next())
    assertEquals(Option.of(3), pi3.next())
    assertEquals(Option.empty, pi3.next())
  }
}