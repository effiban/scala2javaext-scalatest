package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.TemplateTermApplyInfixToDefnTransformer

import scala.meta.quasiquotes.XtensionQuasiquoteTerm
import scala.meta.{Defn, Term}

class ScalatestTemplateTermApplyInfixToDefnTransformer(flatSpecRegistrationTransformer: FlatSpecRegistrationTransformer)
  extends TemplateTermApplyInfixToDefnTransformer {

  override def transform(termApplyInfix: Term.ApplyInfix): Option[Defn] = termApplyInfix match {
      case Term.ApplyInfix(description: Term.ApplyInfix, q"in", Nil, body :: Nil) => flatSpecRegistrationTransformer.transform(description)(body)
      case _ => None
    }
}

object ScalatestTemplateTermApplyInfixToDefnTransformer extends ScalatestTemplateTermApplyInfixToDefnTransformer(FlatSpecRegistrationTransformer)
