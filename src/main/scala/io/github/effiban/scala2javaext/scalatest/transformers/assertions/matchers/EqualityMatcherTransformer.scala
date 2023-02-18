package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherWordClassifier
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.Is

import scala.meta.Term

private[transformers] class EqualityMatcherTransformer(matcherWordClassifier: ScalatestMatcherWordClassifier) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    import matcherWordClassifier._
    matcher match {
      case Term.Apply(word: Term.Name, List(expected)) if isEqualsWord(word) => Some(Term.Apply(Is, List(expected)))
      case _ => None
    }
  }
}

object EqualityMatcherTransformer extends EqualityMatcherTransformer(ScalatestMatcherWordClassifier)
