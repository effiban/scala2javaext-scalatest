package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import scala.meta.{Term, Type, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

trait AssertThrowsTransformer {

  def transform(exceptionType: Type = t"Throwable", body: Term): Term.Apply
}

object AssertThrowsTransformer extends AssertThrowsTransformer {

  override def transform(exceptionType: Type = t"Throwable", body: Term): Term.Apply = {
    val exceptionClass = Term.ApplyType(q"classOf", List(exceptionType))
    Term.Apply(q"assertThrows", List(exceptionClass, Term.Function(Nil, body)))
  }
}
