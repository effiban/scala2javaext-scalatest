package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

private[transformers] class HaveMatcherTransformer(nestedTransformer: MatcherTransformer) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    matcher match {
      case Term.Apply(q"have", List(nestedTerm: Term)) => transformNested(nestedTerm)
      case Term.ApplyInfix(q"have", matcherWord, _, matcherArgs) => transformNested(Term.Apply(matcherWord, matcherArgs))
      case _ => None
    }
  }

  private def transformNested(nestedTerm: Term): Option[Term] = nestedTransformer.transform(nestedTerm)

}

object HaveMatcherTransformer extends HaveMatcherTransformer(
  new CompositeMatcherTransformer(
    List(
      SizeMatcherTransformer
      // TODO - add more matchers which are allowed after 'have'
    )
  )
)
