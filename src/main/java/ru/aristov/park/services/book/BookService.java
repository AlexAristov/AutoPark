package ru.aristov.park.services.book;

import ru.aristov.park.models.book.Book;
import ru.aristov.park.services.CRUDService;

import java.util.List;

public interface BookService extends CRUDService<Book> {
    public List<Book> getByOwnerId(Integer ownerId);
}
