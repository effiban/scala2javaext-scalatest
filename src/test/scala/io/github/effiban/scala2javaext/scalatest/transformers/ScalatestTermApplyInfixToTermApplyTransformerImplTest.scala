package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherVerbClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.MatcherAssertionTransformer

import scala.meta.XtensionQuasiquoteTerm

class ScalatestTermApplyInfixToTermApplyTransformerImplTest extends UnitTestSuite {

  private val matcherAssertionTransformer = mock[MatcherAssertionTransformer]
  private val matcherVerbClassifier = mock[ScalatestMatcherVerbClassifier]

  private val termApplyInfixTransformer = new ScalatestTermApplyInfixToTermApplyTransformerImpl(
    matcherAssertionTransformer,
    matcherVerbClassifier
  )


  test("transform() when has matcher assertion format, verb is a matcher, and matcher is transformed") {
    val inputAssertion = q"x should equal(3)"
    val actual = q"x"
    val verb = q"should"
    val matcher = q"equal(3)"
    val expectedHamcrestAssertion = q"assertThat(x, is(3))"

    when(matcherVerbClassifier.isMatcherVerb(eqTree(verb))).thenReturn(true)
    when(matcherAssertionTransformer.transform(eqTree(actual), eqTree(verb), eqTree(matcher))).thenReturn(Some(expectedHamcrestAssertion))

    termApplyInfixTransformer.transform(inputAssertion).value.structure shouldBe expectedHamcrestAssertion.structure
  }

  test("transform() when has matcher assertion format, verb is a matcher, but matcher is not transformed - should return None") {
    val inputAssertion = q"x should bla(3)"
    val actual = q"x"
    val verb = q"should"
    val matcher = q"bla(3)"

    when(matcherVerbClassifier.isMatcherVerb(eqTree(verb))).thenReturn(true)
    when(matcherAssertionTransformer.transform(eqTree(actual), eqTree(verb), eqTree(matcher))).thenReturn(None)

    termApplyInfixTransformer.transform(inputAssertion) shouldBe None
  }

  test("transform() when has matcher assertion format, but verb is not a matcher - should return None") {
    val inputAssertion = q"x bla equal(3)"
    val verb = q"bla"

    when(matcherVerbClassifier.isMatcherVerb(eqTree(verb))).thenReturn(false)

    termApplyInfixTransformer.transform(inputAssertion) shouldBe None

    verifyNoMoreInteractions(matcherAssertionTransformer)
  }

  test("transform() when has unrecognized format - should return None") {
    val termApplyInfix = q"x should (equal(4), notEqual(5))"

    termApplyInfixTransformer.transform(termApplyInfix) shouldBe None

    verifyNoMoreInteractions(matcherVerbClassifier, matcherAssertionTransformer)
  }
}
