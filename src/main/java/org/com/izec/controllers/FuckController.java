package org.com.izec.controllers;


import org.com.izec.domeins.Note;
import org.com.izec.domeins.User;
import org.com.izec.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
public class FuckController {

    @Autowired
    private NoteRepository noteRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/fuck")
    public String main(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Note> notes = noteRepository.findAll();

        if (filter != null && !filter.isEmpty()) {
            notes = noteRepository.findByTag(filter);
        } else {
            notes = noteRepository.findAll();
        }

        model.addAttribute("notes", notes);
        model.addAttribute("filter", filter);

        if (user.isAdmin()) {
            model.addAttribute("isAdmin", user.isAdmin());
        }

        return "pattern";
    }

    @PostMapping("/fuck")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Model model,
            @RequestParam("file") MultipartFile file) throws IOException {
        Note note = new Note(text, tag, user);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));
            note.setFilename(resultFileName);
        }

        noteRepository.save(note);

        Iterable<Note> notes = noteRepository.findAll();

        model.addAttribute("notes", notes);


        if (user.isAdmin()) {
            model.addAttribute("isAdmin", user.isAdmin());
        }

        return "pattern";
    }


}
