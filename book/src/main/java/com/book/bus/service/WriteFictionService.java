package com.book.bus.service;


import com.book.bus.domain.Fiction;
import org.jsoup.nodes.Document;

public interface WriteFictionService {

    void insert(String fictionURL);

    Fiction getFictions(Document document);

}
