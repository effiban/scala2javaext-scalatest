package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.HasItem

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

private[transformers] class ContainMatcherTransformer(nestedTransformer: MatcherTransformer) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    matcher match {
      case Term.Apply(q"contain", List(expected: Term)) => Some(Term.Apply(HasItem, List(expected)))
      case Term.ApplyInfix(q"contain", nestedMatcherWord: Term.Name, _, expectedElems) => transformNested(Term.Apply(nestedMatcherWord, expectedElems))
      case _ => None
    }
  }

  private def transformNested(nestedTerm: Term): Option[Term] = nestedTransformer.transform(nestedTerm)
}

object ContainMatcherTransformer extends ContainMatcherTransformer(ContainNestedMatcherTransformer)
