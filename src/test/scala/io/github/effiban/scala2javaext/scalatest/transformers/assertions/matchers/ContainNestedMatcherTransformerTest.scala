package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.ContainNestedMatcherTransformer.transform

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ContainNestedMatcherTransformerTest extends UnitTestSuite {

  private val ValidScenarios = Table(
    ("Word", "Items", "ExpectedHamcrestMatcher"),
    (q"allOf", List(q"3", q"4"), q"hasItems(3, 4)"),
    (q"atLeastOneOf", List(q"3", q"4"), q"anyOf(hasItem(3), hasItem(4))"),
    (q"noneOf", List(q"3", q"4"), q"not(hasItems(3, 4))"),
  )

  forAll(ValidScenarios) { case (word: Term.Name, items: List[Term], expectedHamcrestMatcher: Term) =>
    test(s"The matcher '$word${formatItems(items)}' should be transformed to '$expectedHamcrestMatcher'") {
      transform(word, items).value.structure shouldBe expectedHamcrestMatcher.structure
    }
  }

  test("transform() when word is 'bla' should return None") {
    transform(q"bla", List(q"1", q"2")) shouldBe None
  }

  private def formatItems(items: List[Term]) = "(" + items.mkString(",") + ")"
}
