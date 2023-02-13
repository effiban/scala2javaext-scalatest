package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.common.ScalatestConstants

import scala.meta.Term.Block
import scala.meta.{Case, Pat, Term, Type, XtensionQuasiquoteTerm}

trait AssertThrowsTransformer {

  def transform(exceptionType: Type, body: Term): Term.Apply
}

object AssertThrowsTransformer extends AssertThrowsTransformer {

  override def transform(exceptionType: Type, body: Term): Term.Apply = {
    val failWithoutMessage = Term.Apply(ScalatestConstants.Fail, Nil)
    val adjustedBody = body match {
      case block: Block => Block(block.stats :+ failWithoutMessage)
      case term => Block(List(term, failWithoutMessage))
    }
    Term.Apply(
      Term.Select(Term.Apply(q"Try", List(adjustedBody)), q"recover"),
      List(Term.PartialFunction(
        List(Case(pat = Pat.Typed(Pat.Wildcard(), exceptionType), cond = None, body = Block(Nil))))
      )
    )
  }

}
