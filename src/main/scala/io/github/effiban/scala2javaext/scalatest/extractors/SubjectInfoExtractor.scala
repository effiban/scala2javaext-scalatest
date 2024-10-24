package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2javaext.scalatest.classifiers.TestRegistrationWordClassifier
import io.github.effiban.scala2javaext.scalatest.entities.SpecInfo

import scala.meta.{Lit, Term, XtensionQuasiquoteTerm}

trait SubjectInfoExtractor extends SpecInfoExtractor[Term]

private[extractors] class SubjectInfoExtractorImpl(registrationWordClassifier: TestRegistrationWordClassifier) extends SubjectInfoExtractor {

  override def extract(subject: Term): Option[SpecInfo] = {
    import registrationWordClassifier._

    subject match {
      case Term.Select(_, subj@Term.Name("it" | "they")) => Some(SpecInfo(Lit.String(subj.value)))
      case Term.Select(_, subj: Term.Name) if isIgnore(subj) => Some(SpecInfo(q""""it"""", ignored = true))
      case litStr: Lit.String => Some(SpecInfo(litStr))
      case _ => None
    }
  }
}

object SubjectInfoExtractor extends SubjectInfoExtractorImpl(TestRegistrationWordClassifier)