package io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic

import io.github.effiban.scala2java.spi.entities.QualifiedTermApply
import io.github.effiban.scala2javaext.scalatest.common.JUnitConstants.Fail

import scala.meta.{Case, Pat, Term, Type, XtensionQuasiquoteCaseOrPattern, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

trait WithClueTransformer {

  def transform(maybeReturnType: Option[Type] = None, clue: Term, body: Term): QualifiedTermApply
}

object WithClueTransformer extends WithClueTransformer {

  override def transform(maybeReturnType: Option[Type] = None, clue: Term, body: Term): QualifiedTermApply = {
    val tryAndRecover = generateTryAndRecover(maybeReturnType, clue, body)
    if (maybeReturnType.nonEmpty) QualifiedTermApply(Term.Select(tryAndRecover.asTermApply(), q"get"), Nil) else tryAndRecover
  }

  private def generateTryAndRecover(maybeReturnType: Option[Type], clue: Term, body: Term) = {
    val tryInvocation = maybeReturnType match {
      case Some(returnType) => QualifiedTermApply(
        q"io.vavr.control.Try.ofSupplier",
        List(returnType),
        List(Term.Function(Nil, body))
      )
      case None => QualifiedTermApply(q"io.vavr.control.Try.of", List(Term.Function(Nil, body)))
    }

    QualifiedTermApply(
      Term.Select(tryInvocation.asTermApply(), q"recover"),
      List(
        Term.Function(
          List(Term.Param(Nil, q"e", None, None)),
          Term.Match(q"e",
            List(
              Case(Pat.Typed(p"ex", t"org.opentest4j.AssertionFailedError"), None, Term.Apply(Fail, List(clue, q"ex"))),
              Case(Pat.Wildcard(), None, Term.Throw(q"e"))
            )
          )
        )
      )
    )
  }
}
