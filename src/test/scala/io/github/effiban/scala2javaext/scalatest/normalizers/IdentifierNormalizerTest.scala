package io.github.effiban.scala2javaext.scalatest.normalizers

import io.github.effiban.scala2javaext.scalatest.normalizers.IdentifierNormalizer.{toClassName, toMemberName}
import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

class IdentifierNormalizerTest extends UnitTestSuite {

  private val ToMemberNameScenarios = Table(
    ("Identifier", "MemberName"),
    ("abc", "abc"),
    ("Abc", "abc"),
    ("ABC", "abc"),
    ("abc123", "abc123"),
    ("abC dEf Ghi", "abcDefGhi"),
    ("abc 'def' %%&&^^", "abcDef"),
  )

  private val ToClassNameScenarios = Table(
    ("Identifier", "ClassName"),
    ("Abc", "Abc"),
    ("ABC", "Abc"),
    ("abc", "Abc"),
    ("abc123", "Abc123"),
    ("abC dEf ghI", "AbcDefGhi"),
    ("abc 'def' %%&&^^", "AbcDef"),
  )

  forAll(ToMemberNameScenarios) { case (identifier: String, normalizedIdentifier: String) =>
    test(s"The identifier '$identifier' should be normalized to member name '$normalizedIdentifier'") {
      toMemberName(identifier) shouldBe normalizedIdentifier
    }
  }

  forAll(ToClassNameScenarios) { case (identifier: String, normalizedIdentifier: String) =>
    test(s"The identifier '$identifier' should be normalized to class name '$normalizedIdentifier'") {
      toClassName(identifier) shouldBe normalizedIdentifier
    }
  }
}
