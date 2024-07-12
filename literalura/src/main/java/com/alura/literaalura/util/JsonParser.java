package com.alura.literaalura.util;

import com.alura.literaalura.model.Author;
import com.alura.literaalura.model.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public List<Book> parseBooks(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Book> books = new ArrayList<>();
        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode resultsNode = rootNode.path("results");
            for (JsonNode bookNode : resultsNode) {
                Book book = new Book();
                book.setTitle(bookNode.path("title").asText());
                
                List<Author> authors = new ArrayList<>();
                for (JsonNode authorNode : bookNode.path("authors")) {
                    Author author = new Author();
                    author.setName(authorNode.path("name").asText());
                    author.setBirthYear(authorNode.path("birth_year").asInt());
                    author.setDeathYear(authorNode.path("death_year").asInt());
                    authors.add(author);
                }
                book.setAuthors(authors);
                book.setLanguages(objectMapper.convertValue(bookNode.path("languages"), List.class));
                book.setDownloadCount(bookNode.path("download_count").asInt());

                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
