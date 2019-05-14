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
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.i18n.Messages
import play.api.test.{FakeApplication, FakeRequest}
import play.twirl.api.Html
import uk.gov.hmrc.tai.config.FeatureTogglesConfig
import uk.gov.hmrc.tai.util.viewHelpers.JsoupMatchers


class MainTemplateSpec extends PlaySpec with FakeTaiPlayApplication with MockitoSugar with JsoupMatchers {


  "main template" must {

    "include webchat script" when {
      "webchat toggle is on" in {
        when(mockFeatureTogglesConfig.webChatEnabled).thenReturn(true)
        document(customConfigView) must haveElementWithId("webchat-tag")
      }
    }

    "exclude webchat script" when {
      "webchat toggle is off" in {
        when(mockFeatureTogglesConfig.webChatEnabled).thenReturn(false)
        document(customConfigView) mustNot haveElementWithId("webchat-tag")
      }
    }
    
    "exclude webchat script" when {
      "webchat toggle is on and excludeWebchat true is passed in" in {
        when(mockFeatureTogglesConfig.webChatEnabled).thenReturn(true)
        document(customConfigView) must haveElementWithId("webchat-tag")
      }

    }
  }

  implicit val testTemplateRenderer = MockTemplateRenderer
  implicit val testPartialRetriever = MockPartialRetriever
  implicit val messages: Messages = play.api.i18n.Messages.Implicits.applicationMessages
  override lazy val fakeApplication = FakeApplication()
  val mockFeatureTogglesConfig = mock[FeatureTogglesConfig]

  def document(view : Html): Document = Jsoup.parse(view.toString())

  def customConfigView = views.html.main("Test")(Html("This is the main content"))(FakeRequest(), messages, testTemplateRenderer,testPartialRetriever,Some(mockFeatureTogglesConfig))












}
