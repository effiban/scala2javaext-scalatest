package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestMatcherWordClassifier
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.{HasSize, Is}

import scala.meta.Term

private[transformers] class SizeMatcherTransformer(matcherWordClassifier: ScalatestMatcherWordClassifier) extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    import matcherWordClassifier._
    matcher match {
      case Term.Apply(Term.Select(_, word: Term.Name), List(expected)) if isSizeWord(word) => Some(Term.Apply(HasSize, List(expected)))
      case Term.Apply(word : Term.Name, List(expected)) if isSizeWord(word) => Some(Term.Apply(HasSize, List(expected)))
      case _ => None
    }
  }
}

object SizeMatcherTransformer extends SizeMatcherTransformer(ScalatestMatcherWordClassifier)
