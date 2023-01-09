package io.github.effiban.scala2javaext.scalatest

import io.github.effiban.scala2java.spi.Scala2JavaExtension

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestExtension extends Scala2JavaExtension {

  override def shouldBeAppliedIfContains(termSelect: Term.Select): Boolean =
    q"org.scalatest".structure == termSelect.structure
}
