package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherWordClassifier.isEqualWord
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

  forAll(IsEqualWordScenarios) { case (word: Term.Name, expectedResult: Boolean) =>
    test(s"'$word' is ${if (expectedResult) "" else "not "} an 'equal' word") {
      isEqualWord(word) shouldBe expectedResult
    }
  }
}
