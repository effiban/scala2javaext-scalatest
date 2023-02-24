package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

object ContainNestedMatcherTransformer extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = matcher match {
    case Term.Apply(q"atLeastOneOf", expectedElems) => Some(Term.Apply(HamcrestMatcherTerms.HasItems, expectedElems))
    case _ => None
  }
}
