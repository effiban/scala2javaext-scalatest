package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2javaext.scalatest.entities.SpecInfo

import scala.meta.{Lit, Term}

trait SpecInfoExtractor[T <: Term] {

  def extract(spec: T): Option[SpecInfo]
}
