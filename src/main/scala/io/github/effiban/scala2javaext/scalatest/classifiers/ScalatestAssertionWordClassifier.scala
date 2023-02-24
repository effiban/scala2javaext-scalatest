package io.github.effiban.scala2javaext.scalatest.classifiers

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

trait ScalatestAssertionWordClassifier {

  def isAssertionWord(word: Term.Name): Boolean

  def isEqualAssertionWord(word: Term.Name): Boolean

  def isBeAssertionWord(word: Term.Name): Boolean
}

object ScalatestAssertionWordClassifier extends ScalatestAssertionWordClassifier {

  override def isAssertionWord(word: Term.Name): Boolean = word match {
    case q"should" | q"must" => true
    case aWord if isEqualAssertionWord(aWord) => true
    case aWord if isBeAssertionWord(aWord) => true
    case _ => false
  }

  override def isEqualAssertionWord(word: Term.Name): Boolean = word match {
    case q"shouldEqual" | q"mustEqual" => true
    case _ => false
  }

  override def isBeAssertionWord(word: Term.Name): Boolean = word match {
    case q"shouldBe" | q"mustBe" => true
    case _ => false
  }
}
