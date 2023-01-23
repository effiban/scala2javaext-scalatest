package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.DifferentTypeTransformer0

import scala.meta.{Defn, Term}

private[transformers] trait CompositeTermToDefnTransformer[T <: Term] extends DifferentTypeTransformer0[T, Defn] {

  protected val transformers: List[DifferentTypeTransformer0[T, Defn]]

  override def transform(term: T): Option[Defn] =
    transformers.foldLeft[Option[Defn]](None)((maybeDefn, transformer) => maybeDefn.orElse(transformer.transform(term)))
}
