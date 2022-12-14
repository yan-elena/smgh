package it.unibo.pps.smartgh.model.plants

import it.unibo.pps.smartgh.Config
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import java.io.FileNotFoundException

/** This class contains tests to verify that the [[UploadPlants]] works correctly. */
class UploadPlantsTest extends AnyFunSuite with Matchers:
  private val path = Config.Path
  private val file = Config.PlantsInputFile
  private val prologFile = Config.PlantsOutputFile
  private val uploader = UploadPlants

  test(s"$file file lines number should equal to 25") {
    uploader.countResourcesFileLines(file) shouldEqual 25
  }

  test(s"$prologFile should have the same number of lines of $file") {
    uploader.writePrologFile(path, file, prologFile)
    uploader.countPrologFileLines(path + prologFile) shouldEqual uploader.countResourcesFileLines(file)
  }

  test("passing a file that does not exist should yield a FileNotFoundException") {
    assertThrows[FileNotFoundException](uploader.getBufferedSource("noFile"))
  }
