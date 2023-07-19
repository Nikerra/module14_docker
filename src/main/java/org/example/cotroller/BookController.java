package org.example.cotroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.example.dao.entity.Book;
import org.example.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping({"/", "/home"})
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @GetMapping("/home/infoAllBook")
    public ModelAndView infoAllBook() {
        List<Book> allBooks = bookService.getBooksAll();
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("infoAllBook");
        modelAndView.addObject("books",allBooks);
        return modelAndView;
    }

    @GetMapping("/home/infoBook")
    public ModelAndView infoBook(Model model) {
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("infoBook");
        modelAndView.addObject("bookInfo",model.getAttribute("bookInfo"));
        return modelAndView;
    }

    @GetMapping("/home/search")
    public ModelAndView searchBook(Book bookEntity) {
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("search");
        modelAndView.addObject("bookInfo", bookEntity);
        return modelAndView;
    }

    @PostMapping("/home/search")
    public ModelAndView search(@Valid @ModelAttribute("books") Book bookEntity, BindingResult result,
                               Model model, RedirectAttributes attributes) {
        if (result.hasFieldErrors("title")){
            return searchBook(bookEntity);
        }
        String book = String.valueOf(bookService.findBookTitle(bookEntity.getTitle()));

        if (bookService.findBookTitle(bookEntity.getTitle()).isEmpty()) {
            return error("BOOK_NOT_FOUND");
        }
        attributes.addFlashAttribute("bookInfo", book);
        model.addAttribute("bookInfo", book);
        return infoBook(model);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/home/errors")
    public ModelAndView error(String error) {
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("errorsFound");
        modelAndView.addObject("message", error);
        return modelAndView;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/home/delete")
    public ModelAndView deleteBook(Book bookEntity) {
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("deleteForm");
        modelAndView.addObject("bookEntity", bookEntity);
        return modelAndView;
    }
    @PostMapping("/home/delete")
    public ModelAndView deleteBookByID(@Valid @ModelAttribute("bookEntity") Book bookEntity, BindingResult result,
                                       Model model, RedirectAttributes attributes) {

        if (result.hasFieldErrors("id")){
            return deleteBook(bookEntity);
        }

        String book = String.valueOf(bookService.findById(bookEntity.getId()));

        if (bookService.findById(bookEntity.getId()).isEmpty()) {
            return error("BOOK_NOT_FOUND");
        }

        bookService.delete(bookEntity.getId());
        attributes.addFlashAttribute("books", book);
        model.addAttribute("books", book);
        return deleteBook(model);
    }
    @GetMapping("/home/deleteResult")
    public ModelAndView deleteBook(Model model) {
        System.out.println(model);
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("deleteResult");
        modelAndView.addObject("result", model.getAttribute("books"));
        return modelAndView;
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/home/createOrUpdate")
    public ModelAndView create(Book bookEntity) {
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("createOrUpdate");
        modelAndView.addObject("bookEntity", bookEntity);
        return modelAndView;
    }
    @GetMapping("/home/createOrUpdateResult")
    public ModelAndView create(Model model) {
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("createOrUpdateResult");
        modelAndView.addObject("result", model.getAttribute("books"));
        return modelAndView;
    }

    @PostMapping("/home/createOrUpdate")
    public ModelAndView createBook(@Valid @ModelAttribute("bookEntity") Book bookEntity, BindingResult result,
                                   Model model, RedirectAttributes attributes) {
        if (result.hasErrors()){
            return create(bookEntity);
        }
        bookService.createOrUpdate(bookEntity.getTitle(),bookEntity.getAuthor(),
                bookEntity.getLanguage(), bookEntity.getCategory(), bookEntity.isActive());
        attributes.addFlashAttribute("books", bookEntity);
        model.addAttribute("books", bookEntity);
        return create(model);
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping(value = "/home/category")
    public ModelAndView categoryJson(Book book) {
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("category");
        modelAndView.addObject("category", book);
        return modelAndView;
    }

    @PostMapping(value = "/home/category", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> categoryJsonPrint(@Valid @ModelAttribute("bookCategory")Book bookEntity, BindingResult bindingResult,
                                        Model model, RedirectAttributes attributes, @RequestParam String category) throws IOException {

        System.out.println("category=" + category);
        List<Book> bookCategory = bookService.findBookCategory(category);
        System.out.println("Book category=" + bookCategory);
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("BookListCategory.json");
        mapper.writeValue(file, bookCategory);
        return  bookCategory;
    }
    @GetMapping(value = "/home/categoryString", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> categoryJsonResult(Model model, @RequestParam(required = false, value = "category") String category) {
        System.out.println("categoryJsonResult category=" + category);
        List<Book> result = bookService.findBookCategory(category);
        System.out.println(category);
        return result;
    }
}
