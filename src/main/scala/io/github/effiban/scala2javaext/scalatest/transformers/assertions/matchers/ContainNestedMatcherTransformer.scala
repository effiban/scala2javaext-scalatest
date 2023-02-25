package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms._

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

object ContainNestedMatcherTransformer extends WordAndValuesMatcherTransformer {

  override protected[matchers] def transform(word: Term.Name, values: List[Term]): Option[Term] = (word, values) match {
    case (q"allOf", items) => Some(Term.Apply(HasItems, items))
    case (q"atLeastOneOf", items) => Some(Term.Apply(AnyOf, items.map(item => Term.Apply(HasItem, List(item)))))
    case (q"theSameElementsAs", collection :: Nil) => Some(Term.Apply(ContainsInAnyOrder, List(toHamcrestIsPerItem(collection))))
    case (q"noneOf", items) => Some(Term.Apply(Not, List(Term.Apply(HasItems, items))))
    case _ => None
  }

  private def toHamcrestIsPerItem(collection: Term): Term.Apply = {
    Term.Apply(Term.Select(collection, q"map"), List(q"item => is(item)"))
  }
}
