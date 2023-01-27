package io.github.effiban.scala2javaext.scalatest.classifiers

import scala.meta.Term

import scala.meta.quasiquotes.XtensionQuasiquoteTerm

trait ScalatestTermNameClassifier {

  def isTermApplyRegistrator(termName: Term.Name): Boolean

  def isTermApplyNestedRegistrator(termName: Term.Name): Boolean

  def isTermApplyInfixNestedRegistrator(termName: Term.Name): Boolean

  def isSpecVerb(termName: Term.Name): Boolean

}

object ScalatestTermNameClassifier extends ScalatestTermNameClassifier {

  def isTermApplyRegistrator(termName: Term.Name): Boolean = termName match {
    case q"test" | q"Scenario" | q"scenario" | q"it" | q"they" => true
    case _ => false
  }

  def isTermApplyNestedRegistrator(termName: Term.Name): Boolean = termName match {
    case q"Feature" | q"feature" | q"describe" => true
    case _ => false
  }

  def isTermApplyInfixNestedRegistrator(termName: Term.Name): Boolean = termName match {
    case aTermName if isSpecVerb(aTermName) => true
    case q"when" | q"which" => true
    case _ => false
  }

  def isSpecVerb(termName: Term.Name): Boolean = termName match {
    case q"should" | q"must" | q"can" => true
    case _ => false
  }
}
