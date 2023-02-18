package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.Is

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

private[transformers] class BeMatcherTransformer(nestedTransformer: MatcherTransformer) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    matcher match {
      case Term.Apply(q"be", List(nestedTerm: Term)) => transformNested(nestedTerm).orElse(Some(Term.Apply(Is, List(nestedTerm))))
      case _ => None
    }
  }

  private def transformNested(nestedTerm: Term): Option[Term] = nestedTransformer.transform(nestedTerm)

}

object BeMatcherTransformer extends BeMatcherTransformer(
  new CompositeMatcherTransformer(
    List(
      // TODO - add matchers which are allowed after 'be'
    )
  )
)
