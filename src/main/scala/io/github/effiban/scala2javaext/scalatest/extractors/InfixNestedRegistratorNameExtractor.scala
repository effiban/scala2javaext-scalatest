package io.github.effiban.scala2javaext.scalatest.extractors

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier

import scala.meta.{Lit, Term}
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

trait InfixNestedRegistratorNameExtractor {
  def extract(word: Term.Name): Option[Lit.String]
}

private[extractors] class InfixNestedRegistratorNameExtractorImpl(scalatestTermNameClassifier: ScalatestTermNameClassifier)
  extends InfixNestedRegistratorNameExtractor {

  override def extract(word: Term.Name): Option[Lit.String] = {
    word match {
      case aWord if scalatestTermNameClassifier.isTermApplyInfixNestedRegistrator(word) => Some(Lit.String(aWord.value))
      case q"-" => Some(Lit.String(""))
      case _ => None
    }
  }
}

object InfixNestedRegistratorNameExtractor extends InfixNestedRegistratorNameExtractorImpl(ScalatestTermNameClassifier)
