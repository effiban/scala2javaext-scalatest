package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class MatcherAssertionTransformerImplTest extends UnitTestSuite {

  private val termNameClassifier = mock[ScalatestTermNameClassifier]
  private val matcherTransformer = mock[MatcherTransformer]

  private val matcherAssertionTransformer = new MatcherAssertionTransformerImpl(termNameClassifier, matcherTransformer)

  test("transform() when verb is equality matcher verb, and matcher is transformed") {
    val actual = q"x"
    val verb = q"shouldBe"
    val matcher = q"3"
    val expectedAdjustedMatcher = q"equal(3)"
    val expectedHamcrestMatcher = q"is(3)"
    val expectedAssertion = q"assertThat(x, is(3))"

    when(termNameClassifier.isEqualityMatcherVerb(eqTree(verb))).thenReturn(true)
    when(matcherTransformer.transform(eqTree(expectedAdjustedMatcher))).thenReturn(Some(expectedHamcrestMatcher))

    matcherAssertionTransformer.transform(actual, verb, matcher).value.structure shouldBe expectedAssertion.structure
  }

  test("transform() when verb is not equality matcher verb, and matcher is transformed") {
    val actual = q"x"
    val verb = q"should"
    val matcher = q"equal(3)"
    val expectedHamcrestMatcher = q"is(3)"
    val expectedAssertion = q"assertThat(x, is(3))"

    when(termNameClassifier.isEqualityMatcherVerb(eqTree(verb))).thenReturn(false)
    when(matcherTransformer.transform(eqTree(matcher))).thenReturn(Some(expectedHamcrestMatcher))

    matcherAssertionTransformer.transform(actual, verb, matcher).value.structure shouldBe expectedAssertion.structure
  }

  test("transform() when matcher is not transformed should return None") {
    val actual = q"x"
    val verb = q"should"
    val matcher = q"3"

    when(termNameClassifier.isEqualityMatcherVerb(eqTree(verb))).thenReturn(false)
    when(matcherTransformer.transform(eqTree(matcher))).thenReturn(None)

    matcherAssertionTransformer.transform(actual, verb, matcher) shouldBe None
  }
}
