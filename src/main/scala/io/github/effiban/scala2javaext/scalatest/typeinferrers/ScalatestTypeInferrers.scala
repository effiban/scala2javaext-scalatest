package io.github.effiban.scala2javaext.scalatest.typeinferrers

import io.github.effiban.scala2java.spi.typeinferrers.{ExtendedTypeInferrers, SelectTypeInferrer}

trait ScalatestTypeInferrers extends ExtendedTypeInferrers {

  override def selectTypeInferrer(): SelectTypeInferrer = ScalatestSelectTypeInferrer
}
