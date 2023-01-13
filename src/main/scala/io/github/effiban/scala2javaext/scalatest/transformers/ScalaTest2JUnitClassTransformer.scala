package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.ClassTransformer

import scala.meta.{Defn, Type}

class ScalaTest2JUnitClassTransformer(classNameTransformer: ClassNameTransformer) extends ClassTransformer {

  override def transform(defnClass: Defn.Class): Defn.Class = {
    import defnClass._
    val newName = Type.Name(classNameTransformer.transform(name.value, templ.inits))
    defnClass.copy(name = newName)
  }
}

object ScalaTest2JUnitClassTransformer extends ScalaTest2JUnitClassTransformer(ClassNameTransformer)
