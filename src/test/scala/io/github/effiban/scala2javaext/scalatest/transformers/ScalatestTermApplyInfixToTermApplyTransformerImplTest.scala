package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestAssertionWordClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.MatcherAssertionTransformer
import org.mockito.ArgumentMatchersSugar.any

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestTermApplyInfixToTermApplyTransformerImplTest extends UnitTestSuite {

  private val matcherAssertionTransformer = mock[MatcherAssertionTransformer]
  private val assertionWordClassifier = mock[ScalatestAssertionWordClassifier]

  private val termApplyInfixTransformer = new ScalatestTermApplyInfixToTermApplyTransformerImpl(
    matcherAssertionTransformer,
    assertionWordClassifier
  )


  test("transform() when LHS is a Term.Name, 'op' is an assertion word, and matcher is transformed - should return Hamcrest matcher") {
    val inputAssertion = q"x should equal(3)"
    val actual = q"x"
    val word = q"should"
    val matcher = q"equal(3)"
    val expectedHamcrestAssertion = q"assertThat(x, is(3))"

    when(assertionWordClassifier.isAssertionWord(eqTree(word))).thenReturn(true)
    when(matcherAssertionTransformer.transform(eqTree(actual), eqTree(word), eqTree(matcher))).thenReturn(Some(expectedHamcrestAssertion))

    termApplyInfixTransformer.transform(inputAssertion).value.structure shouldBe expectedHamcrestAssertion.structure
  }

  test("transform() when LHS is a Term.Name, 'op' is an assertion word, but matcher is not transformed - should return None") {
    val inputAssertion = q"x should bla(3)"
    val actual = q"x"
    val word = q"should"
    val matcher = q"bla(3)"

    when(assertionWordClassifier.isAssertionWord(eqTree(word))).thenReturn(true)
    when(matcherAssertionTransformer.transform(eqTree(actual), eqTree(word), eqTree(matcher))).thenReturn(None)

    termApplyInfixTransformer.transform(inputAssertion) shouldBe None
  }

  test("transform() when LHS is a Term.Name, but 'op' is not an assertion word - should return None") {
    val inputAssertion = q"x bla equal(3)"
    val word = q"bla"

    when(assertionWordClassifier.isAssertionWord(eqTree(word))).thenReturn(false)

    termApplyInfixTransformer.transform(inputAssertion) shouldBe None

    verifyNoMoreInteractions(matcherAssertionTransformer)
  }

  test("transform() when LHS is a (nested) infix, 'op' is an assertion word, and matcher is transformed - should return Hamcrest matcher") {
    val inputAssertion = q"(x + y) should be(3)"
    val actual = q"(x + y)"
    val word = q"should"
    val matcher = q"be(3)"
    val expectedHamcrestAssertion = q"is(3)"

    when(assertionWordClassifier.isAssertionWord(eqTree(word))).thenReturn(true)
    when(matcherAssertionTransformer.transform(eqTree(actual), eqTree(word), eqTree(matcher))).thenReturn(Some(expectedHamcrestAssertion))

    termApplyInfixTransformer.transform(inputAssertion).value.structure shouldBe expectedHamcrestAssertion.structure
  }

  test("transform() when LHS is an infix including an assertion word, and matcher is transformed - should return Hamcrest matcher") {
    val inputAssertion = q"x should have size 3"
    val actual = q"x"
    val assertionWord = q"should"
    val matcher = q"have size 3"
    val expectedHamcrestAssertion = q"is(3)"

    // Due to the 'right-shift' and recursion, this will be called twice
    when(assertionWordClassifier.isAssertionWord(any[Term.Name])).thenAnswer((word: Term.Name) => word.structure == q"should".structure)
    when(matcherAssertionTransformer.transform(eqTree(actual), eqTree(assertionWord), eqTree(matcher))).thenReturn(Some(expectedHamcrestAssertion))

    termApplyInfixTransformer.transform(inputAssertion).value.structure shouldBe expectedHamcrestAssertion.structure
  }

  test("transform() when LHS is an infix without an assertion word - should return None") {
    val inputAssertion = q"x bla have size 3"

    when(assertionWordClassifier.isAssertionWord(any[Term.Name])).thenReturn(false)

    termApplyInfixTransformer.transform(inputAssertion) shouldBe None

    // Due to the 'right-shift' and recursion, this should be called twice
    verify(assertionWordClassifier, times(2)).isAssertionWord(any[Term.Name])
    verifyNoMoreInteractions(matcherAssertionTransformer)
  }

  test("transform() when has multiple args in the RHS - should return None") {
    val termApplyInfix = q"x should (equal(4), notEqual(5))"

    termApplyInfixTransformer.transform(termApplyInfix) shouldBe None

    verifyNoMoreInteractions(assertionWordClassifier, matcherAssertionTransformer)
  }
}
