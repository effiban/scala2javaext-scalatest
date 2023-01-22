package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers._

trait ScalatestTransformers extends ExtendedTransformers {

  override def fileNameTransformer(): FileNameTransformer = ScalatestFileNameTransformer

  override def classTransformer(): ClassTransformer = ScalatestClassTransformer

  override def defnDefTransformer(): DefnDefTransformer = ScalatestDefnDefTransformer

  override def templateTermApplyInfixToDefnTransformer(): TemplateTermApplyInfixToDefnTransformer = ScalatestTemplateTermApplyInfixToDefnTransformer

  override def templateTermApplyToDefnTransformer(): TemplateTermApplyToDefnTransformer = ScalatestTemplateTermApplyToDefnTransformer
}
