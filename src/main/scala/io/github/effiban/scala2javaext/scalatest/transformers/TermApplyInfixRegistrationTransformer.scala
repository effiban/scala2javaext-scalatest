package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.TemplateTermApplyInfixToDefnTransformer
import io.github.effiban.scala2javaext.scalatest.extractors.TermSpecNameExtractor
import io.github.effiban.scala2javaext.scalatest.generators.JUnitTestMethodGenerator

import scala.meta.quasiquotes.XtensionQuasiquoteTerm
import scala.meta.{Defn, Term}

private[transformers] class TermApplyInfixRegistrationTransformer(termSpecNameExtractor: TermSpecNameExtractor,
                                                                  junitTestMethodGenerator: JUnitTestMethodGenerator)
  extends TemplateTermApplyInfixToDefnTransformer  {

  override def transform(termApplyInfix: Term.ApplyInfix): Option[Defn] = termApplyInfix match {
    case Term.ApplyInfix(description: Term, q"in", Nil, body :: Nil) => description match {
      case Term.ApplyInfix(spec: Term, q"taggedAs", Nil, tags: List[Term]) => transformInner(spec, tags)(body)
      case spec => transformInner(spec)(body)
    }
    case _ => None
  }

  private def transformInner(spec: Term, tags: List[Term] = Nil)(body: Term) = {
    termSpecNameExtractor.extract(spec)
      .flatMap(name => junitTestMethodGenerator.generate(name :: tags)(body))
  }
}

object TermApplyInfixRegistrationTransformer extends TermApplyInfixRegistrationTransformer(
  TermSpecNameExtractor,
  JUnitTestMethodGenerator
)
