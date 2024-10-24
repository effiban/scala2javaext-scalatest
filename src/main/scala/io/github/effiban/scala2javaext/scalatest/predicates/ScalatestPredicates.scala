package io.github.effiban.scala2javaext.scalatest.predicates

import io.github.effiban.scala2java.spi.predicates.{ExtendedPredicates, TemplateInitExcludedPredicate}

trait ScalatestPredicates extends ExtendedPredicates {

  override def templateInitExcludedPredicate(): TemplateInitExcludedPredicate = ScalatestTemplateInitExcludedPredicate
}
