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

package controllers.audit

import controllers.auth.AuthedUser
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.audit.http.connector.AuditConnector

trait Auditable {

  def auditConnector: AuditConnector

  def sendActingAttorneyAuditEvent(auditType : String)(implicit hc: HeaderCarrier, user: AuthedUser) = {

//    if(user.authContext.isDelegating) {
//      val auditEvent = DataEvent(
//        auditSource = "tai-frontend",
//        auditType = auditType,
//        tags = hc.headers.toMap,
//        detail = Map(
//          "attorneyName" -> user.authContext.attorney.fold("")(_.name),
//          "nino" ->  user.getNino,
//          "utr" -> user.getUTR
//        )
//      )
//      val auditResponse = auditConnector.sendEvent(auditEvent)
//    }

  }
}
