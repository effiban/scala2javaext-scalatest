package io.github.effiban.scala2javaext.scalatest.typeinferrers

import io.github.effiban.scala2java.spi.typeinferrers.{ExtendedTypeInferrers, NameTypeInferrer}

trait ScalatestTypeInferrers extends ExtendedTypeInferrers {

  override def nameTypeInferrer(): NameTypeInferrer = ScalatestNameTypeInferrer
}
