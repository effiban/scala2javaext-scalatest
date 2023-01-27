package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.{DifferentTypeTransformer0, TemplateTermApplyToDefnTransformer}

import scala.meta.{Defn, Term}

object ScalatestTemplateTermApplyToDefnTransformer extends CompositeTermToDefnTransformer[Term.Apply] with TemplateTermApplyToDefnTransformer {

  override protected val transformers: List[DifferentTypeTransformer0[Term.Apply, Defn]] =
    List(
      TermApplyRegistrationTransformer,
      TermApplyNestedRegistrationTransformer
    )
}
