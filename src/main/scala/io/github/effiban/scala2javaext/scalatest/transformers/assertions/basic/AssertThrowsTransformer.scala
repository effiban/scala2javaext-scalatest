package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2java.spi.entities.QualifiedTermApply
import io.github.effiban.scala2javaext.scalatest.common.JUnitConstants.AssertThrows

import scala.meta.{Term, Type, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

trait AssertThrowsTransformer {

  def transform(exceptionType: Type = t"java.lang.Throwable", body: Term): QualifiedTermApply
}

object AssertThrowsTransformer extends AssertThrowsTransformer {

  override def transform(exceptionType: Type = t"java.lang.Throwable", body: Term): QualifiedTermApply = {
    val exceptionClass = Term.ApplyType(q"classOf", List(exceptionType))
    QualifiedTermApply(AssertThrows, List(exceptionClass, Term.Function(Nil, body)))
  }
}
