package io.github.effiban.scala2javaext.scalatest.classifiers

import scala.meta.Term
import scala.meta.quasiquotes.XtensionQuasiquoteTerm

trait ScalatestMatcherOperatorClassifier {

  def isEquality(operator: Term.Name): Boolean
}

object ScalatestMatcherOperatorClassifier extends ScalatestMatcherOperatorClassifier {

  override def isEquality(operator: Term.Name): Boolean = operator match {
    case q"equal" | q"===" | q"be" => true
    case _ => false
  }
}
