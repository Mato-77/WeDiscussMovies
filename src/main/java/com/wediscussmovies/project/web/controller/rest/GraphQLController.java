package com.wediscussmovies.project.web.controller.rest;

import com.wediscussmovies.project.service.*;
import com.wediscussmovies.project.service.impl.*;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController()
@RequestMapping(value = "/graphql")
public class GraphQLController {

    private final GraphQL graphQL;

    public GraphQLController(DiscussionServiceImpl discussionService,
                             GenreServiceImpl genreService,
                             MovieServiceImpl movieService,
                             PersonServiceImpl personService,
                             ReplyServiceImpl replyService,
                             UserServiceImpl userService){
        GraphQLSchema graphQLSchema = new GraphQLSchemaGenerator()
                .withResolverBuilders(
                        new AnnotatedResolverBuilder())
                .withOperationsFromSingletons(discussionService,genreService,movieService,personService,replyService,userService)
                .withValueMapperFactory(new JacksonValueMapperFactory())
                .generate();
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();

    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> graphql(@RequestBody Map<String,String> request,
                                      HttpServletRequest raw){
        ExecutionResult result = graphQL.execute(
                ExecutionInput.newExecutionInput()
                .query(request.get("query"))
                .operationName(request.get("operationName"))
                .context(raw).build());
        return result.toSpecification();
    }
}
