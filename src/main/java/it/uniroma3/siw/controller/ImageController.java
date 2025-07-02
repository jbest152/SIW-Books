package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("author/{id}")
    public ResponseEntity<byte[]> getImageByMedicationId(@PathVariable Long id) {
        Author author = authorService.findById(id);

        if (author.getImage() != null) {
            byte[] imageData = author.getImage().getData();
            String contentType = author.getImage().getType();

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageData);
        }

        return ResponseEntity.notFound().build();
    }
}
