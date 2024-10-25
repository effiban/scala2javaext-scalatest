package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherWordClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class EqualMatcherTransformerTest extends UnitTestSuite {

  private val matcherOperatorClassifier = mock[ScalatestMatcherWordClassifier]

  private val equalityMatcherTransformer = new EqualMatcherTransformer(matcherOperatorClassifier)


  test("transform() when has correct format and matcher has a qualified 'equal' word, should return the Hamcrest equivalent") {
    val matcherWord = q"equal"
    val matcher = q"super.equal(3)"
    val expectedHamcrestMatcher = q"org.hamcrest.Matchers.is(3)"

    when(matcherOperatorClassifier.isEqualWord(eqTree(matcherWord))).thenReturn(true)

    equalityMatcherTransformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() when has correct format and matcher has an unqualified 'equal' word, should return the Hamcrest equivalent") {
    val matcherWord = q"equal"
    val matcher = q"equal(3)"
    val expectedHamcrestMatcher = q"org.hamcrest.Matchers.is(3)"

    when(matcherOperatorClassifier.isEqualWord(eqTree(matcherWord))).thenReturn(true)

    equalityMatcherTransformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() when has correct format but matcher has a qualified non-'equal' word, should return None") {
    val matcherWord = q"bla"
    val matcher = q"super.bla(3)"

    when(matcherOperatorClassifier.isEqualWord(eqTree(matcherWord))).thenReturn(false)

    equalityMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform() when has correct format but matcher has an unqualified non-'equal' word, should return None") {
    val matcherWord = q"bla"
    val matcher = q"bla(3)"

    when(matcherOperatorClassifier.isEqualWord(eqTree(matcherWord))).thenReturn(false)

    equalityMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform() when has incorrect format should return None") {
    val matcher = q"bla(x, y)"

    equalityMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(matcherOperatorClassifier)
  }
}
