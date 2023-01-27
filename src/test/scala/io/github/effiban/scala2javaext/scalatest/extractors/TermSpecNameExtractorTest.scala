package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class TermSpecNameExtractorTest extends UnitTestSuite {

  private val infixSpecNameExtractor = mock[InfixSpecNameExtractor]

  private val termSpecNameExtractor = new TermSpecNameExtractorImpl(infixSpecNameExtractor)


  test("extract() when spec is an infix and inner extractor returns a result should return it") {
    val infixSpec = q"""it should "do something""""
    val name = q""""it should do something""""

    when(infixSpecNameExtractor.extract(eqTree(infixSpec))).thenReturn(Some(name))

    termSpecNameExtractor.extract(infixSpec).value.structure shouldBe name.structure
  }

  test("extract() when spec is an infix and inner extractor returns None should return None") {
    val infixSpec = q"""it blabla "do something""""

    when(infixSpecNameExtractor.extract(eqTree(infixSpec))).thenReturn(None)

    termSpecNameExtractor.extract(infixSpec) shouldBe None
  }

  test("extract() when spec is a literal string should return it") {
    val name = q""""it should do something""""

    termSpecNameExtractor.extract(name).value.structure shouldBe name.structure

    verifyNoMoreInteractions(infixSpecNameExtractor)
  }

  test("extract() when spec is a Term.Apply should return none") {
    val spec = q"Scenario(something)"

    termSpecNameExtractor.extract(spec) shouldBe None

    verifyNoMoreInteractions(infixSpecNameExtractor)
  }
}
