package io.github.effiban.scala2javaext.scalatest.classifiers

import scala.meta.Term

import scala.meta.quasiquotes.XtensionQuasiquoteTerm

trait ScalaTestTermNameClassifier {

  def isSpecVerb(verb: Term.Name): Boolean
}

object ScalaTestTermNameClassifier extends ScalaTestTermNameClassifier {

  def isSpecVerb(verb: Term.Name): Boolean = verb match {
    case q"should" | q"must" | q"can" => true
    case _ => false
  }
}
