package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2java.spi.entities.QualifiedTermApply
import io.github.effiban.scala2javaext.scalatest.common.HamcrestMatcherTerms.{AssertThat, IsTrue}

import scala.meta.Term

trait AssertTransformer {

  def transform(assertion: Term, maybeClue: Option[Term] = None): QualifiedTermApply
}

object AssertTransformer extends AssertTransformer {

  override def transform(condition: Term, maybeClue: Option[Term] = None): QualifiedTermApply = maybeClue match {
    case Some(clue) => QualifiedTermApply(AssertThat, List(clue, condition, IsTrue))
    case None => QualifiedTermApply(AssertThat, List(condition, IsTrue))
  }
}
