package io.github.effiban.scala2javaext.scalatest

import io.github.effiban.scala2java.spi.Scala2JavaExtension
import io.github.effiban.scala2javaext.scalatest.predicates.ScalatestPredicates
import io.github.effiban.scala2javaext.scalatest.transformers.ScalatestTransformers

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestExtension extends Scala2JavaExtension
  with ScalatestPredicates
  with ScalatestTransformers {

  override def shouldBeAppliedIfContains(termSelect: Term.Select): Boolean =
    q"org.scalatest".structure == termSelect.structure
}
