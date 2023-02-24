package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.XtensionQuasiquoteTerm

class ContainMatcherTransformerTest extends UnitTestSuite {

  private val containMatcherTransformer = new ContainMatcherTransformer()

  test("transform() a Term.Apply when fun is 'contain' with one arg, should return Hamcrest 'hasItem'") {
    val matcher = q"contain(3)"
    val expectedHamcrestMatcher = q"hasItem(3)"

    containMatcherTransformer.transform(matcher).value.structure shouldBe expectedHamcrestMatcher.structure
  }

  test("transform() a Term.Apply when fun is 'contain' with two args, should return None") {
    val matcher = q"contain(3, 4)"

    containMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform() a Term.Apply when fun is not 'contain', should return None") {
    val matcher = q"include(3)"

    containMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform() a Term.Name should return None") {
    val matcher = q"contain"

    containMatcherTransformer.transform(matcher) shouldBe None
  }
}
