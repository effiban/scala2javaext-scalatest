package io.github.effiban.scala2javaext.scalatest.predicates

import io.github.effiban.scala2java.spi.predicates.TemplateInitExcludedPredicate
import io.github.effiban.scala2javaext.scalatest.common.ScalaTestConstants.{TestSuperclasses, RootPackage}

import scala.meta.{Init, Term, Type, XtensionQuasiquoteType}


object ScalaTestTemplateInitExcludedPredicate extends TemplateInitExcludedPredicate {

  // Although long it is by no means an exhaustive set - I tried to choose the most common ones
  // besides the superclass (which are exhaustive)
  private val CommonExtensibleTypeNames = TestSuperclasses.map(_.name) ++
    Set(
      t"AnyFeatureSpecLike",
      t"AnyFlatSpecLike",
      t"AnyFreeSpecLike",
      t"AnyFunSpecLike",
      t"AnyFunSuiteLike",
      t"AnyPropSpecLike",
      t"AnyWordSpecLike",
      t"AsyncCancelAfterFailure",
      t"AsyncConfigMapFixture",
      t"AsyncFeatureSpecLike",
      t"AsyncFlatSpecLike",
      t"AsyncFreeSpecLike",
      t"AsyncFunSpecLike",
      t"AsyncFunSuiteLike",
      t"AsyncTestDataFixture",
      t"AsyncTestSuite",
      t"AsyncTestSuiteMixin",
      t"AsyncTimeLimitedTests",
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
      t"FixtureAnyFeatureSpecLike",
      t"FixtureAnyFlatSpecLike",
      t"FixtureAnyFreeSpecLike",
      t"FixtureAnyFunSpecLike",
      t"FixtureAnyFunSuiteLike",
      t"FixtureAnyPropSpecLike",
      t"FixtureAnyWordSpecLike",
      t"FixtureAsyncFeatureSpecLike",
      t"FixtureAsyncFlatSpecLike",
      t"FixtureAsyncFreeSpecLike",
      t"FixtureAsyncFunSpecLike",
      t"FixtureAsyncFunSuite",
      t"FixtureAsyncFunSuiteLike",
      t"FixtureAsyncTestSuite",
      t"FixtureAsyncWordSpecLike",
      t"FixtureSuite",
      t"GivenWhenThen",
      t"Matchers",
      t"OneInstancePerTest",
      t"OptionValues",
      t"ParallelTestExecution",
      t"PathAnyFreeSpecLike",
      t"PathAnyFunSpecLike",
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
      .exists(_.structure == RootPackage.structure)
  }
}
