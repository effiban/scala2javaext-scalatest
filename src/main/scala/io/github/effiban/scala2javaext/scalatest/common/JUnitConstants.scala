package io.github.effiban.scala2javaext.scalatest.common

import scala.meta.{Type, XtensionQuasiquoteType}

object JUnitConstants {

  val TestClassSuffix: String = "Test"

  val TestAnnotationType: Type.Name = t"Test"
  val NestedAnnotationType: Type.Name = t"Nested"
  val DisplayNameAnnotationType: Type.Name = t"DisplayName"
  val TagAnnotationType: Type.Name = t"Tag"
}
