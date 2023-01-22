package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.FileNameTransformer

import scala.meta.Init

private[transformers] class ScalatestFileNameTransformer(classNameTransformer: ClassNameTransformer)
  extends FileNameTransformer {

  override def transform(fileName: String, mainClassInits: List[Init]): String = classNameTransformer.transform(fileName, mainClassInits)
}

object ScalatestFileNameTransformer extends ScalatestFileNameTransformer(ClassNameTransformer)
