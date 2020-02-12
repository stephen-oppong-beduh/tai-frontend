/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.tai.service

import org.joda.time.LocalDate
import org.scalatestplus.play.PlaySpec
import uk.gov.hmrc.tai.model.domain._
import uk.gov.hmrc.tai.viewModels.PaymentDetailsViewModel

class PaymentsServiceSpec extends PlaySpec {

  val paymentsService = new PaymentsService

  val testDate = new LocalDate(2019, 8, 8)

  val taxableIncome = 1234
  val taxAmount = 4567
  val nationalInsuranceAmount = 789

  def createEmployment(payments: Seq[Payment]): Employment = {
    val annualAccounts = Seq(AnnualAccount("KEY", uk.gov.hmrc.tai.model.TaxYear(), Available, payments, Nil))
    Employment(
      "test employment",
      Some("EMPLOYER1"),
      testDate,
      None,
//      annualAccounts,
      "",
      "",
      2,
      None,
      false,
      false)
  }

  "filterDuplicates" should {

    "convert empty annualAccounts to an empty PaymentDetailsViewModel" in {
      val payments = Seq(
        Payment(testDate, 123, 456, 7890, taxableIncome, taxAmount, nationalInsuranceAmount, Weekly, Some(false))
      )

      val emptyAnnualAccounts = Seq.empty[AnnualAccount]
      val employment = Employment(
        "test employment",
        Some("EMPLOYER1"),
        testDate,
        None,
//        emptyAnnualAccounts,
        "",
        "",
        2,
        None,
        false,
        false)

      paymentsService.filterDuplicates(employment) mustBe Seq.empty[PaymentDetailsViewModel]
    }

    //TODO: Restore/rewrite when accounts restored.
    "convert Employment to a list of PaymentDetailsViewModel" ignore {
      val payments = Seq(
        Payment(testDate, 123, 456, 7890, taxableIncome, taxAmount, nationalInsuranceAmount, Weekly, Some(false))
      )

      val employment = createEmployment(payments)

      val expectedPaymentDetails = Seq(
        PaymentDetailsViewModel(testDate, taxableIncome, taxAmount, nationalInsuranceAmount)
      )

      paymentsService.filterDuplicates(employment) mustBe expectedPaymentDetails
    }

    "remove duplicate payment, returning an empty PaymentDetailsViewModel" in {
      val payments = Seq(
        Payment(testDate, 123, 456, 7890, taxableIncome, taxAmount, nationalInsuranceAmount, Weekly, Some(true))
      )

      val employment = createEmployment(payments)

      paymentsService.filterDuplicates(employment) mustBe Seq.empty[PaymentDetailsViewModel]
    }

    //TODO: Restore/rewrite when accounts restored.
    "remove duplicate payments, keeping the non duplicate payments as PaymentDetailsViewModels" ignore {
      val payments = Seq(
        Payment(testDate, 123, 456, 888, 999, 1010, 1111, Weekly, Some(true)),
        Payment(testDate, 123, 456, 7890, taxableIncome, taxAmount, nationalInsuranceAmount, Weekly, None)
      )

      val employment = createEmployment(payments)

      val expectedPaymentDetails = Seq(
        PaymentDetailsViewModel(testDate, taxableIncome, taxAmount, nationalInsuranceAmount)
      )

      paymentsService.filterDuplicates(employment) mustBe expectedPaymentDetails
    }
  }
}
