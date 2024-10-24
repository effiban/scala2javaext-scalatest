package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import scala.meta.Term

trait WordAndValueMatcherTransformer extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    matcher match {
      case Term.Apply(Term.Select(_, word: Term.Name), List(value: Term)) => transform(word, value)
      case Term.Apply(word: Term.Name, List(value: Term)) => transform(word, value)
      case _ => None
    }
  }

  protected[matchers] def transform(word: Term.Name, value: Term): Option[Term]
}
