package ru.aristov.park.repositories.book;

import ru.aristov.park.models.book.Book;
import ru.aristov.park.repositories.CRUDRepository;

import java.util.List;

public interface BookRepository extends CRUDRepository<Book> {
    public List<Book> getByOwnerId(Integer ownerId);
}
