package io.github.effiban.scala2javaext.scalatest.common

import scala.meta.{Term, Type, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

object JUnitConstants {

  val TestClassSuffix: String = "Test"

  val TestAnnotationType: Type.Name = t"Test"
  val NestedAnnotationType: Type.Name = t"Nested"
  val DisplayNameAnnotationType: Type.Name = t"DisplayName"
  val TagAnnotationType: Type.Name = t"Tag"
  val DisabledAnnotationType: Type.Name = t"Disabled"

  val HamcrestAssertThat: Term.Name = q"assertThat"
  val HamcrestIsTrue: Term.Apply = q"is(true)"
  val HamcrestIs: Term.Name = q"is"
}
