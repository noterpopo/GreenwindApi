/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package entity;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;

/**
 * Converter for {@link entity.Question}.
 *
 * NOTE: This class has been automatically generated from the {@link entity.Question} original class using Vert.x codegen.
 */
public class QuestionConverter {

  public static void fromJson(JsonObject json, Question obj) {
    if (json.getValue("answerA") instanceof String) {
      obj.setAnswerA((String)json.getValue("answerA"));
    }
    if (json.getValue("answerB") instanceof String) {
      obj.setAnswerB((String)json.getValue("answerB"));
    }
    if (json.getValue("answerC") instanceof String) {
      obj.setAnswerC((String)json.getValue("answerC"));
    }
    if (json.getValue("answerD") instanceof String) {
      obj.setAnswerD((String)json.getValue("answerD"));
    }
    if (json.getValue("id") instanceof Number) {
      obj.setId(((Number)json.getValue("id")).intValue());
    }
    if (json.getValue("question") instanceof String) {
      obj.setQuestion((String)json.getValue("question"));
    }
  }

  public static void toJson(Question obj, JsonObject json) {
    if (obj.getAnswerA() != null) {
      json.put("answerA", obj.getAnswerA());
    }
    if (obj.getAnswerB() != null) {
      json.put("answerB", obj.getAnswerB());
    }
    if (obj.getAnswerC() != null) {
      json.put("answerC", obj.getAnswerC());
    }
    if (obj.getAnswerD() != null) {
      json.put("answerD", obj.getAnswerD());
    }
    json.put("id", obj.getId());
    if (obj.getQuestion() != null) {
      json.put("question", obj.getQuestion());
    }
  }
}