package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.TemplateTermApplyInfixToDefnTransformer
import io.github.effiban.scala2javaext.scalatest.extractors.{FlatSpecNameExtractor, TermApplyInfixSpecNameExtractor}
import io.github.effiban.scala2javaext.scalatest.generators.JUnitTestMethodGenerator

import scala.meta.quasiquotes.XtensionQuasiquoteTerm
import scala.meta.{Defn, Term}

private[transformers] class TermApplyInfixTestRegistrationTransformer(termApplyInfixSpecNameExtractor: TermApplyInfixSpecNameExtractor,
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
    val maybeName = spec match {
      case infixSpec: Term.ApplyInfix => termApplyInfixSpecNameExtractor.extract(infixSpec)
      case _ => None
    }
    maybeName.flatMap(name => junitTestMethodGenerator.generate(name :: tags)(body))
  }
}

object TermApplyInfixTestRegistrationTransformer extends TermApplyInfixTestRegistrationTransformer(
  FlatSpecNameExtractor,
  JUnitTestMethodGenerator
)
