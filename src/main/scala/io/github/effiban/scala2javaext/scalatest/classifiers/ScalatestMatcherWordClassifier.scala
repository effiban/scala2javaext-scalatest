package io.github.effiban.scala2javaext.scalatest.classifiers

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

trait ScalatestMatcherWordClassifier {

  def isEqualsWord(word: Term.Name): Boolean
}

object ScalatestMatcherWordClassifier extends ScalatestMatcherWordClassifier {

  override def isEqualsWord(word: Term.Name): Boolean = word match {
    case q"equal" | q"===" | q"be" => true
    case _ => false
  }
}
