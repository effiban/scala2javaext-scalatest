package io.github.effiban.scala2javaext.scalatest.classifiers

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

trait TestRegistrationWordClassifier {

  def isTermApplyRegistrator(termName: Term.Name): Boolean

  def isTermApplyNestedRegistrator(termName: Term.Name): Boolean

  def isTermApplyInfixRegistrator(termName: Term.Name): Boolean

  def isTermApplyInfixNestedRegistrator(termName: Term.Name): Boolean

  def isSpecVerb(termName: Term.Name): Boolean

  def isIgnore(termName: Term.Name): Boolean
}

object TestRegistrationWordClassifier extends TestRegistrationWordClassifier {

  override def isTermApplyRegistrator(termName: Term.Name): Boolean = termName match {
    case q"test" | q"Scenario" | q"scenario" | q"it" | q"they" => true
    case termName if isIgnore(termName) => true
    case _ => false
  }

  override def isTermApplyNestedRegistrator(termName: Term.Name): Boolean = termName match {
    case q"Feature" | q"feature" | q"describe" => true
    case _ => false
  }

  override def isTermApplyInfixRegistrator(termName: Term.Name): Boolean = termName match {
    case q"in" => true
    case termName if isIgnore(termName) => true
    case _ => false
  }

  override def isTermApplyInfixNestedRegistrator(termName: Term.Name): Boolean = termName match {
    case aTermName if isSpecVerb(aTermName) => true
    case q"when" | q"which" => true
    case _ => false
  }

  override def isSpecVerb(termName: Term.Name): Boolean = termName match {
    case q"should" | q"must" | q"can" => true
    case _ => false
  }

  override def isIgnore(termName: Term.Name): Boolean = termName.structure == q"ignore".structure
}
