package io.github.effiban.scala2javaext.scalatest.typeinferrers

import io.github.effiban.scala2java.spi.contexts.TermSelectInferenceContext
import io.github.effiban.scala2java.spi.typeinferrers.SelectTypeInferrer

import scala.meta.{Term, Type, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

object ScalatestSelectTypeInferrer extends SelectTypeInferrer  {

  override def infer(termSelect: Term.Select, context: TermSelectInferenceContext): Option[Type] = {
    (context.maybeQualType, termSelect.name) match {
      case (Some(t"org.scalatest.Assertions"), q"assert" | q"assertResult" | q"assertThrows" | q"fail") => Some(t"scala.Unit")
      case _ => None
    }
  }
}
