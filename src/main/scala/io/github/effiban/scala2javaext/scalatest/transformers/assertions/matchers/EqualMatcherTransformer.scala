package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherWordClassifier
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.Is

import scala.meta.Term

private[transformers] class EqualMatcherTransformer(matcherWordClassifier: ScalatestMatcherWordClassifier) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    import matcherWordClassifier._
    matcher match {
      case Term.Apply(word: Term.Name, List(expected)) if isEqualWord(word) => Some(Term.Apply(Is, List(expected)))
      case _ => None
    }
  }
}

object EqualMatcherTransformer extends EqualMatcherTransformer(ScalatestMatcherWordClassifier)
