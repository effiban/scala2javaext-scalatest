package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

object StringMatcherTransformer extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    matcher match {
      case Term.Apply(word: Term.Name, List(expected: Term)) => transform(word, expected)
      case _ => None
    }
  }

  private def transform(matcherWord: Term.Name, expected: Term) = matcherWord match {
    case q"startWith" => Some(Term.Apply(HamcrestMatcherTerms.StartsWith, List(expected)))
    case q"endWith" => Some(Term.Apply(HamcrestMatcherTerms.EndsWith, List(expected)))
    case q"include" => Some(Term.Apply(HamcrestMatcherTerms.ContainsString, List(expected)))
    case _ => None
  }
}
