package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

class ScalaTest2JUnitTransformersTest extends UnitTestSuite {

  private val scalaTest2JUnitTransformers = new ScalaTest2JUnitTransformers {}
  import scalaTest2JUnitTransformers._

  test("fileNameTransformer") {
    fileNameTransformer() shouldBe ScalaTest2JUnitFileNameTransformer
  }
}
