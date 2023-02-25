package io.github.effiban.scala2javaext.scalatest.manipulators

import io.github.effiban.scala2javaext.scalatest.manipulators.TermApplyInfixManipulator.rightShiftInnerIfPossible
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class TermApplyInfixManipulatorTest extends UnitTestSuite {

  test("rightShiftInnerIfPossible() when LHS is a Term.Name - should return None") {
    rightShiftInnerIfPossible(q"a + b") shouldBe None
  }

  test("rightShiftInnerIfPossible() when LHS is an infix with one arg - should return the shifted infix") {
    rightShiftInnerIfPossible(q"(a + b) + c").value.structure shouldBe q"a + (b + c)".structure
  }

  test("rightShiftInnerIfPossible() when LHS is an infix with two args - should return None") {
    rightShiftInnerIfPossible(q"a + (b, c)") shouldBe None
  }
}
