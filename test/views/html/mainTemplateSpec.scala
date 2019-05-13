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

package views.html

import controllers.FakeTaiPlayApplication
import mocks.{MockPartialRetriever, MockTemplateRenderer}
import org.joda.time.LocalDate
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import play.api.data.Form
import play.api.test.{FakeApplication, FakeRequest}
import play.twirl.api.Html
import uk.gov.hmrc.tai.config.FeatureTogglesConfig
import uk.gov.hmrc.tai.forms.YesNoTextEntryForm
import uk.gov.hmrc.tai.util.constants.FormValuesConstants
import uk.gov.hmrc.tai.util.viewHelpers.TaiViewSpec
import uk.gov.hmrc.tai.viewModels.employments.WithinSixWeeksViewModel
import uk.gov.hmrc.tai.viewModels.{CanWeContactByPhoneViewModel, CompanyBenefitViewModel, IncomeSourceSummaryViewModel}

class mainTemplateSpec extends TaiViewSpec with FakeTaiPlayApplication with MockitoSugar {

  override def view: Html = views.html.employments.endEmploymentWithinSixWeeksError(model)



//  override def view = views.html.main("Test")(Html("This is the main content"))(FakeRequest(), messages, testTemplateRenderer,testPartialRetriever)

  "main template" must {

    "include webchat script" when {

     // behave like pageWithTitle("teestestestes")

      "webchat is toggled on" in {



        println(Console.YELLOW + "View contains --> {" + view + "}"+ Console.WHITE)

//        when(mockFeatureTogglesConfig.webChatEnabled).thenReturn(true)
        doc must haveElementWithId("webchat-tag")
      }

    }


//    "not include webchat script" when {
//
//      "webchat is toggled on but include nuance webchat is false" in {
//
//
//        doc mustNot haveElementWithId("header")
//
//      }
//
//    }
  }

//  implicit val testTemplateRenderer = MockTemplateRenderer
//  implicit val testPartialRetriever = MockPartialRetriever
//  override lazy val fakeApplication = FakeApplication()

  val mockFeatureTogglesConfig = mock[FeatureTogglesConfig]

  private val employerName = "Employer"
  private lazy val earliestUpdateDate = new LocalDate(2017, 6, 20)
  private lazy val latestPayDate = new LocalDate(2016, 5, 10)
  private lazy val model = WithinSixWeeksViewModel(earliestUpdateDate, employerName, latestPayDate, 2)

}
