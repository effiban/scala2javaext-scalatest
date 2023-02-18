package io.github.effiban.scala2javaext.scalatest.classifiers

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

trait ScalatestAssertionWordClassifier {

  def isAssertionWord(word: Term.Name): Boolean

  def isEqualityAssertionWord(word: Term.Name): Boolean
}

object ScalatestAssertionWordClassifier extends ScalatestAssertionWordClassifier {

  override def isAssertionWord(word: Term.Name): Boolean = word match {
    case q"should" | q"must" => true
    case verb if isEqualityAssertionWord(verb) => true
    case _ => false
  }

  override def isEqualityAssertionWord(word: Term.Name): Boolean = word match {
    case q"shouldBe" |
         q"shouldEqual" |
         q"mustBe" |
         q"mustEqual" => true
    case _ => false
  }
}
