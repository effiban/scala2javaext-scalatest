package io.github.effiban.scala2javaext.scalatest.extractors

import scala.meta.{Lit, Term}

trait SubjectNameExtractor {
  def extract(subject: Term): Option[Lit.String]
}

object SubjectNameExtractor extends SubjectNameExtractor {

  override def extract(subject: Term): Option[Lit.String] = subject match {
    case subj@Term.Name("it" | "they") => Some(Lit.String(subj.value))
    case litStr: Lit.String => Some(litStr)
    case _ => None
  }
}
