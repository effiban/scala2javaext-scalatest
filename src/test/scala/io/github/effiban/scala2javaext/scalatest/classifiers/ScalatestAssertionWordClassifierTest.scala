package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestAssertionWordClassifier.{isAssertionWord, isEqualityAssertionWord}
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestAssertionWordClassifierTest extends UnitTestSuite {

  private val IsAssertionWordScenarios = Table(
    ("Word", "IsAssertionWord"),
    (q"should", true),
    (q"shouldBe", true),
    (q"shouldEqual", true),
    (q"must", true),
    (q"may", false),
    (q"will", false),
    (q"want", false)
  )

  private val IsEqualityAssertionWordScenarios = Table(
    ("Word", "IsEqualityAssertionWord"),
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

  forAll(IsAssertionWordScenarios) { case (word: Term.Name, expectedResult: Boolean) =>
    test(s"'$word' is ${if (expectedResult) "" else "not "} an assertion word") {
      isAssertionWord(word) shouldBe expectedResult
    }
  }

  forAll(IsEqualityAssertionWordScenarios) { case (word: Term.Name, expectedResult: Boolean) =>
    test(s"'$word' is ${if (expectedResult) "" else "not "} an equality assertion word") {
      isEqualityAssertionWord(word) shouldBe expectedResult
    }
  }
}
