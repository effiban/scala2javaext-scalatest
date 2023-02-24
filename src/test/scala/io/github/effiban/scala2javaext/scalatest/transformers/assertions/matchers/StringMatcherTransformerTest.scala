package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class StringMatcherTransformerTest extends UnitTestSuite {

  val stringMatcherTransformer = new StringMatcherTransformer()

  test("transform for 'startWith' with one arg should return corresponding Hamcrest matcher") {
    val matcher = q"""startWith("abc")"""
    val hamcrestMatcher = q"""startsWith("abc")"""

    stringMatcherTransformer.transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for 'endWith' with one arg should return corresponding Hamcrest matcher") {
    val matcher = q"""endWith("abc")"""
    val hamcrestMatcher = q"""endsWith("abc")"""

    stringMatcherTransformer.transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for 'include' with one arg should return corresponding Hamcrest matcher") {
    val matcher = q"""include("abc")"""
    val hamcrestMatcher = q"""containsString("abc")"""

    stringMatcherTransformer.transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for 'startWith' with two args should return None") {
    val matcher = q"""startWith("abc", "def")"""

    stringMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform for Term.Apply with 'unknown' fun should return None") {
    val matcher = q"""unknown("abc")"""

    stringMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform for Term.Name should return None") {
    val input = q"dummy"

    stringMatcherTransformer.transform(input) shouldBe None
  }
}
