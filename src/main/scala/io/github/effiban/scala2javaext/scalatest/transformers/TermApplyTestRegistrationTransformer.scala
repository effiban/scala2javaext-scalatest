package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.TemplateTermApplyToDefnTransformer
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.generators.JUnitTestMethodGenerator

import scala.meta.quasiquotes.XtensionQuasiquoteTerm
import scala.meta.{Defn, Term}

private[transformers] class TermApplyTestRegistrationTransformer(scalatestTermNameClassifier: ScalatestTermNameClassifier,
                                                                 junitTestMethodGenerator: JUnitTestMethodGenerator)
  extends TemplateTermApplyToDefnTransformer {

  override def transform(termApply: Term.Apply): Option[Defn.Def] = {
    import scalatestTermNameClassifier._
    import junitTestMethodGenerator._

    termApply match {
      case Term.Apply(Term.Apply(word: Term.Name, args), body :: Nil) if isTestRegistrationWord(word) => generate(args)(body)
      case _ => None
    }
  }
}

private[transformers] object TermApplyTestRegistrationTransformer extends TermApplyTestRegistrationTransformer(
  ScalatestTermNameClassifier,
  JUnitTestMethodGenerator
)
