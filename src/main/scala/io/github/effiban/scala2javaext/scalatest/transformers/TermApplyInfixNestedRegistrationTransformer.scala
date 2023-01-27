package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.TemplateTermApplyInfixToDefnTransformer
import io.github.effiban.scala2javaext.scalatest.extractors.{InfixNestedRegistratorNameExtractor, SubjectNameExtractor}
import io.github.effiban.scala2javaext.scalatest.generators.JUnitNestedTestClassGenerator

import scala.meta.{Defn, Term}

private[transformers] class TermApplyInfixNestedRegistrationTransformer(infixNestedRegistratorNameExtractor: InfixNestedRegistratorNameExtractor,
                                                                        subjectNameExtractor: SubjectNameExtractor,
                                                                        junitNestedTestClassGenerator: JUnitNestedTestClassGenerator)
  extends TemplateTermApplyInfixToDefnTransformer {

  override def transform(termApplyInfix: Term.ApplyInfix): Option[Defn.Class] = {
    termApplyInfix match {
      case Term.ApplyInfix(subject: Term, word: Term.Name, Nil, (registrationBlock: Term.Block) :: Nil) =>
        for {
          name <- subjectNameExtractor.extract(subject)
          nestedPrefix <- infixNestedRegistratorNameExtractor.extract(word)
        } yield junitNestedTestClassGenerator.generate(name, nestedPrefix, registrationBlock.stats)

      case _ => None
    }
  }
}


private[transformers] object TermApplyInfixNestedRegistrationTransformer extends TermApplyInfixNestedRegistrationTransformer(
  InfixNestedRegistratorNameExtractor,
  SubjectNameExtractor,
  JUnitNestedTestClassGenerator
)
