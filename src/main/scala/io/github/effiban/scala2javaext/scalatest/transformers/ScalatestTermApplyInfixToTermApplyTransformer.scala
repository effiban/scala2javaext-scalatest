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
      case _ => None
    }
  }
}

object ScalatestTermApplyInfixToTermApplyTransformer extends ScalatestTermApplyInfixToTermApplyTransformerImpl(
  MatcherAssertionTransformer,
  ScalatestAssertionWordClassifier
)
