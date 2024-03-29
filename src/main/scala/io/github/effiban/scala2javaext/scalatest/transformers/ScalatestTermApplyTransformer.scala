package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.contexts.TermApplyTransformationContext
import io.github.effiban.scala2java.spi.transformers.TermApplyTransformer
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic.{AssertResultTransformer, AssertThrowsTransformer, AssertTransformer, WithClueTransformer}

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestTermApplyTransformer(assertTransformer: AssertTransformer,
                                    assertResultTransformer: AssertResultTransformer,
                                    assertThrowsTransformer: AssertThrowsTransformer,
                                    withClueTransformer: WithClueTransformer) extends TermApplyTransformer {

  override def transform(termApply: Term.Apply, context: TermApplyTransformationContext = TermApplyTransformationContext()): Term.Apply =
    termApply match {
      case Term.Apply(q"assert", List(condition)) => assertTransformer.transform(condition)
      case Term.Apply(q"assert", List(condition, clue)) => assertTransformer.transform(condition, Some(clue))
      case Term.Apply(q"assertResult", List(expected, actual)) => assertResultTransformer.transform(expected = expected, actual = actual)
      case Term.Apply(q"assertResult", List(expected, clue, actual)) => assertResultTransformer.transform(expected, Some(clue), actual)
      case Term.Apply(q"assertThrows" | q"intercept", List(body)) => assertThrowsTransformer.transform(body = body)
      case Term.Apply(Term.ApplyType(q"assertThrows" | q"intercept", List(exceptionType)), List(body)) =>
        assertThrowsTransformer.transform(exceptionType, body)
      case Term.Apply(q"withClue", List(clue, body)) => withClueTransformer.transform(clue = clue, body = body)
      case Term.Apply(Term.ApplyType(q"withClue", List(returnType)), List(clue, body)) =>
        withClueTransformer.transform(Some(returnType), clue, body)
      case other => other
    }
}

object ScalatestTermApplyTransformer extends ScalatestTermApplyTransformer(
  AssertTransformer,
  AssertResultTransformer,
  AssertThrowsTransformer,
  WithClueTransformer
)
