package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherWordClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class SizeMatcherTransformerTest extends UnitTestSuite {

  private val matcherWordClassifier = mock[ScalatestMatcherWordClassifier]

  val sizeMatcherTransformer = new SizeMatcherTransformer(matcherWordClassifier)

  test("transform() for a Term.Apply() when word is a 'size' word, should return the Hamcrest equivalent") {
    val matcher = q"size(3)"
    val word = q"size"
    val hamcrestMatcher = q"hasSize(3)"

    when(matcherWordClassifier.isSizeWord(eqTree(word))).thenReturn(true)

    sizeMatcherTransformer.transform(matcher).value.structure shouldBe hamcrestMatcher.structure
  }

  test("transform() for a Term.Apply() when word is a 'size' word, and there is more than one argument - should return None") {
    val matcher = q"size(3, 4)"
    val word = q"size"

    sizeMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(matcherWordClassifier)
  }

  test("transform() for a Term.Apply() when word is not a 'size' word, should return None") {
    val matcher = q"width(3)"
    val word = q"width"

    when(matcherWordClassifier.isSizeWord(eqTree(word))).thenReturn(false)

    sizeMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform() for a Term.Apply() when 'fun' is not a Term.Name, should return None") {
    val matcher = q"(size bigger than)(3)"

    sizeMatcherTransformer.transform(matcher) shouldBe None

    verifyNoMoreInteractions(matcherWordClassifier)
  }
}
