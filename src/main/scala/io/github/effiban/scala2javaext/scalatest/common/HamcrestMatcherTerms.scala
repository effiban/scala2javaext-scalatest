package io.github.effiban.scala2javaext.scalatest.common

import scala.meta.{Term, XtensionQuasiquoteTerm}

object HamcrestMatcherTerms {

  val AssertThat: Term.Select = q"org.hamcrest.MatcherAssert.assertThat"

  val IsTrue: Term.Apply = q"org.hamcrest.Matchers.is(true)"
  val Is: Term.Select = q"org.hamcrest.Matchers.is"

  val HasSize: Term.Select = q"org.hamcrest.Matchers.hasSize"

  val StartsWith: Term.Select = q"org.hamcrest.Matchers.startsWith"
  val EndsWith: Term.Select = q"org.hamcrest.Matchers.endsWith"
  val ContainsString: Term.Select = q"org.hamcrest.Matchers.containsString"
  val MatchesPattern: Term.Select = q"org.hamcrest.Matchers.matchesPattern"
  val MatchesRegex: Term.Select = q"org.hamcrest.Matchers.matchesRegex"

  val GreaterThan: Term.Select = q"org.hamcrest.Matchers.greaterThan"
  val GreaterThanOrEqualTo: Term.Select = q"org.hamcrest.Matchers.greaterThanOrEqualTo"
  val LessThan: Term.Select = q"org.hamcrest.Matchers.lessThan"
  val LessThanOrEqualTo: Term.Select = q"org.hamcrest.Matchers.lessThanOrEqualTo"

  val SameInstance: Term.Select = q"org.hamcrest.Matchers.sameInstance"
  val IsA: Term.Select = q"org.hamcrest.Matchers.isA"

  val Empty: Term.Select = q"org.hamcrest.Matchers.empty"

  val HasItem: Term.Select = q"org.hamcrest.Matchers.hasItem"
  val HasItems: Term.Select = q"org.hamcrest.Matchers.hasItems"

  val Contains: Term.Select = q"org.hamcrest.Matchers.contains"
  val ContainsInAnyOrder: Term.Select = q"org.hamcrest.Matchers.containsInAnyOrder"
  val ContainsInRelativeOrder: Term.Select = q"org.hamcrest.Matchers.containsInRelativeOrder"

  val Not: Term.Select = q"org.hamcrest.Matchers.not"

  val AllOf: Term.Select = q"org.hamcrest.Matchers.allOf"
  val AnyOf: Term.Select = q"org.hamcrest.Matchers.anyOf"

}
