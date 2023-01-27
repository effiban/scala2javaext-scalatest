package io.github.effiban.scala2javaext.scalatest.transformers

import scala.meta.{Lit, Term}

trait InfixRegistrationSubjectModifier {
  def prepend(infixRegistration: Term.ApplyInfix, prefix: Lit.String): Term
}

object InfixRegistrationSubjectModifier extends InfixRegistrationSubjectModifier {

  override def prepend(infixRegistration: Term.ApplyInfix, subjectPrefix: Lit.String): Term = infixRegistration match {
    case registration@Term.ApplyInfix(subjectName: Lit.String, _, _, _) => registration.copy(lhs = prepend(subjectName, subjectPrefix))
    case registration@Term.ApplyInfix(spec@Term.ApplyInfix(subjectName: Lit.String, _, _, _), _, _, _) =>
      registration.copy(lhs = spec.copy(lhs = prepend(subjectName, subjectPrefix)))
    case registration => registration
  }

  private def prepend(subjectName: Lit.String, subjectPrefix: Lit.String) = {
    Lit.String(s"${subjectPrefix.value} ${subjectName.value}")
  }
}
