package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherOperatorClassifier
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.Is

import scala.meta.Term

private[transformers] class EqualityMatcherTransformer(operatorClassifier: ScalatestMatcherOperatorClassifier) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    import operatorClassifier._
    matcher match {
      case Term.Apply(operator: Term.Name, List(expected)) if isEquality(operator) => Some(Term.Apply(Is, List(expected)))
      case _ => None
    }
  }
}

object EqualityMatcherTransformer extends EqualityMatcherTransformer(ScalatestMatcherOperatorClassifier)
