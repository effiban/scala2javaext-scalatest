package io.github.effiban.scala2javaext.scalatest.predicates

import io.github.effiban.scala2java.spi.entities.InvocationArgCoordinates
import io.github.effiban.scala2java.spi.predicates.InvocationArgByNamePredicate

import scala.meta.{Term, XtensionQuasiquoteTerm}

object JUnitInvocationArgByNamePredicate extends InvocationArgByNamePredicate {

  override def apply(argCoords: InvocationArgCoordinates): Boolean = argCoords match {
    case InvocationArgCoordinates(Term.Apply(q"assertThrows", _), _, 1) => true
    case _ => false
  }
}
