package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.{DifferentTypeTransformer0, TemplateTermApplyInfixToDefnTransformer}

import scala.meta.{Defn, Term}

object ScalatestTemplateTermApplyInfixToDefnTransformer extends CompositeTermToDefnTransformer[Term.ApplyInfix]
  with TemplateTermApplyInfixToDefnTransformer {

  override protected val transformers: List[DifferentTypeTransformer0[Term.ApplyInfix, Defn]] =
    List(
      TermApplyInfixTestRegistrationTransformer
    )
}
