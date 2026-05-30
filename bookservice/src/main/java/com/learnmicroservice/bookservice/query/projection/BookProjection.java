package com.learnmicroservice.bookservice.query.projection;

import com.learnmicroservice.bookservice.command.data.Book;
import com.learnmicroservice.bookservice.command.data.BookRepository;
import com.learnmicroservice.bookservice.query.model.BookResponseModel;
import com.learnmicroservice.bookservice.query.queries.GetAllBookQuery;
import com.ltfullstack.commonservice.model.BookResponseCommandModel;
import com.ltfullstack.commonservice.queries.GetBookDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
public class BookProjection {

    private final BookRepository bookRepository;

    public BookProjection(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }

    @QueryHandler
    public List<BookResponseModel> getAllBooks(GetAllBookQuery query){
        List<Book> books = bookRepository.findAll();
        List<BookResponseModel> bookrequest =  books.stream().map(book -> {
            BookResponseModel model = new BookResponseModel();
            BeanUtils.copyProperties(book,model);
            return model;
        }).toList();
        return bookrequest;
    }
    @QueryHandler
    public BookResponseCommandModel getDetailBook(GetBookDetailQuery query) throws Exception{
        Book book = bookRepository.findById(query.getId()).orElseThrow(() ->
           new Exception("Not found book with book id = "  + query.getId())
        );
        BookResponseCommandModel bookResponse = new BookResponseCommandModel();
        BeanUtils.copyProperties(book,bookResponse);
        return bookResponse;
    }
}
