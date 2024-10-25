package io.github.effiban.scala2javaext.scalatest.common

import scala.meta.{Term, Type, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

object JUnitConstants {

  val TestClassSuffix: String = "Test"

  val TestAnnotationType: Type.Select = t"org.junit.jupiter.api.Test"
  val NestedAnnotationType: Type.Select = t"org.junit.jupiter.api.Nested"
  val DisplayNameAnnotationType: Type.Select = t"org.junit.jupiter.api.DisplayName"
  val TagAnnotationType: Type.Select = t"org.junit.jupiter.api.Tag"
  val DisabledAnnotationType: Type.Select = t"org.junit.jupiter.api.Disabled"

  val AssertThrows: Term.Select = q"org.junit.jupiter.api.Assertions.assertThrows"
  val Fail: Term.Select = q"org.junit.jupiter.api.Assertions.fail"
}
