package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.{GreaterThan, GreaterThanOrEqualTo, LessThan, LessThanOrEqualTo}

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

object OrderingMatcherTransformer extends WordAndExpectedValueMatcherTransformer {

  override protected[matchers] def transform(operator: Term.Name, expected: Term): Option[Term] = operator match {
    case q">" => Some(Term.Apply(GreaterThan, List(expected)))
    case q">=" => Some(Term.Apply(GreaterThanOrEqualTo, List(expected)))
    case q"<" => Some(Term.Apply(LessThan, List(expected)))
    case q"<=" => Some(Term.Apply(LessThanOrEqualTo, List(expected)))
    case _ => None
  }
}
