package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.OrderingMatcherTransformer.transform

import scala.meta.XtensionQuasiquoteTerm

class OrderingMatcherTransformerTest extends UnitTestSuite {

  test("transform for '<' should return Hamcrest 'lessThan'") {
    val operator = q"<"
    val expected = q"3"
    val expectedHamcrestMatcher = q"org.hamcrest.Matchers.lessThan(3)"

    transform(operator, expected).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform for '<=' should return Hamcrest 'lessThanOrEqualTo'") {
    val operator = q"<="
    val expected = q"3"
    val expectedHamcrestMatcher = q"org.hamcrest.Matchers.lessThanOrEqualTo(3)"

    transform(operator, expected).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform for '>' should return Hamcrest 'greaterThan'") {
    val operator = q">"
    val expected = q"3"
    val hamcrestMatcher = q"org.hamcrest.Matchers.greaterThan(3)"

    transform(operator, expected).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for '>=' should return Hamcrest 'greaterThanOrEqualTo'") {
    val operator = q">="
    val expected = q"3"
    val hamcrestMatcher = q"org.hamcrest.Matchers.greaterThanOrEqualTo(3)"

    transform(operator, expected).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for '~=' should return None") {
    val operator = q"~="
    val expected = q"3"

    transform(operator, expected) shouldBe None
  }
}
