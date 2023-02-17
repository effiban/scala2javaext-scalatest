package io.github.effiban.scala2javaext.scalatest.transformers.testregistrations

import io.github.effiban.scala2java.spi.transformers.TemplateTermApplyToDefnTransformer
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.generators.JUnitNestedTestClassGenerator

import scala.meta.{Defn, Lit, Term}

private[transformers] class TermApplyNestedRegistrationTransformer(scalatestTermNameClassifier: ScalatestTermNameClassifier,
                                                                   junitNestedTestClassGenerator: JUnitNestedTestClassGenerator)
  extends TemplateTermApplyToDefnTransformer {

  override def transform(termApply: Term.Apply): Option[Defn.Class] = {
    import junitNestedTestClassGenerator._
    import scalatestTermNameClassifier._

    termApply match {
      case Term.Apply(Term.Apply(word: Term.Name, (name: Lit.String) :: Nil), (registrationBlock: Term.Block) :: Nil)
        if isTermApplyNestedRegistrator(word) => Some(generate(name = name, nestedRegistrations = registrationBlock.stats))
      case _ => None
    }
  }
}

private[transformers] object TermApplyNestedRegistrationTransformer extends TermApplyNestedRegistrationTransformer(
  ScalatestTermNameClassifier,
  JUnitNestedTestClassGenerator
)
