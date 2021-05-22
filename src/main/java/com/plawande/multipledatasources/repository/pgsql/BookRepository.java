package com.plawande.multipledatasources.repository.pgsql;

import com.plawande.multipledatasources.model.pgsql.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
}
