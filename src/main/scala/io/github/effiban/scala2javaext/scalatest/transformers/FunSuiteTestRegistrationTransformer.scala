package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.TemplateTermApplyToDefnTransformer
import io.github.effiban.scala2javaext.scalatest.generators.JUnitTestMethodGenerator

import scala.meta.quasiquotes.XtensionQuasiquoteTerm
import scala.meta.{Defn, Term}

private[transformers] class FunSuiteTestRegistrationTransformer(junitTestMethodGenerator: JUnitTestMethodGenerator)
  extends TemplateTermApplyToDefnTransformer {

  override def transform(termApply: Term.Apply): Option[Defn] = {
    termApply match {
      case Term.Apply(Term.Apply(q"test", args), body :: Nil) => junitTestMethodGenerator.generate(args)(body)
      case _ => None
    }
  }
}

object FunSuiteTestRegistrationTransformer extends FunSuiteTestRegistrationTransformer(JUnitTestMethodGenerator)
