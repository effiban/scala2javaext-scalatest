package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier.{isNestedTestRegistrationWord, isSpecVerb, isTestRegistrationWord}
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestTermNameClassifierTest extends UnitTestSuite {

  private val IsSpecVerbScenarios = Table(
    ("Term Name", "IsSpecVerb"),
    (q"can", true),
    (q"should", true),
    (q"must", true),
    (q"may", false),
    (q"will", false),
    (q"want", false)
  )

  private val IsTestRegistrationWordScenarios = Table(
    ("Term Name", "IsTestRegistrationWord"),
    (q"test", true),
    (q"Scenario", true),
    (q"scenario", true),
    (q"Feature", false),
    (q"should", false)
  )

  private val IsNestedTestRegistrationWordScenarios = Table(
    ("Term Name", "IsNestedTestRegistrationWord"),
    (q"Feature", true),
    (q"feature", true),
    (q"Scenario", false),
    (q"test", false)
  )

  forAll(IsSpecVerbScenarios) { case (termName: Term.Name, expectedResult: Boolean) =>
    test(s"'$termName' is ${if (expectedResult) "" else "not "} a spec verb") {
      isSpecVerb(termName) shouldBe expectedResult
    }
  }

  forAll(IsTestRegistrationWordScenarios) { case (termName: Term.Name, expectedResult: Boolean) =>
    test(s"'$termName' is ${if (expectedResult) "" else "not "} a test registration word") {
      isTestRegistrationWord(termName) shouldBe expectedResult
    }
  }

  forAll(IsNestedTestRegistrationWordScenarios) { case (termName: Term.Name, expectedResult: Boolean) =>
    test(s"'$termName' is ${if (expectedResult) "" else "not "} a nested test registration word") {
      isNestedTestRegistrationWord(termName) shouldBe expectedResult
    }
  }
}
