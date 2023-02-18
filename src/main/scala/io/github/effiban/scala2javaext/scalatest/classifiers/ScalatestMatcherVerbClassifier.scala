package io.github.effiban.scala2javaext.scalatest.classifiers

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

trait ScalatestMatcherVerbClassifier {

  def isMatcherVerb(verb: Term.Name): Boolean

  def isEqualityMatcherVerb(verb: Term.Name): Boolean
}

object ScalatestMatcherVerbClassifier extends ScalatestMatcherVerbClassifier {

  override def isMatcherVerb(verb: Term.Name): Boolean = verb match {
    case q"should" | q"must" => true
    case verb if isEqualityMatcherVerb(verb) => true
    case _ => false
  }

  override def isEqualityMatcherVerb(verb: Term.Name): Boolean = verb match {
    case q"shouldBe" |
         q"shouldEqual" |
         q"mustBe" |
         q"mustEqual" => true
    case _ => false
  }
}
