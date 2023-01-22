package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.test.utils.matchers.CombinedMatchers.eqTreeList
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite
import org.mockito.ArgumentMatchersSugar.eqTo

import scala.meta.{XtensionQuasiquoteInit, XtensionQuasiquoteTerm}

class ScalatestClassTransformerTest extends UnitTestSuite {

  private val classNameTransformer = mock[ClassNameTransformer]

  private val classTransformer = new ScalatestClassTransformer(classNameTransformer)

  test("transform") {
    val defnClass = q"class MySpec extends AnyFunSpec"
    val expectedDefnClass = q"class MyTest extends AnyFunSpec"

    when(classNameTransformer.transform(eqTo("MySpec"), eqTreeList(List(init"AnyFunSpec")))).thenReturn("MyTest")

    classTransformer.transform(defnClass).structure shouldBe expectedDefnClass.structure
  }
}
