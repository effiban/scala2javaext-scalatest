package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier.{isEqualityMatcherVerb, isEqualityOperator, isIgnore, isMatcherVerb, isSpecVerb, isTermApplyInfixNestedRegistrator, isTermApplyNestedRegistrator, isTermApplyRegistrator}
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

  private val IsMatcherVerbScenarios = Table(
    ("Term Name", "IsMatcherVerb"),
    (q"should", true),
    (q"shouldBe", true),
    (q"shouldEqual", true),
    (q"must", true),
    (q"may", false),
    (q"will", false),
    (q"want", false)
  )

  private val IsEqualityMatcherVerbScenarios = Table(
    ("Term Name", "IsMatcherVerb"),
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

  private val IsEqualityOperatorScenarios = Table(
    ("Term Name", "IsEqualityOperator"),
    (q"equal", true),
    (q"===", true),
    (q"be", true),
    (q"shouldBe", false),
    (q"mustBe", false),
    (q"should", false),
    (q"contains", false),
    (q"have", false)
  )

  private val IsIgnoreScenarios = Table(
    ("Term Name", "IsIgnore"),
    (q"ignore", true),
    (q"Ignore", false),
    (q"in", false)
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

  forAll(IsEqualityOperatorScenarios) { case (termName: Term.Name, expectedResult: Boolean) =>
    test(s"'$termName' is ${if (expectedResult) "" else "not "} an equality operator") {
      isEqualityOperator(termName) shouldBe expectedResult
    }
  }

  forAll(IsIgnoreScenarios) { case (termName: Term.Name, expectedResult: Boolean) =>
    test(s"'$termName' is ${if (expectedResult) "" else "not "} the 'ignore' indicator") {
      isIgnore(termName) shouldBe expectedResult
    }
  }
}
