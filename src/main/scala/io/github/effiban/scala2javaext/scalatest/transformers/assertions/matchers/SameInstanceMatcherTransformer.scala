package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.SameInstance

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

object SameInstanceMatcherTransformer extends WordAndValueMatcherTransformer {

  override protected[matchers] def transform(word: Term.Name, otherInstance: Term): Option[Term] = word match {
    case q"theSameInstanceAs" => Some(Term.Apply(SameInstance, List(otherInstance)))
    case _ => None
  }
}
