package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2java.spi.entities.QualifiedTermApply
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.{AssertThat, Is}

import scala.meta.Term

trait AssertResultTransformer {

  def transform(expected: Term, maybeClue: Option[Term] = None, actual: Term): QualifiedTermApply
}

object AssertResultTransformer extends AssertResultTransformer {

  override def transform(expected: Term, maybeClue: Option[Term] = None, actual: Term): QualifiedTermApply = maybeClue match {
    case Some(clue) => QualifiedTermApply(AssertThat, List(clue, actual, Term.Apply(Is, List(expected))))
    case None => QualifiedTermApply(AssertThat, List(actual, Term.Apply(Is, List(expected))))
  }
}
