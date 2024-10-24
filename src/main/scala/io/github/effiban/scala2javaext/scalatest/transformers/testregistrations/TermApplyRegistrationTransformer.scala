package io.github.effiban.scala2javaext.scalatest.transformers.testregistrations

import io.github.effiban.scala2java.spi.transformers.TemplateTermApplyToDefnTransformer
import io.github.effiban.scala2javaext.scalatest.classifiers.TestRegistrationWordClassifier
import io.github.effiban.scala2javaext.scalatest.generators.JUnitTestMethodGenerator

import scala.meta.{Defn, Term}

private[transformers] class TermApplyRegistrationTransformer(registrationWordClassifier: TestRegistrationWordClassifier,
                                                             junitTestMethodGenerator: JUnitTestMethodGenerator)
  extends TemplateTermApplyToDefnTransformer {

  override def transform(termApply: Term.Apply): Option[Defn.Def] = {
    import junitTestMethodGenerator._
    import registrationWordClassifier._

    termApply match {
      case Term.Apply(Term.Apply(Term.Select(_, registrator: Term.Name), args), body :: Nil) if isTermApplyRegistrator(registrator) =>
        generate(args, isIgnore(registrator))(body)
      case _ => None
    }
  }
}

private[transformers] object TermApplyRegistrationTransformer extends TermApplyRegistrationTransformer(
  TestRegistrationWordClassifier,
  JUnitTestMethodGenerator
)
