package io.github.effiban.scala2javaext.scalatest.predicates

import io.github.effiban.scala2javaext.scalatest.testsuites.UnitTestSuite

import scala.meta.{Init, Name, Type, XtensionQuasiquoteType}

class ScalaTestTemplateInitExcludedPredicateTest extends UnitTestSuite {

  private val Scenarios = Table(
    ("Type Name", "ExpectedExcluded"),
    (t"AnyFeatureSpec", true),
    (t"AnyFeatureSpecLike", true),
    (t"AnyFlatSpec", true),
    (t"AnyFlatSpecLike", true),
    (t"AnyFreeSpec", true),
    (t"AnyFreeSpecLike", true),
    (t"AnyFunSpec", true),
    (t"AnyFunSpecLike", true),
    (t"AnyFunSuite", true),
    (t"AnyFunSuiteLike", true),
    (t"AnyPropSpec", true),
    (t"AnyPropSpecLike", true),
    (t"AnyWordSpec", true),
    (t"AnyWordSpecLike", true),
    (t"AsyncCancelAfterFailure", true),
    (t"AsyncConfigMapFixture", true),
    (t"AsyncFeatureSpec", true),
    (t"AsyncFeatureSpecLike", true),
    (t"AsyncFlatSpec", true),
    (t"AsyncFlatSpecLike", true),
    (t"AsyncFreeSpec", true),
    (t"AsyncFreeSpecLike", true),
    (t"AsyncFunSpec", true),
    (t"AsyncFunSpecLike", true),
    (t"AsyncFunSuite", true),
    (t"AsyncFunSuiteLike", true),
    (t"AsyncTestDataFixture", true),
    (t"AsyncTestSuite", true),
    (t"AsyncTestSuiteMixin", true),
    (t"AsyncTimeLimitedTests", true),
    (t"AsyncWordSpec", true),
    (t"AsyncWordSpecLike", true),
    (t"BeforeAndAfter", true),
    (t"BeforeAndAfterAll", true),
    (t"BeforeAndAfterAllConfigMap", true),
    (t"BeforeAndAfterEach", true),
    (t"BeforeAndAfterEachTestData", true),
    (t"CancelAfterFailure", true),
    (t"ConfigMapFixture", true),
    (t"DocSpecLike", true),
    (t"EitherValues", true),
    (t"FixtureAnyFeatureSpec", true),
    (t"FixtureAnyFeatureSpecLike", true),
    (t"FixtureAnyFlatSpec", true),
    (t"FixtureAnyFlatSpecLike", true),
    (t"FixtureAnyFreeSpec", true),
    (t"FixtureAnyFreeSpecLike", true),
    (t"FixtureAnyFunSpec", true),
    (t"FixtureAnyFunSpecLike", true),
    (t"FixtureAnyFunSuite", true),
    (t"FixtureAnyFunSuiteLike", true),
    (t"FixtureAnyPropSpec", true),
    (t"FixtureAnyPropSpecLike", true),
    (t"FixtureAnyWordSpec", true),
    (t"FixtureAnyWordSpecLike", true),
    (t"FixtureAsyncFeatureSpec", true),
    (t"FixtureAsyncFeatureSpecLike", true),
    (t"FixtureAsyncFlatSpec", true),
    (t"FixtureAsyncFlatSpecLike", true),
    (t"FixtureAsyncFreeSpec", true),
    (t"FixtureAsyncFreeSpecLike", true),
    (t"FixtureAsyncFunSpec", true),
    (t"FixtureAsyncFunSpecLike", true),
    (t"FixtureAsyncFunSuite", true),
    (t"FixtureAsyncFunSuiteLike", true),
    (t"FixtureAsyncTestSuite", true),
    (t"FixtureAsyncWordSpec", true),
    (t"FixtureAsyncWordSpecLike", true),
    (t"FixtureSuite", true),
    (t"GivenWhenThen", true),
    (t"Matchers", true),
    (t"OneInstancePerTest", true),
    (t"OptionValues", true),
    (t"ParallelTestExecution", true),
    (t"PathAnyFreeSpec", true),
    (t"PathAnyFreeSpecLike", true),
    (t"PathAnyFunSpec", true),
    (t"PathAnyFunSpecLike", true),
    (t"RefSpec", true),
    (t"RefSpecLike", true),
    (t"SpanSugar", true),
    (t"StepwiseNestedSuiteExecution", true),
    (t"StopOnFailure", true),
    (t"Suite", true),
    (t"SuiteMixin", true),
    (t"TableDrivenPropertyChecks", true),
    (t"TestDataFixture", true),
    (t"TestSuite", true),
    (t"TestSuiteMixin", true),
    (t"TimeLimitedTests", true),
    (t"TryValues", true),
    (t"UnitFixture", true),
    (t"org.scalatest.Something", true),
    (t"org.scalatest.something.Else", true),
    (t"MyTrait", false)
  )

  forAll(Scenarios) { case (tpe: Type, expectedExcluded: Boolean) =>
    test(s"$tpe should ${if (expectedExcluded) "be" else "not be"} excluded") {
      val init = Init(tpe, Name.Anonymous(), List(Nil))
      ScalaTestTemplateInitExcludedPredicate(init) shouldBe expectedExcluded
    }
  }

}
