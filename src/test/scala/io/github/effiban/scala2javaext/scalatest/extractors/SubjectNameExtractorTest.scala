package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2javaext.scalatest.extractors.SubjectNameExtractor.extract
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Lit, Term, XtensionQuasiquoteTerm}

class SubjectNameExtractorTest extends UnitTestSuite {

  private val ValidScenarios = Table(
    ("Subject", "Expected Subject Name"),
    (q"it", q""""it""""),
    (q"they", q""""they""""),
    (q""""work correctly"""", q""""work correctly"""")
  )

  private val InvalidScenarios = Table(
    "Term",
    q"should",
    q"when",
    q"must",
    q"call(me)"
  )

  forAll(ValidScenarios) { case (subjectTerm: Term, expectedSubjectName: Lit.String) =>
    test(s"The name of the subject $subjectTerm should be '$expectedSubjectName'") {
      extract(subjectTerm).value.structure shouldBe expectedSubjectName.structure
    }
  }

  forAll(InvalidScenarios) { term: Term =>
    test(s"$term is not a valid subject") {
      extract(term) shouldBe None
    }
  }
}
