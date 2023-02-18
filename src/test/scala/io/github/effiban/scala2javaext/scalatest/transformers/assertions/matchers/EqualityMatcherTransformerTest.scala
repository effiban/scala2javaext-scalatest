package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherWordClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class EqualityMatcherTransformerTest extends UnitTestSuite {

  private val matcherOperatorClassifier = mock[ScalatestMatcherWordClassifier]

  private val equalityMatcherTransformer = new EqualityMatcherTransformer(matcherOperatorClassifier)


  test("transform() when has correct format and operator is an equality operator, should return the Hamcrest equivalent") {
    val matcherWord = q"equal"
    val matcher = q"equal(3)"
    val expectedHamcrestMatcher = q"is(3)"

    when(matcherOperatorClassifier.isEqualsWord(eqTree(matcherWord))).thenReturn(true)

    equalityMatcherTransformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() when has correct format but operator is not an equality operator, should return None") {
    val matcherWord = q"bla"
    val matcher = q"bla(3)"

    when(matcherOperatorClassifier.isEqualsWord(eqTree(matcherWord))).thenReturn(false)

    equalityMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform() when has incorrect format should return None") {
    val matcher = q"bla(x, y)"

    equalityMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(matcherOperatorClassifier)
  }
}
