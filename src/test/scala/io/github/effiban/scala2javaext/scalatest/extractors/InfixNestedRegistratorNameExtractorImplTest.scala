package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.TestRegistrationWordClassifier
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Lit, XtensionQuasiquoteTerm}

class InfixNestedRegistratorNameExtractorImplTest extends UnitTestSuite {

  private val registrationWordClassifier = mock[TestRegistrationWordClassifier]

  private val extractor = new InfixNestedRegistratorNameExtractorImpl(registrationWordClassifier)


  test("extract() when word is a valid registrator should return it as a string") {
    val word = q"should"
    when(registrationWordClassifier.isTermApplyInfixNestedRegistrator(eqTree(word))).thenReturn(true)

    extractor.extract(word).value.structure shouldBe q""""should"""".structure
  }

  test("extract() when word is not a valid registrator should return None") {
    val word = q"blabla"
    when(registrationWordClassifier.isTermApplyInfixNestedRegistrator(eqTree(word))).thenReturn(false)

    extractor.extract(word) shouldBe None
  }

  test("extract() when word is the wildcard '-' should return an empty literal string") {
    val word = q"-"

    extractor.extract(word).value.structure shouldBe Lit.String("").structure
  }
}
