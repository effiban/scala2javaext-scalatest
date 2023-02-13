package io.github.effiban.scala2javaext.scalatest.typeinferrers

import io.github.effiban.scala2java.spi.typeinferrers.NameTypeInferrer

import scala.meta.{Term, Type, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

object ScalatestNameTypeInferrer extends NameTypeInferrer  {

  override def infer(termName: Term.Name): Option[Type] = {
    termName match {
      case q"assert" |
           q"assertResult" |
           q"assertThrows" |
           q"fail" => Some(t"Unit")
      case _ => None
    }
  }
}
