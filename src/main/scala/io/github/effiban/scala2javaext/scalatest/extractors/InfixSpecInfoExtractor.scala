package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2javaext.scalatest.classifiers.TestRegistrationWordClassifier
import io.github.effiban.scala2javaext.scalatest.entities.SpecInfo

import scala.meta.{Lit, Term}

trait InfixSpecInfoExtractor extends SpecInfoExtractor[Term.ApplyInfix]

private[extractors] class InfixSpecInfoExtractorImpl(registrationWordClassifier: TestRegistrationWordClassifier,
                                                     subjectInfoExtractor: SubjectInfoExtractor) extends InfixSpecInfoExtractor {

  def extract(infixSpec: Term.ApplyInfix): Option[SpecInfo] = {
    import registrationWordClassifier._

    infixSpec match {
      case Term.ApplyInfix(subject, verb, Nil, (expectation: Lit.String) :: Nil) if isSpecVerb(verb) =>
        subjectInfoExtractor.extract(subject)
          .map(subjectInfo => SpecInfo(Lit.String(s"${subjectInfo.name.value} ${verb.value} ${expectation.value}"), subjectInfo.ignored))

      case _ => None
    }
  }
}

object InfixSpecInfoExtractor extends InfixSpecInfoExtractorImpl(TestRegistrationWordClassifier, SubjectInfoExtractor)