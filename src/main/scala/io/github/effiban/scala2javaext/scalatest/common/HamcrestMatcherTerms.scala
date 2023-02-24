package io.github.effiban.scala2javaext.scalatest.common

import scala.meta.{Term, XtensionQuasiquoteTerm}

object HamcrestMatcherTerms {

  val AssertThat: Term.Name = q"assertThat"

  val IsTrue: Term.Apply = q"is(true)"
  val Is: Term.Name = q"is"

  val HasSize: Term.Name = q"hasSize"

  val StartsWith: Term.Name = q"startsWith"
  val EndsWith: Term.Name = q"endsWith"
  val ContainsString: Term.Name = q"containsString"
  val MatchesPattern: Term.Name = q"matchesPattern"
  val MatchesRegex: Term.Name = q"matchesRegex"

  val GreaterThan: Term.Name = q"greaterThan"
  val GreaterThanOrEqualTo: Term.Name = q"greaterThanOrEqualTo"
  val LessThan: Term.Name = q"lessThan"
  val LessThanOrEqualTo: Term.Name = q"lessThanOrEqualTo"

  val SameInstance: Term.Name = q"sameInstance"
  val IsA: Term.Name = q"isA"
}
