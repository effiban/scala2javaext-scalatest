package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.entities.SpecInfo
import io.github.effiban.scala2javaext.scalatest.matchers.SpecInfoScalatestMatcher.equalSpecInfo
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class InfixSpecInfoExtractorImplTest extends UnitTestSuite {

  private val scalatestTermNameClassifier = mock[ScalatestTermNameClassifier]
  private val subjectInfoExtractor = mock[SubjectInfoExtractor]

  private val infixSpecInfoExtractor = new InfixSpecInfoExtractorImpl(scalatestTermNameClassifier, subjectInfoExtractor)


  test("extract() when valid and not ignored, should return correct spec name") {
    val infixSpec = q"""it should "work""""
    val expectedSpecInfo = SpecInfo(q""""it should work"""")

    when(scalatestTermNameClassifier.isSpecVerb(eqTree(q"should"))).thenReturn(true)
    when(subjectInfoExtractor.extract(eqTree(q"it"))).thenReturn(Some(SpecInfo(q""""it"""")))

    infixSpecInfoExtractor.extract(infixSpec).value should equalSpecInfo(expectedSpecInfo)
  }

  test("extract() when valid and ignored, should return correct spec name") {
    val infixSpec = q"""ignored should "work""""
    val expectedSpecInfo = SpecInfo(q""""it should work"""", ignored = true)

    when(scalatestTermNameClassifier.isSpecVerb(eqTree(q"should"))).thenReturn(true)
    when(subjectInfoExtractor.extract(eqTree(q"ignored"))).thenReturn(Some(SpecInfo(q""""it"""", ignored = true)))

    infixSpecInfoExtractor.extract(infixSpec).value should equalSpecInfo(expectedSpecInfo)
  }

  test("extract() when verb is invalid should return None") {
    val infixSpec = q"""it blabla "work""""

    when(scalatestTermNameClassifier.isSpecVerb(eqTree(q"blabla"))).thenReturn(false)

    infixSpecInfoExtractor.extract(infixSpec) shouldBe None
  }

  test("extract() when subject is invalid should return None") {
    val infixSpec = q"""blabla should "work""""

    when(scalatestTermNameClassifier.isSpecVerb(eqTree(q"should"))).thenReturn(true)
    when(subjectInfoExtractor.extract(eqTree(q"blabla"))).thenReturn(None)

    infixSpecInfoExtractor.extract(infixSpec) shouldBe None
  }

  test("extract() when expectation is not a literal string should return None") {
    val infixSpec = q"it should blabla"

    infixSpecInfoExtractor.extract(infixSpec) shouldBe None
  }

  test("extract() when there is more than one expectation should return None") {
    val infixSpec = q"""it should("blabla", "gaga")"""

    infixSpecInfoExtractor.extract(infixSpec) shouldBe None
  }
}
