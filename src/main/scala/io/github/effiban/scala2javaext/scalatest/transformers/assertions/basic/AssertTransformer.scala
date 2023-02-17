package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2javaext.scalatest.common.JUnitConstants.{HamcrestAssertThat, HamcrestIsTrue}

import scala.meta.Term

trait AssertTransformer {

  def transform(assertion: Term, maybeClue: Option[Term] = None): Term.Apply
}

object AssertTransformer extends AssertTransformer {

  override def transform(condition: Term, maybeClue: Option[Term] = None): Term.Apply = maybeClue match {
    case Some(clue) => Term.Apply(HamcrestAssertThat, List(clue, condition, HamcrestIsTrue))
    case None => Term.Apply(HamcrestAssertThat, List(condition, HamcrestIsTrue))
  }
}
