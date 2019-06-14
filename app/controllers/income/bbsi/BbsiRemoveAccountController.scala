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

package controllers.income.bbsi


import controllers.TaiBaseController
import controllers.actions.ValidatePerson
import controllers.auth.AuthAction
import javax.inject.Inject
import play.api.mvc.{Action, AnyContent, MessagesControllerComponents}
import uk.gov.hmrc.domain.Nino
import uk.gov.hmrc.play.partials.FormPartialRetriever
import uk.gov.hmrc.renderer.TemplateRenderer
import uk.gov.hmrc.tai.service.BbsiService

import scala.concurrent.ExecutionContext


class BbsiRemoveAccountController @Inject()(bank_building_society_check_your_answers: views.html.incomes.bbsi.remove.Bank_building_society_check_your_answers,
                                            bbsiService: BbsiService,
                                            authenticate: AuthAction,
                                            validatePerson: ValidatePerson,
                                            mcc: MessagesControllerComponents,
                                            override implicit val partialRetriever: FormPartialRetriever,
                                            override implicit val templateRenderer: TemplateRenderer)
                                           (implicit ec: ExecutionContext)
  extends TaiBaseController(mcc) {

  def checkYourAnswers(id: Int): Action[AnyContent] = (authenticate andThen validatePerson).async {
    implicit request =>
      implicit val user = request.taiUser
      bbsiService.bankAccount(Nino(user.getNino), id) map {
        case Some(bankAccount) =>
          Ok(bank_building_society_check_your_answers(id, bankAccount.bankName.getOrElse("")))
        case None => NotFound
      }
  }

  def submitYourAnswers(id: Int): Action[AnyContent] = (authenticate andThen validatePerson).async {
    implicit request =>
      implicit val user = request.taiUser

      bbsiService.removeBankAccount(Nino(user.getNino), id) map { _ =>
        Redirect(controllers.income.bbsi.routes.BbsiController.removeConfirmation())
      }
  }
}
