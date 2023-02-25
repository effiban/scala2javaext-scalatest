package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

object StringMatcherTransformer extends WordAndValueMatcherTransformer {

  override protected[matchers] def transform(matcherWord: Term.Name, str: Term): Option[Term] = matcherWord match {
    case q"startWith" => Some(Term.Apply(HamcrestMatcherTerms.StartsWith, List(str)))
    case q"endWith" => Some(Term.Apply(HamcrestMatcherTerms.EndsWith, List(str)))
    case q"include" => Some(Term.Apply(HamcrestMatcherTerms.ContainsString, List(str)))
    case _ => None
  }
}
