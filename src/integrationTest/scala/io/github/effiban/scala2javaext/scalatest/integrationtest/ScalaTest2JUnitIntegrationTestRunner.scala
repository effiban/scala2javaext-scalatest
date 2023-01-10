package io.github.effiban.scala2javaext.scalatest.integrationtest

import io.github.effiban.scala2java.test.utils.integration.runner.IntegrationTestRunner
import org.scalatest.funsuite.AnyFunSuite

import java.nio.file.{Path, Paths}

class ScalaTest2JUnitIntegrationTestRunner extends AnyFunSuite with IntegrationTestRunner {
  override protected def resolveTestFilesBasePath(): Path = Paths.get(getClass.getClassLoader.getResource("testfiles").toURI)
}
