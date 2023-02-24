package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.HasItems
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.ContainNestedMatcherTransformer.transform

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ContainNestedMatcherTransformerTest extends UnitTestSuite {

  test("transform() a Term.Apply when fun is 'atLeastOneOf' should return Hamcrest 'anyOf(hasItem(..), hasItem(..), ...)'") {
    val matcher = q"atLeastOneOf(3, 4)"
    val expectedHamcrestMatcher = q"anyOf(hasItem(3), hasItem(4))"

    transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() a Term.Apply when fun is 'noneOf' should return Hamcrest 'not(hasItems(...))'") {
    val matcher = q"noneOf(3, 4)"
    val expectedHamcrestMatcher = q"not(hasItems(3, 4))"

    transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() a Term.Apply when fun is 'bla' should return None") {
    val matcher = q"bla(3, 4)"

    transform(matcher) shouldBe None
  }

  test("transform() a Term.Name should return None") {
    val matcher = q"atLeastOneOf"

    transform(matcher) shouldBe None
  }
}
