package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

object EmptyMatcherTransformer extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = matcher match {
    case q"empty" => Some(Term.Apply(HamcrestMatcherTerms.Empty, Nil))
    case _ => None
  }
}
