package io.github.effiban.scala2javaext.scalatest.classifiers

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

trait ScalatestMatcherWordClassifier {

  def isEqualWord(word: Term.Name): Boolean

  def isSizeWord(word: Term.Name): Boolean
}

object ScalatestMatcherWordClassifier extends ScalatestMatcherWordClassifier {

  override def isEqualWord(word: Term.Name): Boolean = word match {
    case q"equal" | q"===" => true
    case _ => false
  }

  override def isSizeWord(word: Term.Name): Boolean = word match {
    case q"size" | q"length" => true
    case _ => false
  }
}
