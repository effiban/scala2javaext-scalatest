package io.github.effiban.scala2javaext.scalatest.generators

import io.github.effiban.scala2javaext.scalatest.normalizers.IdentifierNormalizer

import scala.meta.{Defn, Lit, Term, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

trait JUnitTestMethodGenerator {

  def generate(args: List[Term], disabled: Boolean = false)(body: Term): Option[Defn.Def]
}

private[generators] class JUnitTestMethodGeneratorImpl(junitAnnotationGenerator: JUnitAnnotationGenerator,
                                                       identifierNormalizer: IdentifierNormalizer) extends JUnitTestMethodGenerator {

  override def generate(args: List[Term], disabled: Boolean = false)(body: Term): Option[Defn.Def] = {
    args match {
      case Lit.String(name) :: Nil => Some(generate(name, Nil, disabled = disabled)(body))
      case Lit.String(name) :: tags => Some(generate(name, tagNamesOf(tags), disabled)(body))
      case _ => None
    }
  }

  private def generate(name: String, tagNames: List[String], disabled: Boolean)(body: Term) = {
    val innerBody = body match {
      case Term.Block(Term.Function(_, aBody) :: Nil) => aBody
      case Term.Block(Term.AnonymousFunction(aBody) :: Nil) => aBody
      case aBody => aBody
    }

    Defn.Def(
      mods = generateAnnotations(name, tagNames, disabled),
      name = Term.Name(identifierNormalizer.toMemberName(name)),
      tparams = Nil,
      paramss = List(Nil),
      decltpe = Some(t"scala.Unit"),
      body = innerBody
    )
  }

  private def generateAnnotations(name: String, tagNames: List[String], disabled: Boolean) = {
    import junitAnnotationGenerator._
    val mandatoryAnnotations = List(testAnnotation(), displayNameAnnotationWith(name)) ++ tagAnnotationsWith(tagNames)
    val maybeDisabledAnnotation = if (disabled) Some(disabledAnnotation()) else None
    mandatoryAnnotations ++ maybeDisabledAnnotation
  }

  private def tagNamesOf(tags: List[Term]): List[String] = {
    tags.collect {
      case Term.Apply(q"org.scalatest.Tag" | q"Tag", List(Lit.String(tagName))) => tagName
      case tag: Term.Name => tag.value
    }
  }
}

object JUnitTestMethodGenerator extends JUnitTestMethodGeneratorImpl(JUnitAnnotationGenerator, IdentifierNormalizer)