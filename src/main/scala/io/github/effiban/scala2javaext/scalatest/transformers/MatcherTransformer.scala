package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2javaext.scalatest.classifiers.ScalatestTermNameClassifier
import io.github.effiban.scala2javaext.scalatest.common.JUnitConstants.{HamcrestAssertThat, HamcrestIs}

import scala.meta.Term

trait MatcherTransformer {

  def transform(matcher: Term): Option[Term]
}