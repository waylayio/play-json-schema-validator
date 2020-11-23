package com.eclipsesource.schema

import com.eclipsesource.schema.drafts.{Version4, Version7}
import com.eclipsesource.schema.test.JsonSpec
import org.specs2.mutable.Specification
import play.api.libs.json.Json

class RequiredSpec extends Specification with JsonSpec {

  "required draft4" in {
    import Version4._
    implicit val validator: SchemaValidator = SchemaValidator(Some(Version4))
    validate("required", "draft4")
  }

  "required draft7" in {
    import Version7._
    implicit val validator: SchemaValidator = SchemaValidator(Some(Version7))
    validate("required", "draft7")
  }

  "missing property error should contain instance path to missing property" in {
    import Version4._
    val validator = SchemaValidator(Some(Version4))
    val recursiveSchema = JsonSource.schemaFromString(
      """{
        |  "properties": {
        |    "foo": {}
        |  },
        |  "required": ["foo"]
        |}""".stripMargin)

    val instance = Json.obj("bar" -> false)

    val result = validator.validate(recursiveSchema.get, instance)
    result.asEither must beLeft.like {
      case error => (error.toJson(0) \ "instancePath").get.as[String] == "/foo"
    }
    result.isError must beTrue
  }
}
