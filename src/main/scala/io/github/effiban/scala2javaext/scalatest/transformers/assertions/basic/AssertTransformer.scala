package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.{AssertThat, IsTrue}

import scala.meta.Term

trait AssertTransformer {

  def transform(assertion: Term, maybeClue: Option[Term] = None): Term.Apply
}

object AssertTransformer extends AssertTransformer {

  override def transform(condition: Term, maybeClue: Option[Term] = None): Term.Apply = maybeClue match {
    case Some(clue) => Term.Apply(AssertThat, List(clue, condition, IsTrue))
    case None => Term.Apply(AssertThat, List(condition, IsTrue))
  }
}
