package io.github.effiban.scala2javaext.scalatest

import io.github.effiban.scala2java.spi.Scala2JavaExtension
import io.github.effiban.scala2javaext.scalatest.predicates.ScalaTest2JUnitPredicates
import io.github.effiban.scala2javaext.scalatest.providers.ScalaTest2JUnitProviders
import io.github.effiban.scala2javaext.scalatest.transformers.ScalaTest2JUnitTransformers

import scala.meta.{Term, XtensionQuasiquoteTerm}

class ScalaTest2JUnitExtension extends Scala2JavaExtension
  with ScalaTest2JUnitPredicates
  with ScalaTest2JUnitProviders
  with ScalaTest2JUnitTransformers {

  override def shouldBeAppliedIfContains(termSelect: Term.Select): Boolean =
    q"org.scalatest".structure == termSelect.structure
}
