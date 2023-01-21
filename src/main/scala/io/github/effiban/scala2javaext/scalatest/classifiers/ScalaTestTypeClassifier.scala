package io.github.effiban.scala2javaext.scalatest.classifiers

import io.github.effiban.scala2javaext.scalatest.common.ScalaTestConstants.TestSuperclasses

import scala.meta.Type

trait ScalaTestTypeClassifier {
  def isTestSuperclass(tpe: Type): Boolean
}

object ScalaTestTypeClassifier extends ScalaTestTypeClassifier {

  private val allTestSuperclasses = TestSuperclasses ++ TestSuperclasses.map(_.name)

  override def isTestSuperclass(tpe: Type): Boolean = {
    allTestSuperclasses.exists(_.structure == tpe.structure)
  }
}
