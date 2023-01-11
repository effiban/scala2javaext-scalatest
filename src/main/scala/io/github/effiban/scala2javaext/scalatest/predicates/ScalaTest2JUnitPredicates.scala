package io.github.effiban.scala2javaext.scalatest.predicates

import io.github.effiban.scala2java.spi.predicates.{ExtendedPredicates, ImporterExcludedPredicate, TemplateInitExcludedPredicate}

trait ScalaTest2JUnitPredicates extends ExtendedPredicates {

  override def importerExcludedPredicate(): ImporterExcludedPredicate = ScalaTestImporterExcludedPredicate

  override def templateInitExcludedPredicate(): TemplateInitExcludedPredicate = ScalaTestTemplateInitExcludedPredicate
}
