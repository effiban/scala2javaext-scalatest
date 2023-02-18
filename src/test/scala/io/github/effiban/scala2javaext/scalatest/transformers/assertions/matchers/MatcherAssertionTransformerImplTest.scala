package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestAssertionWordClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class MatcherAssertionTransformerImplTest extends UnitTestSuite {

  private val assertionWordClassifier = mock[ScalatestAssertionWordClassifier]
  private val matcherTransformer = mock[MatcherTransformer]

  private val matcherAssertionTransformer = new MatcherAssertionTransformerImpl(assertionWordClassifier, matcherTransformer)

  test("transform() when assertion word is an 'equal' word, and matcher is transformed") {
    val actual = q"x"
    val assertionWord = q"shouldEqual"
    val matcher = q"3"
    val expectedAdjustedMatcher = q"equal(3)"
    val expectedHamcrestMatcher = q"is(3)"
    val expectedAssertion = q"assertThat(x, is(3))"

    when(assertionWordClassifier.isEqualAssertionWord(eqTree(assertionWord))).thenReturn(true)
    when(matcherTransformer.transform(eqTree(expectedAdjustedMatcher))).thenReturn(Some(expectedHamcrestMatcher))

    matcherAssertionTransformer.transform(actual, assertionWord, matcher).value.structure shouldBe expectedAssertion.structure
  }

  test("transform() when assertion word is a 'be' word, and matcher is transformed") {
    val actual = q"x"
    val assertionWord = q"shouldBe"
    val matcher = q"3"
    val expectedAdjustedMatcher = q"be(3)"
    val expectedHamcrestMatcher = q"is(3)"
    val expectedAssertion = q"assertThat(x, is(3))"

    when(assertionWordClassifier.isBeAssertionWord(eqTree(assertionWord))).thenReturn(true)
    when(matcherTransformer.transform(eqTree(expectedAdjustedMatcher))).thenReturn(Some(expectedHamcrestMatcher))

    matcherAssertionTransformer.transform(actual, assertionWord, matcher).value.structure shouldBe expectedAssertion.structure
  }

  test("transform() when assertion word is basic, and matcher is transformed") {
    val actual = q"x"
    val assertionWord = q"should"
    val matcher = q"equal(3)"
    val expectedHamcrestMatcher = q"is(3)"
    val expectedAssertion = q"assertThat(x, is(3))"

    when(assertionWordClassifier.isEqualAssertionWord(eqTree(assertionWord))).thenReturn(false)
    when(matcherTransformer.transform(eqTree(matcher))).thenReturn(Some(expectedHamcrestMatcher))

    matcherAssertionTransformer.transform(actual, assertionWord, matcher).value.structure shouldBe expectedAssertion.structure
  }

  test("transform() when matcher is not transformed should return None") {
    val actual = q"x"
    val assertionWord = q"should"
    val matcher = q"3"

    when(assertionWordClassifier.isEqualAssertionWord(eqTree(assertionWord))).thenReturn(false)
    when(matcherTransformer.transform(eqTree(matcher))).thenReturn(None)

    matcherAssertionTransformer.transform(actual, assertionWord, matcher) shouldBe None
  }
}
