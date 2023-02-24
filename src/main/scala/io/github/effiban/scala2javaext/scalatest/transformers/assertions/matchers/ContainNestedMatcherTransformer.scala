package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.{AnyOf, HasItem, HasItems, Not}

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

object ContainNestedMatcherTransformer extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = matcher match {
    case Term.Apply(q"atLeastOneOf", elems) => Some(Term.Apply(AnyOf, elems.map(elem => Term.Apply(HasItem, List(elem)))))
    case Term.Apply(q"noneOf", elems) => Some(Term.Apply(Not, List(Term.Apply(HasItems, elems))))
    case _ => None
  }
}
