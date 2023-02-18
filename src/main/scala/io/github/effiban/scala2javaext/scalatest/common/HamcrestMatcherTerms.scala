package io.github.effiban.scala2javaext.scalatest.common

import scala.meta.{Term, XtensionQuasiquoteTerm}

object HamcrestMatcherTerms {

  val AssertThat: Term.Name = q"assertThat"
  val IsTrue: Term.Apply = q"is(true)"
  val Is: Term.Name = q"is"
}
