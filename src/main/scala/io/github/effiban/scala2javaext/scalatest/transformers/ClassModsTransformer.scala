package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.SameTypeTransformer0
import io.github.effiban.scala2javaext.scalatest.generators.JUnitAnnotationGenerator

import scala.meta.{Mod, XtensionQuasiquoteMod}


private[transformers] trait ClassModsTransformer extends SameTypeTransformer0[List[Mod]]

private[transformers] class ClassModsTransformerImpl(junitAnnotationGenerator: JUnitAnnotationGenerator) extends ClassModsTransformer {

  override def transform(mods: List[Mod]): List[Mod] = {
    mods.map {
      case mod"@org.scalatest.Ignore" => junitAnnotationGenerator.disabledAnnotation()
      case other => other
    }
  }
}

private[transformers] object ClassModsTransformer extends ClassModsTransformerImpl(JUnitAnnotationGenerator)
