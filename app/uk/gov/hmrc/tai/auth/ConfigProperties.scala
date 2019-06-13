/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.tai.auth

import javax.inject.Inject
import play.api.Configuration
import uk.gov.hmrc.tai.config.DefaultRunMode

class ConfigProperties @Inject()(configuration: Configuration) extends DefaultRunMode {

  val postSignInRedirectUrl: Option[String] = configuration.getOptional[String](s"govuk-tax.$env.login-callback.url")

  val activatePaperless: Boolean = configuration.getOptional[Boolean](s"govuk-tax.$env.activatePaperless")
    .getOrElse(throw new IllegalStateException(s"Could not find configuration for govuk-tax.$env.activatePaperless"))

  val activatePaperlessEvenIfGatekeeperFails: Boolean = configuration.getOptional[Boolean](s"govuk-tax.$env.activatePaperlessEvenIfGatekeeperFails")
    .getOrElse(throw new IllegalStateException(s"Could not find configuration for govuk-tax.$env.activatePaperless"))

  val taxPlatformTaiRootUri: String = configuration.getOptional[String](s"govuk-tax.$env.taxPlatformTaiRootUri").getOrElse("http://noConfigTaiRootUri")
}
