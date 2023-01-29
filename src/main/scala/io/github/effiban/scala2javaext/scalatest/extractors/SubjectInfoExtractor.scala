package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.entities.SpecInfo

import scala.meta.{Lit, Term, XtensionQuasiquoteTerm}

trait SubjectInfoExtractor extends SpecInfoExtractor[Term]

private[extractors] class SubjectInfoExtractorImpl(scalatestTermNameClassifier: ScalatestTermNameClassifier) extends SubjectInfoExtractor {

  override def extract(subject: Term): Option[SpecInfo] = {
    import scalatestTermNameClassifier._

    subject match {
      case subj@Term.Name("it" | "they") => Some(SpecInfo(Lit.String(subj.value)))
      case subj: Term.Name if isIgnore(subj) => Some(SpecInfo(q""""it"""", ignored = true))
      case litStr: Lit.String => Some(SpecInfo(litStr))
      case _ => None
    }
  }
}

object SubjectInfoExtractor extends SubjectInfoExtractorImpl(ScalatestTermNameClassifier)