package io.github.effiban.scala2javaext.scalatest.generators

import io.github.effiban.scala2javaext.scalatest.transformers.IdentifierNormalizer

import scala.meta.{Ctor, Defn, Lit, Name, Self, Stat, Template, Type}

trait JUnitNestedTestClassGenerator {
  def generate(name: Lit.String, nestedRegistrations: List[Stat]): Defn.Class
}

private[generators] class JUnitNestedTestClassGeneratorImpl(junitAnnotationGenerator: JUnitAnnotationGenerator,
                                                            identifierNormalizer: IdentifierNormalizer) extends JUnitNestedTestClassGenerator {

  private val EmptyCtorPrimary = Ctor.Primary(
    mods = Nil,
    name = Name.Anonymous(),
    paramss = Nil
  )

  private val EmptySelf = Self(Name.Anonymous(), None)


  override def generate(name: Lit.String, nestedRegistrations: List[Stat]): Defn.Class = {
    import junitAnnotationGenerator._
    import identifierNormalizer._

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
        stats = nestedRegistrations
      )
    )
  }
}

object JUnitNestedTestClassGenerator extends JUnitNestedTestClassGeneratorImpl(
  JUnitAnnotationGenerator,
  IdentifierNormalizer
)