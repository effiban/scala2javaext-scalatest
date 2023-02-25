package io.github.effiban.scala2javaext.scalatest.manipulators

import scala.meta.Term

trait TermApplyInfixManipulator {
  def rightShiftInnerIfPossible(infix: Term.ApplyInfix): Option[Term.ApplyInfix]
}

object TermApplyInfixManipulator extends TermApplyInfixManipulator {

  /** This method handles the case where a word is nested inside an inner infix and therefore would not be identified by a pattern.
   * For example, given the matcher assertion '''x should have size 2''' - then '''x should have''' will be parsed as the LHS,
   * so that '''size''' instead of '''should''' will be identified as the assertion word, and the corresponding transformer would fail.<br>
   * To resolve this - a "right-shift" of the word 'have' produces: '''x should (have size 2)''' -
   * and now '''should''' is recognized as the assertion word, as expected.
   */
  def rightShiftInnerIfPossible(infix: Term.ApplyInfix): Option[Term.ApplyInfix] = infix match {
    case outer@Term.ApplyInfix(inner@Term.ApplyInfix(_, _, _, List(innerArg)), _, _, _) =>
      Some(inner.copy(args = List(outer.copy(lhs = innerArg))))
    case _ => None
  }
}
