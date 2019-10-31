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

import builders.{UserBuilder}
import controllers.FakeTaiPlayApplication
import mocks.{MockPartialRetriever, MockTemplateRenderer}
import org.jsoup.Jsoup
import org.scalatestplus.play.PlaySpec
import play.api.i18n.Messages.Implicits._
import play.api.i18n.MessagesApi
import play.api.test.FakeRequest
import uk.gov.hmrc.tai.util.viewHelpers.JsoupMatchers

class getHelpPageSpec extends PlaySpec with FakeTaiPlayApplication with JsoupMatchers {

  implicit val request = FakeRequest("GET", "")
  implicit val user = UserBuilder()
  implicit val templateRenderer = MockTemplateRenderer
  implicit val partialRetriever = MockPartialRetriever
  implicit val messagesApi: MessagesApi = app.injector.instanceOf[MessagesApi]

  "show get help page" must {

    "show the correct page title and content" in {

      val html = views.html.help.getHelp()

      val doc = Jsoup.parseBodyFragment(html.toString)
      doc must haveHeadingWithText(messagesApi("tai.getHelp.h1"))
      doc must haveParagraphWithText(messagesApi("tai.getHelp.p1.with.acronym"))

    }
  }

}
