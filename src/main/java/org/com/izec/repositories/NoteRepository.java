package org.com.izec.repositories;

import org.com.izec.domeins.Note;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoteRepository extends CrudRepository<Note, Long> {
    List<Note> findByTag(String tag);
}
