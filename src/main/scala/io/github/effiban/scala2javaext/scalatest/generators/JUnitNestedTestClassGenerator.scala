package io.github.effiban.scala2javaext.scalatest.generators

import io.github.effiban.scala2javaext.scalatest.normalizers.IdentifierNormalizer
import io.github.effiban.scala2javaext.scalatest.transformers.testregistrations.InfixRegistrationSubjectModifier

import scala.meta.{Ctor, Defn, Lit, Name, Self, Stat, Template, Term, Type}

trait JUnitNestedTestClassGenerator {
  def generate(name: Lit.String, nestedPrefix: Lit.String = Lit.String(""), nestedRegistrations: List[Stat]): Defn.Class
}

private[generators] class JUnitNestedTestClassGeneratorImpl(infixRegistrationSubjectModifier: InfixRegistrationSubjectModifier,
                                                            junitAnnotationGenerator: JUnitAnnotationGenerator,
                                                            identifierNormalizer: IdentifierNormalizer) extends JUnitNestedTestClassGenerator {

  private val EmptyCtorPrimary = Ctor.Primary(
    mods = Nil,
    name = Name.Anonymous(),
    paramss = Nil
  )

  private val EmptySelf = Self(Name.Anonymous(), None)


  override def generate(name: Lit.String, nestedPrefix: Lit.String, nestedRegistrations: List[Stat]): Defn.Class = {
    import identifierNormalizer._
    import infixRegistrationSubjectModifier._
    import junitAnnotationGenerator._

    val nestedRegistrationsWithPrefix = nestedRegistrations.map {
      case infixRegistration: Term.ApplyInfix => prepend(infixRegistration, nestedPrefix)
      case registration => registration
    }

    val mods = List(nestedAnnotation(), displayNameAnnotationWith(name.value))
    val className = Type.Name(toClassName(name.value))

    Defn.Class(
      mods = mods,
      name = className,
      tparams = Nil,
      ctor = EmptyCtorPrimary,
      templ = Template(
        early = Nil,
        inits = Nil,
        self = EmptySelf,
        stats = nestedRegistrationsWithPrefix
      )
    )
  }
}

object JUnitNestedTestClassGenerator extends JUnitNestedTestClassGeneratorImpl(
  InfixRegistrationSubjectModifier,
  JUnitAnnotationGenerator,
  IdentifierNormalizer
)