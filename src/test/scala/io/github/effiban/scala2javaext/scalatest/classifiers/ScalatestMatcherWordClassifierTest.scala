package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherWordClassifier.isEqualsWord
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestMatcherWordClassifierTest extends UnitTestSuite {

  private val IsEqualsWordScenarios = Table(
    ("Word", "IsEqualsWord"),
    (q"equal", true),
    (q"===", true),
    (q"be", true),
    (q"shouldBe", false),
    (q"mustBe", false),
    (q"should", false),
    (q"contains", false),
    (q"have", false)
  )

  forAll(IsEqualsWordScenarios) { case (word: Term.Name, expectedResult: Boolean) =>
    test(s"'$word' is ${if (expectedResult) "" else "not "} an 'equals' word") {
      isEqualsWord(word) shouldBe expectedResult
    }
  }
}
