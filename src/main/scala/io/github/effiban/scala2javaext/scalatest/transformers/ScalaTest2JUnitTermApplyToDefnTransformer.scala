package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.TemplateTermApplyToDefnTransformer

import scala.meta.quasiquotes.XtensionQuasiquoteTerm
import scala.meta.{Defn, Term}

class ScalaTest2JUnitTermApplyToDefnTransformer(testRegistrationTransformer: TestRegistrationTransformer)
  extends TemplateTermApplyToDefnTransformer {

  override def transform(termApply: Term.Apply): Option[Defn] = {
    termApply match {
      case Term.Apply(Term.Apply(q"test", args), body :: Nil) => testRegistrationTransformer.transform(args)(body)
      case _ => None
    }
  }
}

object ScalaTest2JUnitTermApplyToDefnTransformer extends ScalaTest2JUnitTermApplyToDefnTransformer(TestRegistrationTransformer)
