package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier

import scala.meta.{Lit, Term}

trait InfixSpecNameExtractor extends SpecNameExtractor[Term.ApplyInfix]

private[extractors] class InfixSpecNameExtractorImpl(subjectNameExtractor: SubjectNameExtractor,
                                                     termNameClassifier: ScalatestTermNameClassifier) extends InfixSpecNameExtractor {

  def extract(infixSpec: Term.ApplyInfix): Option[Lit.String] = {
    import termNameClassifier._

    infixSpec match {
      case Term.ApplyInfix(subject, verb: Term.Name, Nil, (expectation: Lit.String) :: Nil) if isSpecVerb(verb) =>
        subjectNameExtractor.extract(subject)
          .map(subjectName => Lit.String(s"${subjectName.value} ${verb.value} ${expectation.value}"))

      case _ => None
    }
  }
}

object InfixSpecNameExtractor extends InfixSpecNameExtractorImpl(SubjectNameExtractor, ScalatestTermNameClassifier)