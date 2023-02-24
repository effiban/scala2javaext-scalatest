package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.MatchesRegex
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.RegexMatcherTransformer.transform

import scala.meta.{Lit, Term, XtensionQuasiquoteTerm}

class RegexMatcherTransformerTest extends UnitTestSuite {

  test("transform for 'startWith' and the rest valid should return matchesRegex() with '^' prepended to the regex") {
    val matcher = q"""startWith regex "[a-z]+""""
    val hamcrestMatcher = q"""matchesRegex("^[a-z]+")"""

    transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for 'endWith' and the rest valid should return matchesRegex() with '$' appended to the regex") {
    val matcher = q"""endWith regex "[a-z]+""""
    val hamcrestMatcher = Term.Apply(MatchesRegex, List(Lit.String("[a-z]+$")))

    transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for 'include' and the rest valid should return matchesRegex() with '.*' prepended and appended to the regex") {
    val matcher = q"""include regex "[a-z]+""""
    val hamcrestMatcher = q"""matchesRegex(".*[a-z]+.*")"""

    transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for 'fullyMatch' and the rest valid should return matchesPattern() with the regex unchanged") {
    val matcher = q"""fullyMatch regex "[a-z]+""""
    val hamcrestMatcher = q"""matchesPattern("[a-z]+")"""

    transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for 'startWith' with two args should return None") {
    val matcher = q"""startWith regex ("[a-z]+", "a.*")"""

    transform(matcher) shouldBe None
  }

  test("transform when the matcher word is 'bla' should return None") {
    val matcher = q"""bla regex "[a-z]+""""

    transform(matcher) shouldBe None
  }

  test("transform when the 'op' is not 'bla' should return None") {
    val matcher = q"""startWith bla "[a-z]+""""

    transform(matcher) shouldBe None
  }

  test("transform for a Term.Apply should return None") {
    val input = q"""regex("[a-z]+")"""

    transform(input) shouldBe None
  }

  test("transform for a Term.Name should return None") {
    val input = q"regex"

    transform(input) shouldBe None
  }

}
