package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2javaext.scalatest.classifiers.TestRegistrationWordClassifier

import scala.meta.quasiquotes.XtensionQuasiquoteTerm
import scala.meta.{Lit, Term}

trait InfixNestedRegistratorNameExtractor {
  def extract(word: Term.Name): Option[Lit.String]
}

private[extractors] class InfixNestedRegistratorNameExtractorImpl(registrationWordClassifier: TestRegistrationWordClassifier)
  extends InfixNestedRegistratorNameExtractor {

  override def extract(word: Term.Name): Option[Lit.String] = {
    word match {
      case aWord if registrationWordClassifier.isTermApplyInfixNestedRegistrator(word) => Some(Lit.String(aWord.value))
      case q"-" => Some(Lit.String(""))
      case _ => None
    }
  }
}

object InfixNestedRegistratorNameExtractor extends InfixNestedRegistratorNameExtractorImpl(TestRegistrationWordClassifier)
