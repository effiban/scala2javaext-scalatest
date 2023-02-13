package io.github.effiban.scala2javaext.scalatest.typeinferrers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.typeinferrers.ScalatestNameTypeInferrer.infer

import scala.meta.{Term, Type, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

class ScalatestNameTypeInferrerTest extends UnitTestSuite {

  private val InferrableScenarios = Table(
    ("Name", "ExpectedType"),
    (q"assert", t"Unit"),
    (q"assertResult", t"Unit"),
    (q"assertThrows", t"Unit"),
    (q"fail", t"Unit"),
  )

  private val NonInferrableScenarios = Table(
    "Name",
    q"bla"
  )

  forAll(InferrableScenarios) { case (name: Term.Name, expectedType: Type) =>
    test(s"$name should be inferred as type $expectedType") {
      infer(name).value.structure shouldBe expectedType.structure
    }
  }

  forAll(NonInferrableScenarios) { name: Term.Name =>
    test(s"$name should not be inferrable") {
      infer(name) shouldBe None
    }
  }
}
