package entity;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.util.concurrent.atomic.AtomicInteger;

@DataObject(generateConverter = true)
public class Question {
    private static final AtomicInteger acc=new AtomicInteger(0);

    private int id;
    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;

    public Question(){

    }

    public Question(Question q) {
        this.id = q.id;
        this.question = q.question;
        this.answerA = q.answerA;
        this.answerB = q.answerB;
        this.answerC = q.answerC;
        this.answerD = q.answerD;
    }

    public Question(JsonObject object){
        QuestionConverter.fromJson(object,this);
    }

    public Question(int id, String question, String answerA, String answerB, String answerC, String answerD) {
        this.id = id;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
    }

    public JsonObject toJson(){
        JsonObject jsonObject=new JsonObject();
        QuestionConverter.toJson(this,jsonObject);
        return jsonObject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj) return true;
        if(obj==null||getClass()!=obj.getClass()) return false;

        Question q=(Question)obj;

        if(id!=q.id) return false;
        if(!question.equals(q.question)) return false;
        if(!answerA.equals(q.answerA)) return false;
        if(!answerB.equals(q.answerB)) return false;
        if(!answerC.equals(q.answerC)) return false;
        if(!answerD.equals(q.answerD)) return false;
        return true;
    }
}
