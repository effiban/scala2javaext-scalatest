package io.github.effiban.scala2javaext.scalatest.transformers

import io.github.effiban.scala2java.spi.transformers.ClassTransformer

import scala.meta.{Defn, Type}

class ScalatestClassTransformer(classModsTransformer: ClassModsTransformer, classNameTransformer: ClassNameTransformer) extends ClassTransformer {

  override def transform(defnClass: Defn.Class): Defn.Class = {
    import defnClass._
    val newMods = classModsTransformer.transform(mods)
    val newName = Type.Name(classNameTransformer.transform(name.value, templ.inits))
    defnClass.copy(mods = newMods, name = newName)
  }
}

object ScalatestClassTransformer extends ScalatestClassTransformer(ClassModsTransformer, ClassNameTransformer)
