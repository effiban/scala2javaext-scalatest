package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.TermApplyTransformer

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestTermApplyTransformer(assertTransformer: AssertTransformer,
                                    assertResultTransformer: AssertResultTransformer,
                                    assertExpectedExceptionTransformer: AssertExpectedExceptionTransformer) extends TermApplyTransformer {

  override def transform(termApply: Term.Apply): Term.Apply = termApply match {
    case Term.Apply(q"assert", List(condition)) => assertTransformer.transform(condition)
    case Term.Apply(q"assert", List(condition, clue)) => assertTransformer.transform(condition, Some(clue))
    case Term.Apply(Term.Apply(q"assertResult", List(expected)), List(actual)) =>
      assertResultTransformer.transform(expected = expected, actual = actual)
    case Term.Apply(Term.Apply(q"assertResult", List(expected, clue)), List(actual)) =>
      assertResultTransformer.transform(expected, Some(clue), actual)
    case Term.Apply(Term.ApplyType(q"assertThrows", List(exceptionType)), List(body)) =>
      assertExpectedExceptionTransformer.transform(exceptionType, body)
    case Term.Apply(Term.ApplyType(q"intercept", List(exceptionType)), List(body)) =>
      assertExpectedExceptionTransformer.transform(exceptionType, body, returnException = true)
    case other => other
  }
}

object ScalatestTermApplyTransformer extends ScalatestTermApplyTransformer(
  AssertTransformer,
  AssertResultTransformer,
  AssertExpectedExceptionTransformer
)
