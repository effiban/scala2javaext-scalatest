package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers._

trait ScalatestTransformers extends ExtendedTransformers {

  override def classTransformer(): ClassTransformer = ScalatestClassTransformer

  override def defnDefTransformer(): DefnDefTransformer = ScalatestDefnDefTransformer

  override def templateTermApplyInfixToDefnTransformer(): TemplateTermApplyInfixToDefnTransformer = ScalatestTemplateTermApplyInfixToDefnTransformer

  override def templateTermApplyToDefnTransformer(): TemplateTermApplyToDefnTransformer = ScalatestTemplateTermApplyToDefnTransformer

  override def termApplyInfixToTermApplyTransformer(): TermApplyInfixToTermApplyTransformer = ScalatestTermApplyInfixToTermApplyTransformer

  override def qualifiedTermApplyTransformer(): QualifiedTermApplyTransformer = ScalatestQualifiedTermApplyTransformer
}
