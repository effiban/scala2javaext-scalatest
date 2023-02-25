package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.TermApplyInfixToTermApplyTransformer
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestAssertionWordClassifier
import io.github.effiban.scala2javaext.scalatest.manipulators.TermApplyInfixManipulator
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers.MatcherAssertionTransformer

import scala.meta.Term

private[transformers] class ScalatestTermApplyInfixToTermApplyTransformerImpl(matcherAssertionTransformer: MatcherAssertionTransformer,
                                                                              assertionWordClassifier: ScalatestAssertionWordClassifier,
                                                                              termApplyInfixManipulator: TermApplyInfixManipulator)
  extends TermApplyInfixToTermApplyTransformer {

  override def transform(termApplyInfix: Term.ApplyInfix): Option[Term.Apply] = {
    import assertionWordClassifier._
    import termApplyInfixManipulator._

    termApplyInfix match {
      case Term.ApplyInfix(actual, word, _, List(matcher)) if isAssertionWord(word) => matcherAssertionTransformer.transform(actual, word, matcher)
      case infix => rightShiftInnerIfPossible(infix).flatMap(transform)
    }
  }
}

object ScalatestTermApplyInfixToTermApplyTransformer extends ScalatestTermApplyInfixToTermApplyTransformerImpl(
  MatcherAssertionTransformer,
  ScalatestAssertionWordClassifier,
  TermApplyInfixManipulator
)
