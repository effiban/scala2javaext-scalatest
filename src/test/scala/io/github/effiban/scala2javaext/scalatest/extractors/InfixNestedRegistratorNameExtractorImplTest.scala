package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class InfixNestedRegistratorNameExtractorImplTest extends UnitTestSuite {

  private val scalatestTermNameClassifier = mock[ScalatestTermNameClassifier]

  private val extractor = new InfixNestedRegistratorNameExtractorImpl(scalatestTermNameClassifier)


  test("extract() when word is a valid registrator should return it as a string") {
    val word = q"should"
    when(scalatestTermNameClassifier.isTermApplyInfixNestedRegistrator(eqTree(word))).thenReturn(true)

    extractor.extract(word).value.structure shouldBe q""""should"""".structure
  }

  test("extract() when word is not a valid registrator should return None") {
    val word = q"blabla"
    when(scalatestTermNameClassifier.isTermApplyInfixNestedRegistrator(eqTree(word))).thenReturn(false)

    extractor.extract(word) shouldBe None
  }
}
