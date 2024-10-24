package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

class ScalatestTransformersTest extends UnitTestSuite {

  private val scalatestTransformers = new ScalatestTransformers {}
  import scalatestTransformers._

  test("classTransformer") {
    classTransformer() shouldBe ScalatestClassTransformer
  }

  test("defnDefTransformer") {
    defnDefTransformer() shouldBe ScalatestDefnDefTransformer
  }

  test("templateTermApplyInfixToDefnTransformer") {
    templateTermApplyInfixToDefnTransformer() shouldBe ScalatestTemplateTermApplyInfixToDefnTransformer
  }

  test("templateTermApplyToDefnTransformer") {
    templateTermApplyToDefnTransformer() shouldBe ScalatestTemplateTermApplyToDefnTransformer
  }

  test("termApplyInfixToTermApplyTransformer") {
    termApplyInfixToTermApplyTransformer() shouldBe ScalatestTermApplyInfixToTermApplyTransformer
  }
}
