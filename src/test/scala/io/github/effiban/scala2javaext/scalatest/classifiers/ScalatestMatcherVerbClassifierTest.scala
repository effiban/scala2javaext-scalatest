package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherVerbClassifier.{isEqualityMatcherVerb, isMatcherVerb}
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestMatcherVerbClassifierTest extends UnitTestSuite {

  private val IsMatcherVerbScenarios = Table(
    ("Verb", "IsMatcherVerb"),
    (q"should", true),
    (q"shouldBe", true),
    (q"shouldEqual", true),
    (q"must", true),
    (q"may", false),
    (q"will", false),
    (q"want", false)
  )

  private val IsEqualityMatcherVerbScenarios = Table(
    ("Verb", "IsEqualityMatcherVerb"),
    (q"shouldBe", true),
    (q"shouldEqual", true),
    (q"mustBe", true),
    (q"mustEqual", true),
    (q"should", false),
    (q"must", false),
    (q"may", false),
    (q"will", false),
    (q"want", false)
  )

  forAll(IsMatcherVerbScenarios) { case (termName: Term.Name, expectedResult: Boolean) =>
    test(s"'$termName' is ${if (expectedResult) "" else "not "} a matcher verb") {
      isMatcherVerb(termName) shouldBe expectedResult
    }
  }

  forAll(IsEqualityMatcherVerbScenarios) { case (termName: Term.Name, expectedResult: Boolean) =>
    test(s"'$termName' is ${if (expectedResult) "" else "not "} an equality matcher verb") {
      isEqualityMatcherVerb(termName) shouldBe expectedResult
    }
  }
}
