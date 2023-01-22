package io.github.effiban.scala2javaext.scalatest.predicates

import io.github.effiban.scala2java.spi.predicates.ImporterExcludedPredicate
import io.github.effiban.scala2javaext.scalatest.common.ScalatestConstants.RootPackage

import scala.meta.{Importer, Term}

object ScalatestImporterExcludedPredicate extends ImporterExcludedPredicate {

  override def apply(importer: Importer): Boolean = {
    importer.ref.collect { case termSelect: Term.Select => termSelect }
      .exists(_.structure == RootPackage.structure)
  }
}
