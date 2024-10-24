package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.SameInstanceMatcherTransformer.transform

import scala.meta.XtensionQuasiquoteTerm

class SameInstanceMatcherTransformerTest extends UnitTestSuite {

  test("transform for 'theSameInstanceAs' should return Hamcrest 'sameInstance'") {
    val word = q"theSameInstanceAs"
    val expected = q"expected"
    val hamcrestMatcher = q"org.hamcrest.Matchers.sameInstance(expected)"

    transform(word, expected).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for 'bla' should return None") {
    val operator = q"bla"
    val expected = q"org.hamcrest.Matchers.expected"

    transform(operator, expected) shouldBe None
  }
}
