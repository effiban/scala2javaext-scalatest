package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.TemplateTermApplyInfixToDefnTransformer
import io.github.effiban.scala2javaext.scalatest.extractors.{InfixNestedRegistratorNameExtractor, SubjectInfoExtractor}
import io.github.effiban.scala2javaext.scalatest.generators.JUnitNestedTestClassGenerator

import scala.meta.{Defn, Term}

private[transformers] class TermApplyInfixNestedRegistrationTransformer(infixNestedRegistratorNameExtractor: InfixNestedRegistratorNameExtractor,
                                                                        subjectInfoExtractor: SubjectInfoExtractor,
                                                                        junitNestedTestClassGenerator: JUnitNestedTestClassGenerator)
  extends TemplateTermApplyInfixToDefnTransformer {

  override def transform(termApplyInfix: Term.ApplyInfix): Option[Defn.Class] = {
    termApplyInfix match {
      case Term.ApplyInfix(subject: Term, word: Term.Name, Nil, (registrationBlock: Term.Block) :: Nil) =>
        for {
          subjectInfo <- subjectInfoExtractor.extract(subject)
          nestedPrefix <- infixNestedRegistratorNameExtractor.extract(word)
        } yield junitNestedTestClassGenerator.generate(subjectInfo.name, nestedPrefix, registrationBlock.stats)

      case _ => None
    }
  }
}


private[transformers] object TermApplyInfixNestedRegistrationTransformer extends TermApplyInfixNestedRegistrationTransformer(
  InfixNestedRegistratorNameExtractor,
  SubjectInfoExtractor,
  JUnitNestedTestClassGenerator
)
