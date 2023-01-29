package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.CombinedMatchers.eqTreeList
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import org.mockito.ArgumentMatchersSugar.eqTo

import scala.meta.{XtensionQuasiquoteInit, XtensionQuasiquoteMod, XtensionQuasiquoteTerm}

class ScalatestClassTransformerTest extends UnitTestSuite {

  private val classModsTransformer = mock[ClassModsTransformer]
  private val classNameTransformer = mock[ClassNameTransformer]

  private val classTransformer = new ScalatestClassTransformer(classModsTransformer, classNameTransformer)

  test("transform") {
    val defnClass =
      q"""
      @Ignore
      class MySpec extends AnyFunSpec
      """

    val expectedDefnClass =
      q"""
      @Disabled
      class MyTest extends AnyFunSpec
      """

    when(classModsTransformer.transform(eqTreeList(List(mod"@Ignore")))).thenReturn(List(mod"@Disabled"))
    when(classNameTransformer.transform(eqTo("MySpec"), eqTreeList(List(init"AnyFunSpec")))).thenReturn("MyTest")

    classTransformer.transform(defnClass).structure shouldBe expectedDefnClass.structure
  }
}
