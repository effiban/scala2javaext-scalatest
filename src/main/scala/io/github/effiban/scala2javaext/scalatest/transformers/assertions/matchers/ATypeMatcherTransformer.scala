package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherWordClassifier
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.{IsA, SameInstance}

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

private[matchers] class ATypeMatcherTransformer(matcherWordClassifier: ScalatestMatcherWordClassifier) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    import matcherWordClassifier._
    matcher match {
      case Term.ApplyType(word: Term.Name, List(tpe)) if isATypeWord(word) => Some(Term.Apply(IsA, List(Term.ApplyType(q"classOf", List(tpe)))))
      case _ => None
    }
  }
}

object ATypeMatcherTransformer extends ATypeMatcherTransformer(ScalatestMatcherWordClassifier)
