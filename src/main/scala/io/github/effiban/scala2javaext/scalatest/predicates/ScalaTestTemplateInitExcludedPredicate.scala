package io.github.effiban.scala2javaext.scalatest.predicates

import io.github.effiban.scala2java.spi.predicates.TemplateInitExcludedPredicate
import io.github.effiban.scala2javaext.scalatest.common.ScalaTestConstants.ScalaTestRootPackage

import scala.meta.{Init, Term, Type, XtensionQuasiquoteType}


object ScalaTestTemplateInitExcludedPredicate extends TemplateInitExcludedPredicate {

  // Although long it is by no means an exhaustive set, I tried to choose the most common ones
  // Please maintain alphabetical order!!
  private val CommonExtensibleTypeNames = Set(
    t"AnyFeatureSpec",
    t"AnyFeatureSpecLike",
    t"AnyFlatSpec",
    t"AnyFlatSpecLike",
    t"AnyFreeSpec",
    t"AnyFreeSpecLike",
    t"AnyFunSpec",
    t"AnyFunSpecLike",
    t"AnyFunSuite",
    t"AnyFunSuiteLike",
    t"AnyPropSpec",
    t"AnyPropSpecLike",
    t"AnyWordSpec",
    t"AnyWordSpecLike",
    t"AsyncCancelAfterFailure",
    t"AsyncConfigMapFixture",
    t"AsyncFeatureSpec",
    t"AsyncFeatureSpecLike",
    t"AsyncFlatSpec",
    t"AsyncFlatSpecLike",
    t"AsyncFreeSpec",
    t"AsyncFreeSpecLike",
    t"AsyncFunSpec",
    t"AsyncFunSpecLike",
    t"AsyncFunSuite",
    t"AsyncFunSuiteLike",
    t"AsyncTestDataFixture",
    t"AsyncTestSuite",
    t"AsyncTestSuiteMixin",
    t"AsyncTimeLimitedTests",
    t"AsyncWordSpec",
    t"AsyncWordSpecLike",
    t"BeforeAndAfter",
    t"BeforeAndAfterAll",
    t"BeforeAndAfterAllConfigMap",
    t"BeforeAndAfterEach",
    t"BeforeAndAfterEachTestData",
    t"CancelAfterFailure",
    t"ConfigMapFixture",
    t"DocSpecLike",
    t"EitherValues",
    t"FixtureAnyFeatureSpec",
    t"FixtureAnyFeatureSpecLike",
    t"FixtureAnyFlatSpec",
    t"FixtureAnyFlatSpecLike",
    t"FixtureAnyFreeSpec",
    t"FixtureAnyFreeSpecLike",
    t"FixtureAnyFunSpec",
    t"FixtureAnyFunSpecLike",
    t"FixtureAnyFunSuite",
    t"FixtureAnyFunSuiteLike",
    t"FixtureAnyPropSpec",
    t"FixtureAnyPropSpecLike",
    t"FixtureAnyWordSpec",
    t"FixtureAnyWordSpecLike",
    t"FixtureAsyncFeatureSpec",
    t"FixtureAsyncFeatureSpecLike",
    t"FixtureAsyncFlatSpec",
    t"FixtureAsyncFlatSpecLike",
    t"FixtureAsyncFreeSpec",
    t"FixtureAsyncFreeSpecLike",
    t"FixtureAsyncFunSpec",
    t"FixtureAsyncFunSpecLike",
    t"FixtureAsyncFunSuite",
    t"FixtureAsyncFunSuiteLike",
    t"FixtureAsyncTestSuite",
    t"FixtureAsyncWordSpec",
    t"FixtureAsyncWordSpecLike",
    t"FixtureSuite",
    t"GivenWhenThen",
    t"Matchers",
    t"OneInstancePerTest",
    t"OptionValues",
    t"ParallelTestExecution",
    t"PathAnyFreeSpec",
    t"PathAnyFreeSpecLike",
    t"PathAnyFunSpec",
    t"PathAnyFunSpecLike",
    t"RefSpec",
    t"RefSpecLike",
    t"SpanSugar",
    t"StepwiseNestedSuiteExecution",
    t"StopOnFailure",
    t"Suite",
    t"SuiteMixin",
    t"TableDrivenPropertyChecks",
    t"TestDataFixture",
    t"TestSuite",
    t"TestSuiteMixin",
    t"TimeLimitedTests",
    t"TryValues",
    t"UnitFixture"
  )

  override def apply(init: Init): Boolean = init.tpe match {
    case typeName: Type.Name => CommonExtensibleTypeNames.exists(_.structure == typeName.structure)
    case tpe if isUnderScalaTestPackage(tpe) => true
    case _ => false
  }

  private def isUnderScalaTestPackage(tpe: Type): Boolean = {
    tpe.collect { case termSelect: Term.Select => termSelect }
      .exists(_.structure == ScalaTestRootPackage.structure)
  }
}
