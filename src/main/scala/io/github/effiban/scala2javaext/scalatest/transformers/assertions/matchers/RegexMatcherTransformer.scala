package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.{MatchesPattern, MatchesRegex}

import scala.meta.quasiquotes.XtensionQuasiquoteTerm
import scala.meta.{Lit, Term}

object RegexMatcherTransformer extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    matcher match {
      case Term.ApplyInfix(Term.Select(_, matcherWord: Term.Name), q"regex", _, List(regex: Lit.String)) => transform(matcherWord, regex)
      case _ => None
    }
  }

  private def transform(matcherWord: Term.Name, regex: Lit.String) = {
    matcherWord match {
      case q"startWith" => Some(Term.Apply(MatchesRegex, List(Lit.String(s"^${regex.value}"))))
      case q"endWith" => Some(Term.Apply(MatchesRegex, List(Lit.String(s"${regex.value}$$"))))
      case q"include" => Some(Term.Apply(MatchesRegex, List(Lit.String(s".*${regex.value}.*"))))
      case q"fullyMatch" => Some(Term.Apply(MatchesPattern, List(regex)))
      case _ => None
    }
  }
}
