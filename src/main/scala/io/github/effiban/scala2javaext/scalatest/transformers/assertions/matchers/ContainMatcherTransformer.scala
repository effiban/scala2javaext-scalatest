package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.HasItem

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

private[transformers] class ContainMatcherTransformer(nestedTransformer: MatcherTransformer) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    matcher match {
      case Term.Apply(Term.Select(_, q"contain") | q"contain", List(term: Term)) =>
        Some(transformNested(term).getOrElse(Term.Apply(HasItem, List(term))))
      case Term.ApplyInfix(Term.Select(_, q"contain") | q"contain", nestedMatcherWord: Term.Name, _, values) =>
        transformNested(Term.Apply(nestedMatcherWord, values))
      case _ => None
    }
  }

  private def transformNested(nestedTerm: Term): Option[Term] = nestedTransformer.transform(nestedTerm)
}

object ContainMatcherTransformer extends ContainMatcherTransformer(ContainNestedMatcherTransformer)
