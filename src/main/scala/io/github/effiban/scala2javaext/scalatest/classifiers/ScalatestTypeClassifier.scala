package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.common.ScalatestConstants.TestSuperclasses

import scala.meta.Type

trait ScalatestTypeClassifier {
  def isTestSuperclass(tpe: Type): Boolean
}

object ScalatestTypeClassifier extends ScalatestTypeClassifier {

  private val allTestSuperclasses = TestSuperclasses ++ TestSuperclasses.map(_.name)

  override def isTestSuperclass(tpe: Type): Boolean = {
    allTestSuperclasses.exists(_.structure == tpe.structure)
  }
}
