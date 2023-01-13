package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.FileNameTransformer
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalaTestTypeClassifier
import io.github.effiban.scala2javaext.scalatest.common.JUnitConstants.JUnitTestClassSuffix
import io.github.effiban.scala2javaext.scalatest.common.ScalaTestConstants.ScalaTestTestClassSuffixes

import scala.meta.Init

class ScalaTest2JUnitFileNameTransformer(scalaTestTypeClassifier: ScalaTestTypeClassifier) extends FileNameTransformer {

  override def transform(fileName: String, mainClassInits: List[Init]): String = {
    if (includesScalaTestTestSuperclass(mainClassInits)) {
      appendJUnitTestClassSuffix(stripScalaTestTestClassSuffix(fileName))
    } else {
      fileName
    }
  }

  private def includesScalaTestTestSuperclass(mainClassInits: List[Init]) = {
    mainClassInits
      .map(_.tpe)
      .exists(scalaTestTypeClassifier.isTestSuperclass)
  }

  private def stripScalaTestTestClassSuffix(fileName: String) = {
    ScalaTestTestClassSuffixes.find(fileName.endsWith)
      .map(scalaTestTestClassSuffix => s"${fileName.stripSuffix(scalaTestTestClassSuffix)}")
      .getOrElse(fileName)
  }

  private def appendJUnitTestClassSuffix(fileName: String) = s"${fileName.stripSuffix(JUnitTestClassSuffix)}$JUnitTestClassSuffix"
}

object ScalaTest2JUnitFileNameTransformer extends ScalaTest2JUnitFileNameTransformer(ScalaTestTypeClassifier)
