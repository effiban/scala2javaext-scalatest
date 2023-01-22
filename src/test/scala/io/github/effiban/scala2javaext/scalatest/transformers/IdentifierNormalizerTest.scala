package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

class IdentifierNormalizerTest extends UnitTestSuite {

  private val ToMemberNameScenarios = Table(
    ("Identifier", "MemberName"),
    ("abc", "abc"),
    ("abc123", "abc123"),
    ("Abc", "abc"),
    ("abc def ghi", "abcDefGhi"),
    ("abc 'def' %%&&^^", "abcDef"),
  )

  forAll(ToMemberNameScenarios) { case (identifier: String, normalizedIdentifier: String) =>
    test(s"The identifier '$identifier' should be normalized to '$normalizedIdentifier'") {
      IdentifierNormalizer.toMemberName(identifier) shouldBe normalizedIdentifier
    }
  }
}
