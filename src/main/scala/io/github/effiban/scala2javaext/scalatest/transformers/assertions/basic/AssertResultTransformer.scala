package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.{AssertThat, Is}

import scala.meta.Term

trait AssertResultTransformer {

  def transform(expected: Term, maybeClue: Option[Term] = None, actual: Term): Term.Apply
}

object AssertResultTransformer extends AssertResultTransformer {

  override def transform(expected: Term, maybeClue: Option[Term] = None, actual: Term): Term.Apply = maybeClue match {
    case Some(clue) => Term.Apply(AssertThat, List(clue, actual, Term.Apply(Is, List(expected))))
    case None => Term.Apply(AssertThat, List(actual, Term.Apply(Is, List(expected))))
  }
}
