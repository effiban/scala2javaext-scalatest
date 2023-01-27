package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier.{isSpecVerb, isTermApplyNestedRegistrator, isTermApplyRegistrator, isTermApplyInfixNestedRegistrator}
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestTermNameClassifierTest extends UnitTestSuite {

  private val IsTermApplyRegistratorScenarios = Table(
    ("Term Name", "IsTermApplyRegistrator"),
    (q"test", true),
    (q"Scenario", true),
    (q"scenario", true),
    (q"it", true),
    (q"they", true),
    (q"Feature", false),
    (q"should", false)
  )

  private val IsTermApplyNestedRegistratorScenarios = Table(
    ("Term Name", "IsTermApplyNestedRegistrator"),
    (q"Feature", true),
    (q"feature", true),
    (q"describe", true),
    (q"Scenario", false),
    (q"test", false)
  )

  private val IsTermApplyInfixNestedRegistratorScenarios = Table(
    ("Term Name", "IsTermApplyInfixNestedRegistrator"),
    (q"can", true),
    (q"should", true),
    (q"must", true),
    (q"when", true),
    (q"which", true),
    (q"it", false),
    (q"they", false)
  )

  private val IsSpecVerbScenarios = Table(
    ("Term Name", "IsSpecVerb"),
    (q"can", true),
    (q"should", true),
    (q"must", true),
    (q"may", false),
    (q"will", false),
    (q"want", false)
  )

  forAll(IsTermApplyRegistratorScenarios) { case (termName: Term.Name, expectedResult: Boolean) =>
    test(s"'$termName' is ${if (expectedResult) "" else "not "} a Term.Apply registrator") {
      isTermApplyRegistrator(termName) shouldBe expectedResult
    }
  }

  forAll(IsTermApplyNestedRegistratorScenarios) { case (termName: Term.Name, expectedResult: Boolean) =>
    test(s"'$termName' is ${if (expectedResult) "" else "not "} a Term.Apply nested registrator") {
      isTermApplyNestedRegistrator(termName) shouldBe expectedResult
    }
  }

  forAll(IsTermApplyInfixNestedRegistratorScenarios) { case (termName: Term.Name, expectedResult: Boolean) =>
    test(s"'$termName' is ${if (expectedResult) "" else "not "} a Term.ApplyInfix nested registrator") {
      isTermApplyInfixNestedRegistrator(termName) shouldBe expectedResult
    }
  }

  forAll(IsSpecVerbScenarios) { case (termName: Term.Name, expectedResult: Boolean) =>
    test(s"'$termName' is ${if (expectedResult) "" else "not "} a spec verb") {
      isSpecVerb(termName) shouldBe expectedResult
    }
  }
}
