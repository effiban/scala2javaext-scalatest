package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherWordClassifier.{isATypeWord, isEqualWord, isSizeWord}
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestMatcherWordClassifierTest extends UnitTestSuite {

  private val IsEqualWordScenarios = Table(
    ("Word", "IsEqualWord"),
    (q"equal", true),
    (q"===", true),
    (q"be", false),
    (q"shouldBe", false),
    (q"mustBe", false),
    (q"should", false),
    (q"contains", false),
    (q"have", false)
  )

  private val IsSizeWordScenarios = Table(
    ("Word", "IsSizeWord"),
    (q"size", true),
    (q"length", true),
    (q"be", false),
    (q"equal", false),
    (q"contain", false),
    (q"have", false)
  )

  private val IsATypeWordScenarios = Table(
    ("Word", "IsATypeWord"),
    (q"a", true),
    (q"an", true),
    (q"be", false),
    (q"is", false),
  )

  forAll(IsEqualWordScenarios) { case (word: Term.Name, expectedResult: Boolean) =>
    test(s"'$word' is ${if (expectedResult) "" else "not "} an 'equal' word") {
      isEqualWord(word) shouldBe expectedResult
    }
  }

  forAll(IsSizeWordScenarios) { case (word: Term.Name, expectedResult: Boolean) =>
    test(s"'$word' is ${if (expectedResult) "" else "not "} a 'size' word") {
      isSizeWord(word) shouldBe expectedResult
    }
  }

  forAll(IsATypeWordScenarios) { case (word: Term.Name, expectedResult: Boolean) =>
    test(s"'$word' is ${if (expectedResult) "" else "not "} an 'a type' word") {
      isATypeWord(word) shouldBe expectedResult
    }
  }
}
