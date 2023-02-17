package io.github.effiban.scala2javaext.scalatest.transformers.testregistrations

import io.github.effiban.scala2java.spi.transformers.TemplateTermApplyToDefnTransformer
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.generators.JUnitTestMethodGenerator

import scala.meta.{Defn, Term}

private[transformers] class TermApplyRegistrationTransformer(scalatestTermNameClassifier: ScalatestTermNameClassifier,
                                                             junitTestMethodGenerator: JUnitTestMethodGenerator)
  extends TemplateTermApplyToDefnTransformer {

  override def transform(termApply: Term.Apply): Option[Defn.Def] = {
    import junitTestMethodGenerator._
    import scalatestTermNameClassifier._

    termApply match {
      case Term.Apply(Term.Apply(registrator: Term.Name, args), body :: Nil) if isTermApplyRegistrator(registrator) =>
        generate(args, isIgnore(registrator))(body)
      case _ => None
    }
  }
}

private[transformers] object TermApplyRegistrationTransformer extends TermApplyRegistrationTransformer(
  ScalatestTermNameClassifier,
  JUnitTestMethodGenerator
)
