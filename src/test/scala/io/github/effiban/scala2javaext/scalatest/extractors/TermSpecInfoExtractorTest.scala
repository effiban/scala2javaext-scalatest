package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.entities.SpecInfo
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class TermSpecInfoExtractorTest extends UnitTestSuite {

  private val infixSpecInfoExtractor = mock[InfixSpecInfoExtractor]

  private val termSpecInfoExtractor = new TermSpecInfoExtractorImpl(infixSpecInfoExtractor)


  test("extract() when spec is an infix and inner extractor returns a result with ignored=false should return it") {
    val infixSpec = q"""it should "do something""""
    val expectedName = q""""it should do something""""

    when(infixSpecInfoExtractor.extract(eqTree(infixSpec))).thenReturn(Some(SpecInfo(expectedName)))

    termSpecInfoExtractor.extract(infixSpec).value shouldBe SpecInfo(expectedName)
  }

  test("extract() when spec is an infix and inner extractor returns a result with ignored=true should return it") {
    val infixSpec = q"""ignore should "do something""""
    val expectedName = q""""it should do something""""

    when(infixSpecInfoExtractor.extract(eqTree(infixSpec))).thenReturn(Some(SpecInfo(expectedName, ignored = true)))

    termSpecInfoExtractor.extract(infixSpec).value shouldBe SpecInfo(expectedName, ignored = true)
  }

  test("extract() when spec is an infix and inner extractor returns None should return None") {
    val infixSpec = q"""it blabla "do something""""

    when(infixSpecInfoExtractor.extract(eqTree(infixSpec))).thenReturn(None)

    termSpecInfoExtractor.extract(infixSpec) shouldBe None
  }

  test("extract() when spec is a literal string should return it with ignored=false") {
    val name = q""""it should do something""""

    termSpecInfoExtractor.extract(name).value shouldBe SpecInfo(name)

    verifyNoMoreInteractions(infixSpecInfoExtractor)
  }

  test("extract() when spec is a Term.Apply should return none") {
    val spec = q"Scenario(something)"

    termSpecInfoExtractor.extract(spec) shouldBe None

    verifyNoMoreInteractions(infixSpecInfoExtractor)
  }
}
