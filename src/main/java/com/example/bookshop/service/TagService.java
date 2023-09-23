package com.example.bookshop.service;

import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.TagRepository;
import com.example.bookshop.struct.book.BookEntity;
import com.example.bookshop.struct.tags.TagEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    @Autowired
    private final TagRepository tagRepository;
    @Autowired
    private final BookRepository bookRepository;

    public List<TagEntity> getTags(){
        return tagRepository.findAll();
    }

    public TagEntity gatOneTag(Integer id){
        return tagRepository.getOne(id);
    }
    public Page<BookEntity> getBooksOfTags(Integer tagId, Integer offset, Integer limit){
        Pageable nextPage = PageRequest.of(offset, limit);
        return bookRepository.getBookEntitiesByTagId(tagId, nextPage);
    }
}
