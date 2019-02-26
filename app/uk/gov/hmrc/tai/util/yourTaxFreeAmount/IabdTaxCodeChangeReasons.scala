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

package uk.gov.hmrc.tai.util.yourTaxFreeAmount

import play.api.i18n.Messages
import uk.gov.hmrc.play.views.helpers.MoneyPounds
import uk.gov.hmrc.tai.model.domain._
import uk.gov.hmrc.tai.util.MonetaryUtil

class IabdTaxCodeChangeReasons extends IabdMessageGroups {

  def reasons(iabdPairs: AllowancesAndDeductionPairs)(implicit messages: Messages): Seq[String] = {

    val combinedBenefits = iabdPairs.allowances ++ iabdPairs.deductions

    val whatsChangedPairs = combinedBenefits.filter(pair => pair.previous.isDefined && pair.current.isDefined)
    val whatsNewPairs = combinedBenefits.filter(pair => pair.previous.isEmpty && pair.current.isDefined)

    whatsNewPairs.map(translateNewBenefits(_)) ++
    whatsChangedPairs.flatMap(translateChangedBenefits(_))
  }

  private def translateNewBenefits(pair: CodingComponentPair)(implicit  messages: Messages): String = {
    val componentType = pair.componentType

    val isNowGet: Boolean = (youNowGetBenefits filter (_ == componentType)).nonEmpty
    val isHaveClaimed: Boolean = (youHaveClaimedBenefits filter (_ == componentType)).nonEmpty

    if(isNowGet) {
      messages("tai.taxCodeComparison.iabd.you.now.get", CodingComponentTypeDescription.componentTypeToString(componentType))
    } else if (isHaveClaimed) {
      messages("tai.taxCodeComparison.iabd.you.have.claimed", CodingComponentTypeDescription.componentTypeToString(componentType))
    } else if(componentType == EarlyYearsAdjustment) {
      messages("tai.taxCodeComparison.iabd.you.have.claimed.expenses")
    } else if(componentType == UnderPaymentFromPreviousYear) {
      pair.current match {
        case Some(value) => messages("tai.taxCodeComparison.iabd.you.have.underpaid", MonetaryUtil.withPoundPrefix(value.toInt))
        case None => genericBenefitMessage
      }
    } else if(componentType == EstimatedTaxYouOweThisYear) {
      pair.current match {
        case Some(value) => messages("tai.taxCodeComparison.iabd.we.estimated.you.have.underpaid", MonetaryUtil.withPoundPrefix(value.toInt))
        case None => genericBenefitMessage
      }
    }
    else {
      genericBenefitMessage
    }
  }

  private def translateChangedBenefits(pair: CodingComponentPair)(implicit messages: Messages): Option[String] = {
    val hasAnythingChanged: Boolean = pair match {
      case CodingComponentPair(_, _, previousAmount: Some[BigDecimal], currentAmount: Some[BigDecimal]) =>
        currentAmount.x != previousAmount.x
      case _ => false
    }

    (hasAnythingChanged) match {
      case true => yourBenefitsUpdatedMessage(pair.componentType)
      case false => None
    }
  }

  private def yourBenefitsUpdatedMessage(componentType: TaxComponentType)(implicit messages: Messages): Option[String] = {

    val isHaveBeen: Boolean = (haveBeenAllowances filter (_ == componentType)).nonEmpty

    val isNeitherHasOrHaveBeen: Boolean = (hasBeenAllowances filter (_ == componentType)).isEmpty && !isHaveBeen

    if(isNeitherHasOrHaveBeen) {
      Some(genericBenefitMessage)
    } else if (isHaveBeen) {
      Some(messages("tai.taxCodeComparison.iabd.have.been.updated", CodingComponentTypeDescription.componentTypeToString(componentType)))
    } else {
      Some(messages("tai.taxCodeComparison.iabd.has.been.updated", CodingComponentTypeDescription.componentTypeToString(componentType)))
    }
  }

  private def genericBenefitMessage(implicit messages: Messages): String = {
    messages("taxCode.change.yourTaxCodeChanged.paragraph")
  }
}
