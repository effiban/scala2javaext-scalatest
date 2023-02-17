package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.common.JUnitConstants.{HamcrestAssertThat, HamcrestIs}

import scala.meta.Term

private[transformers] class EqualityMatcherTransformer(termNameClassifier: ScalatestTermNameClassifier) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    import termNameClassifier._
    matcher match {
      case Term.Apply(operator: Term.Name, List(expected)) if isEqualityOperator(operator) => Some(Term.Apply(HamcrestIs, List(expected)))
      case _ => None
    }
  }
}

object EqualityMatcherTransformer extends EqualityMatcherTransformer(ScalatestTermNameClassifier)
