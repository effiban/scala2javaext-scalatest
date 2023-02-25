package io.github.effiban.scala2javaext.scalatest.transformers.assertions.matchers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Term, XtensionQuasiquoteTerm}

class WordAndValuesMatcherTransformerTest extends UnitTestSuite {

  test("transform for a Term.Apply with word 'valid' and expected args, should return expected matcher") {
    val matcher = q"valid(3, 4)"
    val expectedMatcher = q"matches(3, 4)"

    TestMatcherTransformer.transform(matcher).value.structure shouldBe expectedMatcher.structure
  }

  test("transform for a Term.Apply with word 'invalid' should return None") {
    val matcher = q"invalid(3, 4)"

    TestMatcherTransformer.transform(matcher) shouldBe None
  }

  test("transform for a Term.Name should return None") {
    val input = q"dummy"

    TestMatcherTransformer.transform(input) shouldBe None
  }

  private object TestMatcherTransformer extends WordAndValuesMatcherTransformer {
    override protected[matchers] def transform(word: Term.Name, values: List[Term]): Option[Term] = word match {
      case q"valid" => Some(Term.Apply(q"matches", values))
      case _ => None
    }
  }
}
