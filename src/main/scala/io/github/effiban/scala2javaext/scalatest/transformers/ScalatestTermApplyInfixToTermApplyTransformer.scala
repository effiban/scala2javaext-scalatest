package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.TermApplyInfixToTermApplyTransformer
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestAssertionWordClassifier
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.MatcherAssertionTransformer

import scala.meta.Term

private[transformers] class ScalatestTermApplyInfixToTermApplyTransformerImpl(matcherAssertionTransformer: MatcherAssertionTransformer,
                                                                              assertionWordClassifier: ScalatestAssertionWordClassifier)
  extends TermApplyInfixToTermApplyTransformer {

  override def transform(termApplyInfix: Term.ApplyInfix): Option[Term.Apply] = {
    import assertionWordClassifier._
    termApplyInfix match {
      case Term.ApplyInfix(actual, word, _, List(matcher)) if isAssertionWord(word) => matcherAssertionTransformer.transform(actual, word, matcher)
      case outerInfix@Term.ApplyInfix(innerInfix@Term.ApplyInfix(_, _, _, List(innerArg)), _, _, _) =>
        transform(rightShiftInfix(innerInfix, innerArg, outerInfix))
      case _ => None
    }
  }

  /** This method handles the case where the assertion word is nested inside an inner infix and would not be identified by the basic pattern.
   * For example, if the code is '''x should have size 2''' - then '''x should have''' will be parsed as the LHS, so that '''size''' instead of
   * '''should''' will be matched by the basic pattern.<br>
   * To resolve this - doing a "right-shift" of the word 'have' to obtain the following: '''x should (have size 2)''' -
   * after which, a recursive call to transform() will be able to recognize it with the basic pattern.
   */
  private def rightShiftInfix(inner: Term.ApplyInfix, innerArg: Term, outer: Term.ApplyInfix) = {
    inner.copy(args = List(outer.copy(lhs = innerArg)))
  }
}

object ScalatestTermApplyInfixToTermApplyTransformer extends ScalatestTermApplyInfixToTermApplyTransformerImpl(
  MatcherAssertionTransformer,
  ScalatestAssertionWordClassifier
)
