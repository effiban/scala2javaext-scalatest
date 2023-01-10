package io.github.effiban.scala2javaext.scalatest.providers

import io.github.effiban.scala2java.spi.providers.{AdditionalImportersProvider, ExtendedProviders}

trait ScalaTest2JUnitProviders extends ExtendedProviders {

  override def additionalImportersProvider(): AdditionalImportersProvider = JUnitImportersProvider
}
