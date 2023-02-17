package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class EqualityMatcherTransformerTest extends UnitTestSuite {

  private val termNameClassifier = mock[ScalatestTermNameClassifier]

  private val equalityMatcherTransformer = new EqualityMatcherTransformer(termNameClassifier)


  test("transform() when has correct format and operator is an equality operator, should return the Hamcrest equivalent") {
    val operator = q"equal"
    val matcher = q"equal(3)"
    val expectedHamcrestMatcher = q"is(3)"

    when(termNameClassifier.isEqualityOperator(eqTree(operator))).thenReturn(true)

    equalityMatcherTransformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() when has correct format but operator is not an equality matcher, should return None") {
    val operator = q"bla"
    val matcher = q"bla(3)"

    when(termNameClassifier.isEqualityOperator(eqTree(operator))).thenReturn(false)

    equalityMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform() when has incorrect format should return None") {
    val matcher = q"bla(x, y)"

    equalityMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(termNameClassifier)
  }
}
