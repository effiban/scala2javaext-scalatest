package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import scala.meta.Term

trait WordAndExpectedValueMatcherTransformer extends MatcherTransformer {

  override def transform(matcher: Term): Option[Term] = {
    matcher match {
      case Term.Apply(matcherFun: Term.Name, List(expected: Term)) => transform(matcherFun, expected)
      case _ => None
    }
  }

  protected[matchers] def transform(matcherFun: Term.Name, expected: Term): Option[Term]
}
