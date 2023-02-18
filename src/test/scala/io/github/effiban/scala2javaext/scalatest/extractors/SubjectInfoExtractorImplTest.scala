package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2java.test.utils.matchers.TreeMatcher.eqTree
import io.github.effiban.scala2javaext.scalatest.classifiers.TestRegistrationWordClassifier
import io.github.effiban.scala2javaext.scalatest.entities.SpecInfo
import io.github.effiban.scala2javaext.scalatest.matchers.SpecInfoScalatestMatcher.equalSpecInfo
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class SubjectInfoExtractorImplTest extends UnitTestSuite {

  private val registrationWordClassifier = mock[TestRegistrationWordClassifier]

  private val subjectInfoExtractor = new SubjectInfoExtractorImpl(registrationWordClassifier)


  test("when subject is the Term.Name 'it' should return the literal 'it' and ignored=false") {
    subjectInfoExtractor.extract(q"it").value should equalSpecInfo(SpecInfo(q""""it""""))
  }

  test("when subject is the Term.Name 'they' should return the literal 'they' and ignored=false") {
    subjectInfoExtractor.extract(q"they").value should equalSpecInfo(SpecInfo(q""""they""""))
  }

  test("when subject is the Term.Name 'ignore' should return the 'it' and ignored=true") {
    val subject = q"ignore"
    when(registrationWordClassifier.isIgnore(eqTree(subject))).thenReturn(true)

    subjectInfoExtractor.extract(subject).value should equalSpecInfo(SpecInfo(q""""it"""", ignored = true))
  }

  test("when subject is the Term.Name 'blabla' should return None") {
    val subject = q"blabla"
    when(registrationWordClassifier.isIgnore(eqTree(subject))).thenReturn(false)

    subjectInfoExtractor.extract(subject) shouldBe None
  }

  test("when subject is a literal string should return the same and ignored=false") {
    val subject = q""""my subject""""
    subjectInfoExtractor.extract(subject).value shouldBe SpecInfo(subject)
  }
}
