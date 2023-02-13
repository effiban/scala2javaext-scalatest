package io.github.effiban.scala2javaext.scalatest.predicates

import io.github.effiban.scala2java.spi.entities.InvocationArgCoordinates
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class JUnitInvocationArgByNamePredicateTest extends UnitTestSuite {

  private val InvocationArgByNameClassifications = Table(
    ("InvocationArgCoordinates", "ExpectedPassedByName"),
    (InvocationArgCoordinates(invocation = q"assertThrows(classOf[IllegalStateException], doSomething())", index = 0), false),
    (InvocationArgCoordinates(invocation = q"assertThrows(classOf[IllegalStateException], doSomething())", index = 1), true),
    (InvocationArgCoordinates(invocation = q"foo(x)", index = 0), false)
  )

  forAll(InvocationArgByNameClassifications) { case (argCoords: InvocationArgCoordinates, expectedPassedByName: Boolean) =>
    test(s"The argument at: $argCoords is passed by ${if (expectedPassedByName) "name" else "value"}") {
      JUnitInvocationArgByNamePredicate(argCoords)
    }
  }
}
