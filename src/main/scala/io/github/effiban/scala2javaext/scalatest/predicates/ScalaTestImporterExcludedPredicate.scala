package io.github.effiban.scala2javaext.scalatest.predicates

import io.github.effiban.scala2java.spi.predicates.ImporterExcludedPredicate

import scala.annotation.tailrec
import scala.meta.{Importer, Term, XtensionQuasiquoteTerm}

object ScalaTestImporterExcludedPredicate extends ImporterExcludedPredicate {

  override def apply(importer: Importer): Boolean = isScalaTestPackage(importer.ref)

  @tailrec
  private def isScalaTestPackage(term: Term): Boolean = term match {
    case q"org.scalatest" => true
    case Term.Select(qual: Term, _) => isScalaTestPackage(qual)
    case _ => false
  }
}
