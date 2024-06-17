package ru.aristov.park.services.book;

import ru.aristov.park.models.book.Book;
import ru.aristov.park.repositories.book.BookRepository;
import ru.aristov.park.repositories.book.BookRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository = new BookRepositoryImpl();

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public Optional<Book> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    public Book create(Book entity) {
        return null;
    }

    @Override
    public Book update(Book entity) {
        return bookRepository.update(entity);
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public List<Book> getByOwnerId(Integer ownerId) {
        return bookRepository.getByOwnerId(ownerId);
    }
}
