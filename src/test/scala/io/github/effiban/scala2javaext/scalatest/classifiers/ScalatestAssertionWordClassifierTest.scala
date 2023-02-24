package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestAssertionWordClassifier.{isAssertionWord, isBeAssertionWord, isEqualAssertionWord}
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

  private val IsEqualAssertionWordScenarios = Table(
    ("Word", "IsEqualAssertionWord"),
    (q"shouldEqual", true),
    (q"mustEqual", true),
    (q"shouldBe", false),
    (q"mustBe", false),
    (q"should", false),
    (q"must", false),
    (q"may", false),
    (q"will", false),
    (q"want", false)
  )

  private val IsBeAssertionWordScenarios = Table(
    ("Word", "IsBeAssertionWord"),
    (q"shouldBe", true),
    (q"mustBe", true),
    (q"shouldEqual", false),
    (q"mustEqual", false),
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

  forAll(IsEqualAssertionWordScenarios) { case (word: Term.Name, expectedResult: Boolean) =>
    test(s"'$word' is ${if (expectedResult) "" else "not "} an 'equal' assertion word") {
      isEqualAssertionWord(word) shouldBe expectedResult
    }
  }

  forAll(IsBeAssertionWordScenarios) { case (word: Term.Name, expectedResult: Boolean) =>
    test(s"'$word' is ${if (expectedResult) "" else "not "} a 'be' assertion word") {
      isBeAssertionWord(word) shouldBe expectedResult
    }
  }
}
