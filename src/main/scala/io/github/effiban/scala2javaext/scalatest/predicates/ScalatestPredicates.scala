package io.github.effiban.scala2javaext.scalatest.predicates

import io.github.effiban.scala2java.spi.predicates.{ExtendedPredicates, ImporterExcludedPredicate, InvocationArgByNamePredicate, TemplateInitExcludedPredicate}

trait ScalatestPredicates extends ExtendedPredicates {

  override def importerExcludedPredicate(): ImporterExcludedPredicate = ScalatestImporterExcludedPredicate

  override def templateInitExcludedPredicate(): TemplateInitExcludedPredicate = ScalatestTemplateInitExcludedPredicate

  override def invocationArgByNamePredicate(): InvocationArgByNamePredicate = JUnitInvocationArgByNamePredicate
}
