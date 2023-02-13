package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.common.ScalatestConstants.Fail

import scala.meta.Term.Block
import scala.meta.{Case, Lit, Pat, Term, Type, XtensionQuasiquoteCaseOrPattern, XtensionQuasiquoteTerm}

trait AssertExpectedExceptionTransformer {

  def transform(exceptionType: Type, body: Term, returnException: Boolean = false): Term.Apply
}

object AssertExpectedExceptionTransformer extends AssertExpectedExceptionTransformer {

  override def transform(exceptionType: Type, body: Term, returnException: Boolean = false): Term.Apply = {
    val tryAndRecover = generateTryAndRecover(exceptionType, body, returnException)
   if (returnException) Term.Apply(Term.Select(tryAndRecover, q"get"), Nil) else tryAndRecover
  }

  private def generateTryAndRecover(exceptionType: Type, body: Term, returnException: Boolean) = {
    val expectedExceptionFailure = Term.Apply(Fail, List(Lit.String(s"Should have thrown an $exceptionType")))
    val adjustedBody = body match {
      case block: Block => Block(block.stats :+ expectedExceptionFailure)
      case term => Block(List(term, expectedExceptionFailure))
    }
    val exceptionArg = q"e"
    val exceptionPat = if (returnException) p"ex" else Pat.Wildcard()
    val exceptionParam = Term.Param(Nil, exceptionArg, None, None)
    val output = if (returnException) q"ex" else Block(Nil)

    Term.Apply(
      Term.Select(Term.Apply(q"Try", List(adjustedBody)), q"recover"),
      List(
        Term.Function(
          List(exceptionParam),
          Term.Match(
            exceptionArg,
            List(
              Case(pat = Pat.Typed(exceptionPat, exceptionType), cond = None, body = output),
              Case(pat = Pat.Wildcard(), cond = None, body = expectedExceptionFailure)
            )
          )
        )
      )
    )
  }
}
