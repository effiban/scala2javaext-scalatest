package io.github.effiban.scala2javaext.scalatest.generators

import io.github.effiban.scala2javaext.scalatest.transformers.IdentifierNormalizer

import scala.meta.{Defn, Lit, Term, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

trait JUnitTestMethodGenerator {

  def generate(args: List[Term])(body: Term): Option[Defn.Def]
}

private[generators] class JUnitTestMethodGeneratorImpl(junitAnnotationGenerator: JUnitAnnotationGenerator,
                                                       identifierNormalizer: IdentifierNormalizer) extends JUnitTestMethodGenerator {

  override def generate(args: List[Term])(body: Term): Option[Defn.Def] = {
    args match {
      case Lit.String(name) :: Nil => Some(generate(name)(body))
      case Lit.String(name) :: tags => Some(generate(name, tagNamesOf(tags))(body))
      case _ => None
    }
  }

  private def generate(name: String, tagNames: List[String] = Nil)(body: Term) = {
    import junitAnnotationGenerator._

    Defn.Def(
      mods = List(testAnnotation(), displayNameAnnotationWith(name)) ++ tagAnnotationsWith(tagNames),
      name = Term.Name(identifierNormalizer.toMemberName(name)),
      tparams = Nil,
      paramss = List(Nil),
      decltpe = Some(t"Unit"),
      body = body
    )
  }

  private def tagNamesOf(tags: List[Term]): List[String] = {
    tags.collect {
      case Term.Apply(q"Tag", List(Lit.String(tagName))) => tagName
      case tag: Term.Name => tag.value
    }
  }
}

object JUnitTestMethodGenerator extends JUnitTestMethodGeneratorImpl(JUnitAnnotationGenerator, IdentifierNormalizer)