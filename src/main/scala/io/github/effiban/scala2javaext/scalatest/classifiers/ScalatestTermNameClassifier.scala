package io.github.effiban.scala2javaext.scalatest.classifiers

import scala.meta.Term

import scala.meta.quasiquotes.XtensionQuasiquoteTerm

trait ScalatestTermNameClassifier {

  def isSpecVerb(verb: Term.Name): Boolean

  def isTestRegistrationWord(word: Term.Name): Boolean

  def isNestedTestRegistrationWord(word: Term.Name): Boolean
}

object ScalatestTermNameClassifier extends ScalatestTermNameClassifier {

  def isSpecVerb(verb: Term.Name): Boolean = verb match {
    case q"should" | q"must" | q"can" => true
    case _ => false
  }

  def isTestRegistrationWord(word: Term.Name): Boolean = word match {
    case q"test" | q"Scenario" | q"scenario" | q"it" | q"they" => true
    case _ => false
  }

  def isNestedTestRegistrationWord(word: Term.Name): Boolean = word match {
    case q"Feature" | q"feature" | q"describe" => true
    case _ => false
  }
}
