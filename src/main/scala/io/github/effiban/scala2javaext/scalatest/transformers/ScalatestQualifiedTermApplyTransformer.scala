package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.contexts.TermApplyTransformationContext
import io.github.effiban.scala2java.spi.entities.QualifiedTermApply
import io.github.effiban.scala2java.spi.transformers.QualifiedTermApplyTransformer
import io.github.effiban.scala2javaext.scalatest.common.ScalatestConstants.AssertionsType
import io.github.effiban.scala2javaext.scalatest.transformers.assertions.basic.{AssertResultTransformer, AssertThrowsTransformer, AssertTransformer, WithClueTransformer}

import scala.meta.XtensionQuasiquoteTerm

class ScalatestQualifiedTermApplyTransformer(assertTransformer: AssertTransformer,
                                             assertResultTransformer: AssertResultTransformer,
                                             assertThrowsTransformer: AssertThrowsTransformer,
                                             withClueTransformer: WithClueTransformer) extends QualifiedTermApplyTransformer {

  override def transform(qualifiedTermApply: QualifiedTermApply,
                         context: TermApplyTransformationContext = TermApplyTransformationContext()): Option[QualifiedTermApply] = {
    import qualifiedTermApply._

    val maybeResolvedQualifierType = (context.maybeQualifierType, qualifiedName.qual) match {
      case (Some(qualifierType), _) => Some(qualifierType)
      case (_, q"org.scalatest.Assertions") => Some(AssertionsType)
      case _ => None
    }
    
    (maybeResolvedQualifierType, qualifiedName.name, typeArgs, args) match {
      case (Some(AssertionsType), q"assert", _, List(cond)) => Some(assertTransformer.transform(cond))
      case (Some(AssertionsType), q"assert", _, List(cond, clue)) => Some(assertTransformer.transform(cond, Some(clue)))
      
      case (Some(AssertionsType), q"assertResult", _, List(expected, actual)) =>
        Some(assertResultTransformer.transform(expected = expected, actual = actual))
      case (Some(AssertionsType), q"assertResult", _, List(expected, clue, actual)) =>
        Some(assertResultTransformer.transform(expected, Some(clue), actual))
      
      case (Some(AssertionsType), q"assertThrows" | q"intercept", Nil, List(body)) =>
        Some(assertThrowsTransformer.transform(body = body))
      case (Some(AssertionsType), q"assertThrows" | q"intercept", List(exceptionType), List(body)) =>
        Some(assertThrowsTransformer.transform(exceptionType, body))
      
      case (Some(AssertionsType), q"withClue", Nil, List(clue, body)) =>
        Some(withClueTransformer.transform(clue = clue, body = body))
      case (Some(AssertionsType), q"withClue", List(returnType), List(clue, body)) =>
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
