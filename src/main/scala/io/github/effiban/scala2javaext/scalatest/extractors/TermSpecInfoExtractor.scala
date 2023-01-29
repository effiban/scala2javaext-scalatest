package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2javaext.scalatest.entities.SpecInfo

import scala.meta.{Lit, Term}

trait TermSpecInfoExtractor extends SpecInfoExtractor[Term]

private[extractors] class TermSpecInfoExtractorImpl(infixSpecNameExtractor: InfixSpecInfoExtractor) extends TermSpecInfoExtractor {

  def extract(spec: Term): Option[SpecInfo] = spec match {
    case infixSpec: Term.ApplyInfix => infixSpecNameExtractor.extract(infixSpec)
    case name: Lit.String => Some(SpecInfo(name))
    case _ => None
  }
}

object TermSpecInfoExtractor extends TermSpecInfoExtractorImpl(InfixSpecInfoExtractor)