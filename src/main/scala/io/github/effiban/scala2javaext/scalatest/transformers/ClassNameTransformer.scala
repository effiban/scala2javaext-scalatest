package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.SameTypeTransformer1
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTypeClassifier
import io.github.effiban.scala2javaext.scalatest.common.JUnitConstants
import io.github.effiban.scala2javaext.scalatest.common.ScalatestConstants

import scala.meta.Init

private[transformers] trait ClassNameTransformer extends SameTypeTransformer1[String, List[Init]]

private[transformers] class ClassNameTransformerImpl(scalaTestTypeClassifier: ScalatestTypeClassifier)
  extends ClassNameTransformer {

  override def transform(className: String, classInits: List[Init]): String = {
    if (includesScalaTestTestSuperclass(classInits)) {
      appendJUnitTestClassSuffix(stripScalaTestTestClassSuffix(className))
    } else {
      className
    }
  }

  private def includesScalaTestTestSuperclass(mainClassInits: List[Init]) = {
    mainClassInits
      .map(_.tpe)
      .exists(scalaTestTypeClassifier.isTestSuperclass)
  }

  private def stripScalaTestTestClassSuffix(fileName: String) = {
    ScalatestConstants.TestClassSuffixes.find(fileName.endsWith)
      .map(scalaTestTestClassSuffix => s"${fileName.stripSuffix(scalaTestTestClassSuffix)}")
      .getOrElse(fileName)
  }

  private def appendJUnitTestClassSuffix(fileName: String) = s"${fileName.stripSuffix(JUnitConstants.TestClassSuffix)}${JUnitConstants.TestClassSuffix}"
}

object ClassNameTransformer extends ClassNameTransformerImpl(ScalatestTypeClassifier)
