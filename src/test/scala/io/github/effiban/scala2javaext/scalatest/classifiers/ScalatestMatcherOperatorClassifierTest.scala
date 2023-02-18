package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherOperatorClassifier.isEquality
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestMatcherOperatorClassifierTest extends UnitTestSuite {

  private val IsEqualityScenarios = Table(
    ("Operator", "IsEquality"),
    (q"equal", true),
    (q"===", true),
    (q"be", true),
    (q"shouldBe", false),
    (q"mustBe", false),
    (q"should", false),
    (q"contains", false),
    (q"have", false)
  )

  forAll(IsEqualityScenarios) { case (termName: Term.Name, expectedResult: Boolean) =>
    test(s"'$termName' is ${if (expectedResult) "" else "not "} an equality operator") {
      isEquality(termName) shouldBe expectedResult
    }
  }
}
