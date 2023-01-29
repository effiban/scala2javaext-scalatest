package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.TemplateTermApplyInfixToDefnTransformer
import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.extractors.TermSpecInfoExtractor
import io.github.effiban.scala2javaext.scalatest.generators.JUnitTestMethodGenerator

import scala.meta.quasiquotes.XtensionQuasiquoteTerm
import scala.meta.{Defn, Term}

private[transformers] class TermApplyInfixRegistrationTransformer(scalatestTermNameClassifier: ScalatestTermNameClassifier,
                                                                  termSpecInfoExtractor: TermSpecInfoExtractor,
                                                                  junitTestMethodGenerator: JUnitTestMethodGenerator)
  extends TemplateTermApplyInfixToDefnTransformer  {

  override def transform(termApplyInfix: Term.ApplyInfix): Option[Defn] = {
    import scalatestTermNameClassifier._
    termApplyInfix match {
      case Term.ApplyInfix(description: Term, registrator: Term.Name, Nil, body :: Nil) if isTermApplyInfixRegistrator(registrator) =>
        transformByDescription(description, registrator)(body)
      case _ => None
    }
  }

  private def transformByDescription(description: Term, registrator: Term.Name)(body: Term) = {
    description match {
      case Term.ApplyInfix(spec: Term, q"taggedAs", Nil, tags: List[Term]) => transformBySpec(spec, tags, registrator)(body)
      case spec => transformBySpec(spec = spec, registrator = registrator)(body)
    }
  }

  private def transformBySpec(spec: Term, tags: List[Term] = Nil, registrator: Term.Name)(body: Term) = {
    import scalatestTermNameClassifier._

    termSpecInfoExtractor.extract(spec)
      .flatMap(specInfo => {
        val disabled = isIgnore(registrator) || specInfo.ignored
        junitTestMethodGenerator.generate(specInfo.name :: tags, disabled)(body)
      })
  }
}

object TermApplyInfixRegistrationTransformer extends TermApplyInfixRegistrationTransformer(
  ScalatestTermNameClassifier,
  TermSpecInfoExtractor,
  JUnitTestMethodGenerator
)
