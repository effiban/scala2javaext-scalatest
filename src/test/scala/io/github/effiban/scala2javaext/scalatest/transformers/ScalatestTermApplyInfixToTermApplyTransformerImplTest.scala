package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestAssertionWordClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.MatcherAssertionTransformer

import scala.meta.XtensionQuasiquoteTerm

class ScalatestTermApplyInfixToTermApplyTransformerImplTest extends UnitTestSuite {

  private val matcherAssertionTransformer = mock[MatcherAssertionTransformer]
  private val assertionWordClassifier = mock[ScalatestAssertionWordClassifier]

  private val termApplyInfixTransformer = new ScalatestTermApplyInfixToTermApplyTransformerImpl(
    matcherAssertionTransformer,
    assertionWordClassifier
  )


  test("transform() when has matcher assertion format, word is an assertion, and matcher is transformed") {
    val inputAssertion = q"x should equal(3)"
    val actual = q"x"
    val word = q"should"
    val matcher = q"equal(3)"
    val expectedHamcrestAssertion = q"assertThat(x, is(3))"

    when(assertionWordClassifier.isAssertionWord(eqTree(word))).thenReturn(true)
    when(matcherAssertionTransformer.transform(eqTree(actual), eqTree(word), eqTree(matcher))).thenReturn(Some(expectedHamcrestAssertion))

    termApplyInfixTransformer.transform(inputAssertion).value.structure shouldBe expectedHamcrestAssertion.structure
  }

  test("transform() when has matcher assertion format, word is an assertion, but matcher is not transformed - should return None") {
    val inputAssertion = q"x should bla(3)"
    val actual = q"x"
    val word = q"should"
    val matcher = q"bla(3)"

    when(assertionWordClassifier.isAssertionWord(eqTree(word))).thenReturn(true)
    when(matcherAssertionTransformer.transform(eqTree(actual), eqTree(word), eqTree(matcher))).thenReturn(None)

    termApplyInfixTransformer.transform(inputAssertion) shouldBe None
  }

  test("transform() when has matcher assertion format, but word is not an assertion - should return None") {
    val inputAssertion = q"x bla equal(3)"
    val word = q"bla"

    when(assertionWordClassifier.isAssertionWord(eqTree(word))).thenReturn(false)

    termApplyInfixTransformer.transform(inputAssertion) shouldBe None

    verifyNoMoreInteractions(matcherAssertionTransformer)
  }

  test("transform() when has unrecognized format - should return None") {
    val termApplyInfix = q"x should (equal(4), notEqual(5))"

    termApplyInfixTransformer.transform(termApplyInfix) shouldBe None

    verifyNoMoreInteractions(assertionWordClassifier, matcherAssertionTransformer)
  }
}
