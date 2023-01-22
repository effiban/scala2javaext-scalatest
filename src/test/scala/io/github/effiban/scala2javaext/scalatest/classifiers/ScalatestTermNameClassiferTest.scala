package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier.isSpecVerb
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestTermNameClassiferTest extends UnitTestSuite {

  private val Scenarios = Table(
    ("Term Name", "IsSpecVerb"),
    (q"can", true),
    (q"should", true),
    (q"must", true),
    (q"may", false),
    (q"will", false),
    (q"want", false)
  )

  forAll(Scenarios) { case (termName: Term.Name, expectedIsSpecVerb: Boolean) =>
    test(s"'$termName' is ${if (expectedIsSpecVerb) "" else "not "} a spec verb") {
      isSpecVerb(termName) shouldBe expectedIsSpecVerb
    }
  }

}
