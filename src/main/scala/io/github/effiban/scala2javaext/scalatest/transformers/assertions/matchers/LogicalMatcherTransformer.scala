package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.Not

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

private[transformers] class LogicalMatcherTransformer(nestedTransformer: => MatcherTransformer) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    matcher match {
      case Term.ApplyInfix(q"not", nestedMatcherWord, _, nestedMatcherArgs) =>
        transformNested(Term.Apply(nestedMatcherWord, nestedMatcherArgs))
          .map(hamcrestMatcher => Term.Apply(Not, List(hamcrestMatcher)))
      case _ => None
    }
  }

  private def transformNested(nestedMatcher: Term) = nestedTransformer.transform(nestedMatcher)
}
