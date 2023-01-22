package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.common.JUnitConstants.{DisplayAnnotationType, TagAnnotationType, TestAnnotationType}

import scala.meta.{Defn, Init, Lit, Mod, Name, Term, Type, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

private[transformers] trait TestRegistrationTransformer {

  def transform(args: List[Term])(body: Term): Option[Defn.Def]
}

private[transformers] class TestRegistrationTransformerImpl(identifierNormalizer: IdentifierNormalizer) extends TestRegistrationTransformer {

  override def transform(args: List[Term])(body: Term): Option[Defn.Def] = {
    args match {
      case Lit.String(name) :: Nil => Some(transform(name)(body))
      case Lit.String(name) :: tags => Some(transform(name, tagNamesOf(tags))(body))
      case _ => None
    }
  }

  private def transform(name: String, tagNames: List[String] = Nil)(body: Term) =
    Defn.Def(
      mods = List(annotationOf(TestAnnotationType), displayNameAnnotationWith(name)) ++ tagAnnotationsWith(tagNames),
      name = Term.Name(identifierNormalizer.toMemberName(name)),
      tparams = Nil,
      paramss = List(Nil),
      decltpe = Some(t"Unit"),
      body = body
    )

  private def tagNamesOf(tags: List[Term]): List[String] = {
    tags.collect {
      case Term.Apply(q"Tag", List(Lit.String(tagName))) => tagName
      case tag: Term.Name => tag.value
    }
  }

  private def displayNameAnnotationWith(name: String): Mod.Annot = annotationOf(DisplayAnnotationType, Some(name))

  private def tagAnnotationsWith(tagNames: List[String]): List[Mod.Annot] = tagNames.map(tagName => annotationOf(TagAnnotationType, Some(tagName)))

  private def annotationOf(annotType: Type.Name, maybeArg: Option[String] = None) = {
    val args = maybeArg.map(arg => List(List(Lit.String(arg)))).getOrElse(Nil)
    Mod.Annot(Init(annotType, Name.Anonymous(), args))
  }
}

object TestRegistrationTransformer extends TestRegistrationTransformerImpl(IdentifierNormalizer)