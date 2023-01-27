package io.github.effiban.scala2javaext.scalatest.extractors

import scala.meta.{Lit, Term}

trait TermSpecNameExtractor extends SpecNameExtractor[Term]

private[extractors] class TermSpecNameExtractorImpl(infixSpecNameExtractor: InfixSpecNameExtractor) extends TermSpecNameExtractor {

  def extract(spec: Term): Option[Lit.String] = spec match {
    case infixSpec: Term.ApplyInfix => infixSpecNameExtractor.extract(infixSpec)
    case name: Lit.String => Some(name)
    case _ => None
  }
}

object TermSpecNameExtractor extends TermSpecNameExtractorImpl(InfixSpecNameExtractor)