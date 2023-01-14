package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.{ClassTransformer, DefnDefTransformer, ExtendedTransformers, FileNameTransformer}

trait ScalaTest2JUnitTransformers extends ExtendedTransformers {

  override def fileNameTransformer(): FileNameTransformer = ScalaTest2JUnitFileNameTransformer

  override def classTransformer(): ClassTransformer = ScalaTest2JUnitClassTransformer

  override def defnDefTransformer(): DefnDefTransformer = ScalaTest2JUnitDefnDefTransformer
}
