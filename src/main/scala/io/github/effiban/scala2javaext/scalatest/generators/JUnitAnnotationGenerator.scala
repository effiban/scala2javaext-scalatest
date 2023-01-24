package io.github.effiban.scala2javaext.scalatest.generators

import io.github.effiban.scala2javaext.scalatest.common.JUnitConstants.{DisplayNameAnnotationType, NestedAnnotationType, TagAnnotationType, TestAnnotationType}

import scala.meta.{Init, Lit, Mod, Name, Type}

trait JUnitAnnotationGenerator {

  def testAnnotation(): Mod.Annot

  def nestedAnnotation(): Mod.Annot

  def displayNameAnnotationWith(name: String): Mod.Annot

  def tagAnnotationsWith(tagNames: List[String]): List[Mod.Annot]
}

object JUnitAnnotationGenerator extends JUnitAnnotationGenerator {

  override def testAnnotation(): Mod.Annot = annotationOf(TestAnnotationType)

  override def nestedAnnotation(): Mod.Annot = annotationOf(NestedAnnotationType)

  override def displayNameAnnotationWith(name: String): Mod.Annot = annotationOf(DisplayNameAnnotationType, Some(name))

  override def tagAnnotationsWith(tagNames: List[String]): List[Mod.Annot] = tagNames.map(tagName => annotationOf(TagAnnotationType, Some(tagName)))

  private def annotationOf(annotType: Type.Name, maybeArg: Option[String] = None) = {
    val args = maybeArg.map(arg => List(List(Lit.String(arg)))).getOrElse(Nil)
    Mod.Annot(Init(annotType, Name.Anonymous(), args))
  }
}
