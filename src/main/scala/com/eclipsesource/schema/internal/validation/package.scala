package com.eclipsesource.schema.internal

import play.api.data.validation.ValidationError
import play.api.libs.json.JsPath
import scalaz.Validation

package object validation {
  type Validated[E, O] = Validation[Seq[E], O]
  type Mapping[E, I, O] = I => Validated[E, O]
  type Constraint[T] = Mapping[ValidationError, T, T]
  type VA[O] = Validated[(JsPath, Seq[ValidationError]), O]
}
