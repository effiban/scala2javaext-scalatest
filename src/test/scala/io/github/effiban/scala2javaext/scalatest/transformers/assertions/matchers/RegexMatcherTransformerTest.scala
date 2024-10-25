package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.MatchesRegex
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.RegexMatcherTransformer.transform

import scala.meta.{Lit, Term, XtensionQuasiquoteTerm}

class RegexMatcherTransformerTest extends UnitTestSuite {

  test("transform for 'super.startWith' and the rest valid should return matchesRegex() with '^' prepended to the regex") {
    val matcher = q"""super.startWith regex "[a-z]+""""
    val hamcrestMatcher = q"""org.hamcrest.Matchers.matchesRegex("^[a-z]+")"""

    transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for 'super.endWith' and the rest valid should return matchesRegex() with '$' appended to the regex") {
    val matcher = q"""super.endWith regex "[a-z]+""""
    val hamcrestMatcher = Term.Apply(MatchesRegex, List(Lit.String("[a-z]+$")))

    transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for 'super.include' and the rest valid should return matchesRegex() with '.*' prepended and appended to the regex") {
    val matcher = q"""super.include regex "[a-z]+""""
    val hamcrestMatcher = q"""org.hamcrest.Matchers.matchesRegex(".*[a-z]+.*")"""

    transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for 'super.fullyMatch' and the rest valid should return matchesPattern() with the regex unchanged") {
    val matcher = q"""super.fullyMatch regex "[a-z]+""""
    val hamcrestMatcher = q"""org.hamcrest.Matchers.matchesPattern("[a-z]+")"""

    transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform for 'super.startWith' with two args should return None") {
    val matcher = q"""super.startWith regex ("[a-z]+", "a.*")"""

    transform(matcher) shouldBe None
  }

  test("transform when the matcher word is 'super.bla' should return None") {
    val matcher = q"""super.bla regex "[a-z]+""""

    transform(matcher) shouldBe None
  }

  test("transform when the 'op' is 'bla' should return None") {
    val matcher = q"""super.startWith bla "[a-z]+""""

    transform(matcher) shouldBe None
  }

  test("transform for a Term.Apply should return None") {
    val input = q"""super.startWith(regex("[a-z]+"))"""

    transform(input) shouldBe None
  }

  test("transform for a Term.Name should return None") {
    val input = q"regex"

    transform(input) shouldBe None
  }

}
