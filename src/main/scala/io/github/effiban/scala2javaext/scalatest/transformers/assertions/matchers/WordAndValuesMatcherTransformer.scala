package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import scala.meta.Term

trait WordAndValuesMatcherTransformer extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    matcher match {
      case Term.Apply(word: Term.Name, values: List[Term]) => transform(word, values)
      case _ => None
    }
  }

  protected[matchers] def transform(word: Term.Name, expectedValues: List[Term]): Option[Term]
}
