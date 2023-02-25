package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.{AnyOf, HasItem, HasItems, Not}

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

object ContainNestedMatcherTransformer extends WordAndValuesMatcherTransformer {

  override protected[matchers] def transform(word: Term.Name, items: List[Term]): Option[Term] = word match {
    case q"allOf" => Some(Term.Apply(HasItems, items))
    case q"atLeastOneOf" => Some(Term.Apply(AnyOf, items.map(item => Term.Apply(HasItem, List(item)))))
    case q"noneOf" => Some(Term.Apply(Not, List(Term.Apply(HasItems, items))))
    case _ => None
  }
}
