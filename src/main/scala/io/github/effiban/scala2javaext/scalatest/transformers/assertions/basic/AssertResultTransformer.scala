package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2javaext.scalatest.common.JUnitConstants.{HamcrestAssertThat, HamcrestIs}

import scala.meta.Term

trait AssertResultTransformer {

  def transform(expected: Term, maybeClue: Option[Term] = None, actual: Term): Term.Apply
}

object AssertResultTransformer extends AssertResultTransformer {

  override def transform(expected: Term, maybeClue: Option[Term] = None, actual: Term): Term.Apply = maybeClue match {
    case Some(clue) => Term.Apply(HamcrestAssertThat, List(clue, actual, Term.Apply(HamcrestIs, List(expected))))
    case None => Term.Apply(HamcrestAssertThat, List(actual, Term.Apply(HamcrestIs, List(expected))))
  }
}
