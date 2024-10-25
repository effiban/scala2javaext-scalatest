package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.Is

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

private[transformers] class BeMatcherTransformer(nestedTransformer: MatcherTransformer) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    matcher match {
      case Term.Apply(Term.Select(_, q"be") | q"be", List(expected: Term)) =>
        transformNested(expected).orElse(Some(Term.Apply(Is, List(expected))))
      case Term.ApplyInfix(Term.Select(_, q"be") | q"be", nestedMatcherWord: Term.Name, _,  List(expected: Term)) =>
        transformNested(Term.Apply(nestedMatcherWord, List(expected)))
      case _ => None
    }
  }

  private def transformNested(nestedTerm: Term): Option[Term] = nestedTransformer.transform(nestedTerm)

}

object BeMatcherTransformer extends BeMatcherTransformer(
  new CompositeMatcherTransformer(
    List(
      OrderingMatcherTransformer,
      SameInstanceMatcherTransformer,
      ATypeMatcherTransformer,
      EmptyMatcherTransformer
    )
  )
)
