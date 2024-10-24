package io.github.effiban.scala2javaext.scalatest.typeinferrers

import io.github.effiban.scala2java.spi.contexts.TermSelectInferenceContext
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import io.github.effiban.scala2javaext.scalatest.typeinferrers.ScalatestSelectTypeInferrer.infer

import scala.meta.{Term, Type, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

class ScalatestSelectTypeInferrerTest extends UnitTestSuite {

  private val ValidScenarios = Table(
    ("Qualifier Type", "Qualified Name", "Expected Type"),
    (t"org.scalatest.Assertions", q"super.assert", t"scala.Unit"),
    (t"org.scalatest.Assertions", q"super.assertResult", t"scala.Unit"),
    (t"org.scalatest.Assertions", q"super.assertThrows", t"scala.Unit"),
    (t"org.scalatest.Assertions", q"super.fail", t"scala.Unit")
  )

  private val InvalidScenariosWithWrongQualifierType = Table(
    ("Qualifier Type", "Qualified Name"),
    (t"org.scalatest.Bla", q"super.assert"),
    (t"org.scalatest.Bla", q"super.assertResult"),
    (t"org.scalatest.Bla", q"super.assertThrows"),
    (t"org.scalatest.Bla", q"super.fail")
  )

  private val InvalidScenariosWithoutQualifierType = Table(
    ("Qualified Name"),
    (q"x.assert"),
    (q"x.assertResult"),
    (q"x.assertThrows"),
    (q"x.fail"),
    (q"x.bla")
  )

  forAll(ValidScenarios) { case (qualifierType: Type, qualifiedName: Term.Select, expectedType: Type) =>
    test(s"Qualifier type $qualifierType with name ${qualifiedName.name} should be inferred as type $expectedType") {
      val context = TermSelectInferenceContext(maybeQualType = Some(qualifierType))
      infer(qualifiedName, context).value.structure shouldBe expectedType.structure
    }
  }

  forAll(InvalidScenariosWithWrongQualifierType) { case (qualifierType: Type, qualifiedName: Term.Select) =>
    test(s"Qualifier type $qualifierType with name ${qualifiedName.name} should not be inferrable") {
      val context = TermSelectInferenceContext(maybeQualType = Some(qualifierType))
      infer(qualifiedName, context) shouldBe None
    }
  }

  forAll(InvalidScenariosWithoutQualifierType) { (qualifiedName: Term.Select) =>
    test(s"$qualifiedName with no qualifier type should not be inferrable") {
      infer(qualifiedName, TermSelectInferenceContext()) shouldBe None
    }
  }
}
