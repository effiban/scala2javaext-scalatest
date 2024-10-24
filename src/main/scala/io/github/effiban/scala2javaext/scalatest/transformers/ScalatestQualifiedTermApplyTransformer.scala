package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.contexts.TermApplyTransformationContext
import io.github.effiban.scala2java.spi.entities.QualifiedTermApply
import io.github.effiban.scala2java.spi.transformers.QualifiedTermApplyTransformer
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic.{AssertResultTransformer, AssertThrowsTransformer, AssertTransformer, WithClueTransformer}

import scala.meta.{XtensionQuasiquoteTerm, XtensionQuasiquoteType}

class ScalatestQualifiedTermApplyTransformer(assertTransformer: AssertTransformer,
                                             assertResultTransformer: AssertResultTransformer,
                                             assertThrowsTransformer: AssertThrowsTransformer,
                                             withClueTransformer: WithClueTransformer) extends QualifiedTermApplyTransformer {

  override def transform(qualifiedTermApply: QualifiedTermApply,
                         context: TermApplyTransformationContext = TermApplyTransformationContext()): Option[QualifiedTermApply] = {
    import qualifiedTermApply._

    (context.maybeQualifierType, qualifiedName.name, typeArgs, args) match {
      case (Some(t"org.scalatest.Assertions"), q"assert", _, List(condition)) => Some(assertTransformer.transform(condition))
      case (Some(t"org.scalatest.Assertions"), q"assert", _, List(condition, clue)) => Some(assertTransformer.transform(condition, Some(clue)))
      case (Some(t"org.scalatest.Assertions"), q"assertResult", _, List(expected, actual)) =>
        Some(assertResultTransformer.transform(expected = expected, actual = actual))
      case (Some(t"org.scalatest.Assertions"), q"assertResult", _, List(expected, clue, actual)) =>
        Some(assertResultTransformer.transform(expected, Some(clue), actual))
      case (Some(t"org.scalatest.Assertions"), q"assertThrows" | q"intercept", _, List(body)) =>
        Some(assertThrowsTransformer.transform(body = body))
      case (Some(t"org.scalatest.Assertions"), q"assertThrows" | q"intercept", List(exceptionType), List(body)) =>
        Some(assertThrowsTransformer.transform(exceptionType, body))
      case (Some(t"org.scalatest.Assertions"), q"withClue", _, List(clue, body)) =>
        Some(withClueTransformer.transform(clue = clue, body = body))
      case (Some(t"org.scalatest.Assertions"), q"withClue", List(returnType), List(clue, body)) =>
        Some(withClueTransformer.transform(Some(returnType), clue, body))
      case _ => None
    }
  }
}

object ScalatestQualifiedTermApplyTransformer extends ScalatestQualifiedTermApplyTransformer(
  AssertTransformer,
  AssertResultTransformer,
  AssertThrowsTransformer,
  WithClueTransformer
)
