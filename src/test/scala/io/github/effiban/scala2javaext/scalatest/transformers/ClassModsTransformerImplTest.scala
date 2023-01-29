package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.generators.JUnitAnnotationGenerator
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Mod, XtensionQuasiquoteMod}

class ClassModsTransformerImplTest extends UnitTestSuite {

  private val junitAnnotationGenerator = mock[JUnitAnnotationGenerator]

  private val classModsTransformer = new ClassModsTransformerImpl(junitAnnotationGenerator)

  test("transform() when includes '@Ignore'") {
    val mods = List(mod"@Bla", mod"@Ignore", Mod.Sealed())
    val expectedMods = List(mod"@Bla", mod"@Disabled", Mod.Sealed())

    when(junitAnnotationGenerator.disabledAnnotation()).thenReturn(mod"@Disabled")

    classModsTransformer.transform(mods).structure shouldBe expectedMods.structure
  }

  test("transform() when doesn't include '@Ignore'") {
    val mods = List(mod"@Bla", mod"@Gaga", Mod.Sealed())

    classModsTransformer.transform(mods).structure shouldBe mods.structure
  }
}
