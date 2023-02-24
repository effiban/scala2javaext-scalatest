package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.ContainNestedMatcherTransformer.transform

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ContainNestedMatcherTransformerTest extends UnitTestSuite {

  test("transform() a Term.Apply when fun is 'atLeastOneOf' should return Hamcrest 'hasItems'") {
    val matcher = q"atLeastOneOf(3, 4)"
    val expectedHamcrestMatcher = Term.Apply(HamcrestMatcherTerms.HasItems, List(q"3", q"4"))

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
