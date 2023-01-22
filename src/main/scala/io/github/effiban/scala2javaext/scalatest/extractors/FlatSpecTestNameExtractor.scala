package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier

import scala.meta.quasiquotes.XtensionQuasiquoteTerm
import scala.meta.{Lit, Term}

trait FlatSpecTestNameExtractor {

  def extract(nameClause: Term.ApplyInfix): Option[Lit.String]
}

private[extractors] class FlatSpecTestNameExtractorImpl(termNameClassifier: ScalatestTermNameClassifier) extends FlatSpecTestNameExtractor {

  def extract(nameClause: Term.ApplyInfix): Option[Lit.String] = nameClause match {
    case Term.ApplyInfix(subject, verb: Term.Name, Nil, (expectation: Lit.String) :: Nil) if isSpecVerb(verb) =>
      extractSubjectName(subject).map(subjectName => Lit.String(s"$subjectName ${verb.value} ${expectation.value}"))

    case _ => None
  }

  private def isSpecVerb(verb: Term.Name) = termNameClassifier.isSpecVerb(verb)

  private def extractSubjectName(subject: Term) = subject match {
    case q"it" => Some("it")
    case litStr: Lit.String => Some(litStr.value)
    case _ => None
  }
}

object FlatSpecTestNameExtractor extends FlatSpecTestNameExtractorImpl(ScalatestTermNameClassifier)