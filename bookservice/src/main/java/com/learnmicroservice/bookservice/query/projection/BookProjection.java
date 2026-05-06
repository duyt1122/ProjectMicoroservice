package com.learnmicroservice.bookservice.query.projection;

import com.learnmicroservice.bookservice.command.data.Book;
import com.learnmicroservice.bookservice.command.data.BookRepository;
import com.learnmicroservice.bookservice.query.model.BookResponseModel;
import com.learnmicroservice.bookservice.query.queries.GetAllBookQuery;
import com.learnmicroservice.bookservice.query.queries.GetBookDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

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
    public BookResponseModel getDetailBook(GetBookDetailQuery query) throws Exception{
        Book book = bookRepository.findById(query.getId()).orElseThrow(() ->
           new Exception("Not found book with book id = "  + query.getId())
        );
        BookResponseModel bookResponse = new BookResponseModel();
        BeanUtils.copyProperties(book,bookResponse);
        return bookResponse;
    }
}
