package verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

import java.util.HashSet;
import java.util.Set;

public class SingleApplicationVerticle extends AbstractVerticle{
    private static final String HTTP_HOST = "0.0.0.0";
    private static final String MONGODB_HOST = "127.0.0.1";
    private static final int HTTP_PORT = 8082;
    private static final int MONGODB_PORT = 27017;
    public static final String COLLETCION_NAME="zhaoxin";

    private MongoClient client;
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        initData();
        Router router=Router.router(vertx);
        Set<String> allowHeaders=new HashSet<>();
        allowHeaders.add("x-requested-with");
        allowHeaders.add("Access-Control-Allow-Origin");
        allowHeaders.add("origin");
        allowHeaders.add("Content-Type");
        allowHeaders.add("accept");
        Set<HttpMethod> allowMethods=new HashSet<>();
        allowMethods.add(HttpMethod.GET);
        allowMethods.add(HttpMethod.POST);
        allowMethods.add(HttpMethod.DELETE);
        allowMethods.add(HttpMethod.PATCH);

        router.route().handler(CorsHandler.create("*")
        .allowedHeaders(allowHeaders)
        .allowedMethods(allowMethods));
        router.route().handler(BodyHandler.create());

        router.get(Constants.API_GET).handler(this::handleGetQuestion);
        router.get(Constants.API_LIST_ALL).handler(this::handleGetAll);
        router.post(Constants.API_CREATE).handler(this::handleCreateQuestion);
        router.patch(Constants.API_UPDATE).handler(this::handleUpdateTodo);
        router.delete(Constants.API_DELETE).handler(this::handleDeleteOne);
        router.delete(Constants.API_DELETE_ALL).handler(this::handleDeleteAll);


        vertx.createHttpServer(/*options*/)
                .requestHandler(router::accept)
                .listen(HTTP_PORT,HTTP_HOST,result->{
                    if(result.succeeded())
                        startFuture.complete();
                    else
                        startFuture.fail(result.cause());
                });
    }

    private void initData(){
        JsonObject config=new JsonObject();
        config.put("host",MONGODB_HOST).put("port",MONGODB_PORT).put("db_name","greenwind").put("username","popo").put("password","54336qaz55");
        client=MongoClient.createShared(vertx,config);
        JsonObject query=new JsonObject();
        query.put("connect","vertx");
        JsonObject update=new JsonObject().put("$set",new JsonObject().put("time",String.valueOf(System.currentTimeMillis())));
        client.updateCollection(COLLETCION_NAME,query,update,res->{
            if(res.succeeded()){
                System.out.println("Connect success!");
            }else {
                res.cause().printStackTrace();
            }
        });
    }

    private void handleGetQuestion(RoutingContext context){
        String id=context.request().getParam("questionId");
        if(id==null){
            sendError(400,context.response());
        }else {
            JsonObject query=new JsonObject();
            query.put("id",id);
            client.find(COLLETCION_NAME,query,res->{
                if(res.succeeded()){
                    if(res.result().isEmpty())
                        sendError(404, context.response());
                    else {
                        context.response()
                                .putHeader("content-type", "application/json")
                                .end(res.result().get(0).encodePrettily());
                    }
                }else {
                    sendError(503, context.response());
                }
            });
        }
    }

    private void handleGetAll(RoutingContext context){
        JsonObject query=new JsonObject();
        client.find(COLLETCION_NAME,query,res->{
            if(res.succeeded()){
                StringBuilder builder=new StringBuilder();
                for(JsonObject object:res.result()){
                    builder.append(object.encodePrettily());
                }
                context.response()
                        .putHeader("content-type", "application/json")
                        .end(builder.toString());
            }else {
                sendError(503, context.response());
            }
        });
    }
    private void handleCreateQuestion(RoutingContext context){
        client.insert(COLLETCION_NAME,context.getBodyAsJson(),res->{
            if(res.succeeded()){
                System.out.println("Inserted id "+res.result());
                context.response()
                        .setStatusCode(201)
                        .putHeader("content-type", "application/json")
                        .end();
            }else {
                sendError(503, context.response());
            }
        });
    }

    private void handleUpdateTodo(RoutingContext context){
        String id=context.request().getParam("questionId");
        if(id==null){
            sendError(400,context.response());
        }else {
            JsonObject query=new JsonObject();
            query.put("id",id);
            client.updateCollection(COLLETCION_NAME,query,new JsonObject().put("$set",context.getBodyAsJson()),res->{
                if(res.succeeded()){
                    context.response()
                            .setStatusCode(200)
                            .putHeader("content-type", "application/json")
                            .end();
                }else {
                    sendError(503, context.response());
                }
            });
        }
    }

    private void handleDeleteOne(RoutingContext context){
        String id=context.request().getParam("questionId");
        if(id==null){
            sendError(400,context.response());
        }else {
            JsonObject query=new JsonObject();
            query.put("id",id);
            client.removeDocument(COLLETCION_NAME,query,res->{
                if(res.succeeded()){
                    context.response().setStatusCode(204).end();
                }else {
                    sendError(503, context.response());
                }
            });
        }
    }

    private void handleDeleteAll(RoutingContext context){
        client.removeDocuments(COLLETCION_NAME,new JsonObject(),res->{
            if(res.succeeded()){
                context.response().setStatusCode(204).end();
            }else {
                sendError(503, context.response());
            }
        });
    }


    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }
}
