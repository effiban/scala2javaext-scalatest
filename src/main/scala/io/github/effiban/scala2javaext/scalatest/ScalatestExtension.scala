package io.github.effiban.scala2javaext.scalatest

import io.github.effiban.scala2java.spi.Scala2JavaExtension
import io.github.effiban.scala2javaext.scalatest.predicates.ScalatestPredicates
import io.github.effiban.scala2javaext.scalatest.providers.JUnitProviders
import io.github.effiban.scala2javaext.scalatest.transformers.ScalatestTransformers
import io.github.effiban.scala2javaext.scalatest.typeinferrers.ScalatestTypeInferrers

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalatestExtension extends Scala2JavaExtension
  with ScalatestPredicates
  with JUnitProviders
  with ScalatestTransformers
  with ScalatestTypeInferrers {

  override def shouldBeAppliedIfContains(termSelect: Term.Select): Boolean =
    q"org.scalatest".structure == termSelect.structure
}
