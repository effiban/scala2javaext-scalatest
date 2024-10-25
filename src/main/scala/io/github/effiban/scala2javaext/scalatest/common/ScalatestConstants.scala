package io.github.effiban.scala2javaext.scalatest.common

import scala.meta.{Term, Type, XtensionQuasiquoteTerm, XtensionQuasiquoteType}

object ScalatestConstants {

  val RootPackage: Term.Select = q"org.scalatest"

  val TestSuperclasses: Set[Type.Select] = Set(
    t"org.scalatest.featurespec.AnyFeatureSpec",
    t"org.scalatest.featurespec.AsyncFeatureSpec",
    t"org.scalatest.featurespec.FixtureAnyFeatureSpec",
    t"org.scalatest.featurespec.FixtureAsyncFeatureSpec",
    t"org.scalatest.flatspec.AnyFlatSpec",
    t"org.scalatest.flatspec.AsyncFlatSpec",
    t"org.scalatest.flatspec.FixtureAnyFlatSpec",
    t"org.scalatest.flatspec.FixtureAsyncFlatSpec",
    t"org.scalatest.freespec.AnyFreeSpec",
    t"org.scalatest.freespec.AsyncFreeSpec",
    t"org.scalatest.freespec.FixtureAnyFreeSpec",
    t"org.scalatest.freespec.FixtureAsyncFreeSpec",
    t"org.scalatest.freespec.PathAnyFreeSpec",
    t"org.scalatest.funspec.AnyFunSpec",
    t"org.scalatest.funspec.AsyncFunSpec",
    t"org.scalatest.funspec.FixtureAnyFunSpec",
    t"org.scalatest.funspec.FixtureAsyncFunSpec",
    t"org.scalatest.funspec.PathAnyFunSpec",
    t"org.scalatest.funsuite.AnyFunSuite",
    t"org.scalatest.funsuite.AsyncFunSuite",
    t"org.scalatest.funsuite.FixtureAnyFunSuite",
    t"org.scalatest.propspec.AnyPropSpec",
    t"org.scalatest.propspec.FixtureAnyPropSpec",
    t"org.scalatest.refspec.RefSpec",
    t"org.scalatest.wordspec.AnyWordSpec",
    t"org.scalatest.wordspec.AsyncWordSpec",
    t"org.scalatest.wordspec.FixtureAnyWordSpec",
    t"org.scalatest.wordspec.FixtureAsyncWordSpec"
  )

  val TestClassSuffixes: Set[String] = Set("Spec", "Suite")

  val Equal: Term.Name = q"equal"
  val Be: Term.Name = q"be"
  val Empty: Term.Name = q"empty"

  val AssertionsType: Type.Select = t"org.scalatest.Assertions"
}
