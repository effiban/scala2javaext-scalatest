package io.github.effiban.scala2javaext.scalatest.extractors

import scala.meta.{Lit, Term}

trait SpecNameExtractor[T <: Term] {

  def extract(spec: T): Option[Lit.String]
}
