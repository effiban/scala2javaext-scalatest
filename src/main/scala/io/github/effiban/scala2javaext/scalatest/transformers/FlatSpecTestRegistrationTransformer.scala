package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.TemplateTermApplyInfixToDefnTransformer
import io.github.effiban.scala2javaext.scalatest.extractors.FlatSpecTestNameExtractor
import io.github.effiban.scala2javaext.scalatest.generators.JUnitTestMethodGenerator

import scala.meta.quasiquotes.XtensionQuasiquoteTerm
import scala.meta.{Defn, Term}

private[transformers] class FlatSpecTestRegistrationTransformer(flatSpecTestNameExtractor: FlatSpecTestNameExtractor,
                                                                junitTestMethodGenerator: JUnitTestMethodGenerator)
  extends TemplateTermApplyInfixToDefnTransformer  {

  override def transform(termApplyInfix: Term.ApplyInfix): Option[Defn] = termApplyInfix match {
    case Term.ApplyInfix(description: Term.ApplyInfix, q"in", Nil, body :: Nil) => description match {
      case Term.ApplyInfix(spec: Term.ApplyInfix, q"taggedAs", Nil, tags: List[Term]) => transformInner(spec, tags)(body)
      case spec => transformInner(spec)(body)
    }
    case _ => None
  }

  private def transformInner(nameClause: Term.ApplyInfix, tags: List[Term] = Nil)(body: Term) = {
    flatSpecTestNameExtractor.extract(nameClause)
      .flatMap(name => junitTestMethodGenerator.generate(name :: tags)(body))
  }
}

object FlatSpecTestRegistrationTransformer extends FlatSpecTestRegistrationTransformer(
  FlatSpecTestNameExtractor,
  JUnitTestMethodGenerator
)
