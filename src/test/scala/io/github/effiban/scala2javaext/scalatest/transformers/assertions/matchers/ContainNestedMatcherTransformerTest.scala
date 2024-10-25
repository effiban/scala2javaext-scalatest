package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.ContainNestedMatcherTransformer.transform

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ContainNestedMatcherTransformerTest extends UnitTestSuite {

  private val ValidItemsScenarios = Table(
    ("Word", "Items", "ExpectedHamcrestMatcher"),
    (q"allOf", List(q"3", q"4"), q"org.hamcrest.Matchers.hasItems(3, 4)"),
    (q"atLeastOneOf",
      List(q"3", q"4"),
      q"org.hamcrest.Matchers.anyOf(org.hamcrest.Matchers.hasItem(3), org.hamcrest.Matchers.hasItem(4))"),
    (q"inOrder", List(q"3", q"4"), q"org.hamcrest.Matchers.containsInRelativeOrder(3, 4)"),
    (q"noneOf", List(q"3", q"4"), q"org.hamcrest.Matchers.not(org.hamcrest.Matchers.hasItems(3, 4))")
  )

  private val ValidCollectionScenarios = Table(
    ("Word", "CollectionArg", "ExpectedHamcrestMatcher"),
    (q"theSameElementsAs",
      q"expectedList",
      q"org.hamcrest.Matchers.containsInAnyOrder(expectedList.map(item => org.hamcrest.Matchers.is(item)))"),
    (q"theSameElementsInOrderAs",
      q"expectedList",
      q"org.hamcrest.Matchers.contains(expectedList.map(item => org.hamcrest.Matchers.is(item)))")
  )

  private val InvalidMultiCollectionScenarios = Table(
    ("Word", "MultiCollection"),
    (q"theSameElementsAs", List(q"expectedList1", q"expectedList2")),
    (q"theSameElementsInOrderAs", List(q"expectedList1", q"expectedList2"))
  )

  forAll(ValidItemsScenarios) { case (word: Term.Name, items: List[Term], expectedHamcrestMatcher: Term) =>
    test(s"The matcher '$word${formatValues(items)}' should be transformed to '$expectedHamcrestMatcher'") {
      transform(word, items).value.structure shouldBe expectedHamcrestMatcher.structure
    }
  }

  forAll(ValidCollectionScenarios) { case (word: Term.Name, collection: Term, expectedHamcrestMatcher: Term) =>
    test(s"The matcher '$word $collection' should be transformed to '$expectedHamcrestMatcher'") {
      transform(word, List(collection)).value.structure shouldBe expectedHamcrestMatcher.structure
    }
  }

  forAll(InvalidMultiCollectionScenarios) { case (word: Term.Name, multiCollections: List[Term]) =>
    test(s"The matcher '$word ${formatValues(multiCollections)}' should NOT be transformed") {
      transform(word, multiCollections) shouldBe None
    }
  }

  test("transform() when word is 'bla' should return None") {
    transform(q"bla", List(q"1", q"2")) shouldBe None
  }

  private def formatValues(values: List[Term]) = "(" + values.mkString(",") + ")"
}
