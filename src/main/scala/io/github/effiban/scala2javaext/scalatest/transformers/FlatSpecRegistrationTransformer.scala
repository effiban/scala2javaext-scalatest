package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.extractors.FlatSpecTestNameExtractor

import scala.meta.quasiquotes.XtensionQuasiquoteTerm
import scala.meta.{Defn, Term}

private[transformers] trait FlatSpecRegistrationTransformer {
  def transform(description: Term.ApplyInfix)(body: Term): Option[Defn]
}

private[transformers] class FlatSpecRegistrationTransformerImpl(flatSpecTestNameExtractor: FlatSpecTestNameExtractor,
                                                                testRegistrationTransformer: TestRegistrationTransformer)
  extends FlatSpecRegistrationTransformer {

  override def transform(description: Term.ApplyInfix)(body: Term): Option[Defn] = description match {
    case Term.ApplyInfix(nameClause: Term.ApplyInfix, q"taggedAs", Nil, tags: List[Term]) => transformInner(nameClause, tags)(body)
    case nameClause => transformInner(nameClause)(body)
  }

  private def transformInner(nameClause: Term.ApplyInfix, tags: List[Term] = Nil)(body: Term) = {
    flatSpecTestNameExtractor.extract(nameClause)
      .flatMap(name => testRegistrationTransformer.transform(name :: tags)(body))
  }
}

object FlatSpecRegistrationTransformer extends FlatSpecRegistrationTransformerImpl(
  FlatSpecTestNameExtractor,
  TestRegistrationTransformer
)
