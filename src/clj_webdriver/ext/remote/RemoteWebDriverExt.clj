(ns ^{:doc "Custom implementation of RemoteWebDriver, adding support for screenshots."}
  clj-webdriver.ext.remote.RemoteWebDriverExt
  (:import [org.openqa.selenium.remote DriverCommand RemoteWebDriver])
  (:require [clojure.tools.logging :as lg])
  (:gen-class
   :main false
   :extends org.openqa.selenium.remote.RemoteWebDriver
   :implements [org.openqa.selenium.TakesScreenshot]))

(defn -log [this session-id command-name to-log when]
  (when (-> (System/getenv "RFZ_QA_SERVER") (= "rfz-selenium"))
    (lg/info "SELENIUM!!!" session-id command-name to-log when)))

(defn -getScreenshotAs
  [this target]
  (let [base64 (->> DriverCommand/SCREENSHOT
                    (.execute this)
                    .getValue
                    str)]
    (.convertFromBase64Png target base64)))

