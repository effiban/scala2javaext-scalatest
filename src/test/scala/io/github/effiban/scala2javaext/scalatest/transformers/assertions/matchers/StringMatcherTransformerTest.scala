package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.StringMatcherTransformer.transform

import scala.meta.XtensionQuasiquoteTerm

class StringMatcherTransformerTest extends UnitTestSuite {

  test("transform for 'startWith' should return Hamcrest 'startsWith'") {
    val word = q"startWith"
    val expected = q""""abc""""
    val expectedHamcrestMatcher = q"""org.hamcrest.Matchers.startsWith("abc")"""

    transform(word, expected).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform for 'endWith' should return Hamcrest 'endsWith'") {
    val word = q"endWith"
    val expected = q""""abc""""
    val expectedHamcrestMatcher = q"""org.hamcrest.Matchers.endsWith("abc")"""

    transform(word, expected).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform for 'include' should return Hamcrest 'containsString'") {
    val word = q"include"
    val expected = q""""abc""""
    val hamcrestMatcher = q"""org.hamcrest.Matchers.containsString("abc")"""

    transform(word, expected).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for 'unknown' should return None") {
    val word = q"unknown"
    val expected = q""""abc""""

    transform(word, expected) shouldBe None
  }
}
