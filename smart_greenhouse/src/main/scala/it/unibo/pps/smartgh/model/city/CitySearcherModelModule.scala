package it.unibo.pps.smartgh.model.city

import alice.tuprolog.{Prolog, SolveInfo, Struct, Term, Theory}

import scala.io.{Codec, Source}
import scala.util.Using

object CitySearcherModelModule:

  /** A trait exposing methods for managing city searches. */
  trait CitySearcherModel:

    /** Retrieves all cities.
      * @return
      *   a sequences of city names
      */
    def getAllCities: Seq[String]

    /** Method for searching cities beginning with the given characters.
      * @param charSequence
      *   a sequence of characters that the name of the city begins with
      * @return
      *   a sequence of city names
      */
    def searchCities(charSequence: Seq[Char]): Seq[String]

    /** Check whether all cities contain a given city.
      * @param city
      *   the city to test
      * @return
      *   true if this sequence has the given city, false otherwise
      */
    def containCity(city: String): Boolean

  trait Provider:
    val citySearcherModel: CitySearcherModel

  trait Component:
    class CitySearcherModelImpl(fileName: String) extends CitySearcherModel:
      import it.unibo.pps.smartgh.prolog.Scala2P.{*, given}
      private val prologFile = Using(Source.fromFile(fileName, enc = "UTF8"))(_.mkString).getOrElse("")
      private val engine = prologEngine(Theory.parseLazilyWithStandardOperators(prologFile))
      private val cities = engine("citta(X)").map(extractTerm).toSeq

      override def getAllCities: Seq[String] = cities

      override def searchCities(charSequence: Seq[Char]): Seq[String] =
        engine("ricerca_citta(" + charSequence.mkString("['", "','", "'|_]") + ", X)").map(extractTerm).toSeq

      override def containCity(city: String): Boolean = cities.contains(city)

      private def extractTerm(solveInfo: SolveInfo) = extractTermToString(solveInfo, "X").replace("'", "")

  trait Interface extends Provider with Component