package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.{GreaterThan, GreaterThanOrEqualTo, LessThan, LessThanOrEqualTo}

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

object OrderingMatcherTransformer extends WordAndValueMatcherTransformer {

  override protected[matchers] def transform(operator: Term.Name, value: Term): Option[Term] = operator match {
    case q">" => Some(Term.Apply(GreaterThan, List(value)))
    case q">=" => Some(Term.Apply(GreaterThanOrEqualTo, List(value)))
    case q"<" => Some(Term.Apply(LessThan, List(value)))
    case q"<=" => Some(Term.Apply(LessThanOrEqualTo, List(value)))
    case _ => None
  }
}
