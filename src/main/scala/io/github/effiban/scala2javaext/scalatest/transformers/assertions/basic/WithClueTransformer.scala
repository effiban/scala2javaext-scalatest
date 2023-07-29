package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2javaext.scalatest.common.ScalatestConstants.Fail

import scala.meta.{Case, Pat, Term, Type, XtensionQuasiquoteCaseOrPattern, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

trait WithClueTransformer {

  def transform(maybeReturnType: Option[Type] = None, clue: Term, body: Term): Term.Apply
}

object WithClueTransformer extends WithClueTransformer {

  override def transform(maybeReturnType: Option[Type] = None, clue: Term, body: Term): Term.Apply = {
    val tryAndRecover = generateTryAndRecover(maybeReturnType, clue, body)
    if (maybeReturnType.nonEmpty) Term.Apply(Term.Select(tryAndRecover, q"get"), Nil) else tryAndRecover
  }

  private def generateTryAndRecover(maybeReturnType: Option[Type], clue: Term, body: Term) = {
    val tryInvocation = maybeReturnType match {
      case Some(returnType) => Term.Apply(Term.ApplyType(q"Try.ofSupplier", List(returnType)), List(Term.Function(Nil, body)))
      case None => Term.Apply(q"Try.of", List(Term.Function(Nil, body)))
    }

    Term.Apply(
      Term.Select(tryInvocation, q"recover"),
      List(
        Term.Function(
          List(Term.Param(Nil, q"e", None, None)),
          Term.Match(q"e",
            List(
              Case(Pat.Typed(p"ex", t"AssertionFailedError"), None, Term.Apply(Fail, List(clue, q"ex"))),
              Case(Pat.Wildcard(), None, Term.Throw(q"e"))
            )
          )
        )
      )
    )
  }
}
