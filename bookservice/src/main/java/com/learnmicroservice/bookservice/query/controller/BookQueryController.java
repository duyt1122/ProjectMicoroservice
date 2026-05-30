package com.learnmicroservice.bookservice.query.controller;

import com.learnmicroservice.bookservice.query.model.BookResponseModel;
import com.learnmicroservice.bookservice.query.queries.GetAllBookQuery;
import com.ltfullstack.commonservice.model.BookResponseCommandModel;
import com.ltfullstack.commonservice.queries.GetBookDetailQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {
    private final QueryGateway queryGateway;

    public BookQueryController(QueryGateway queryGateway){
        this.queryGateway = queryGateway;
    }
    @GetMapping
    public List<BookResponseModel> getAllBooks(){
        GetAllBookQuery query = new GetAllBookQuery();
       List<BookResponseModel>  bookFuture = queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
       return bookFuture;
    }
    @GetMapping("/{bookId}")
    public BookResponseCommandModel getBookDetail(@PathVariable String bookId){
        GetBookDetailQuery query = new GetBookDetailQuery(bookId);
        return queryGateway.query(query,ResponseTypes.instanceOf(BookResponseCommandModel.class)).join();
    }
}
