package org.example.cotroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.example.dao.entity.Book;
import org.example.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
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
        modelAndView.setViewName("/infoAllBook");
        modelAndView.addObject("books",allBooks);
        return modelAndView;
    }

    @GetMapping("/home/infoBook")
    public ModelAndView infoBook(Model model) {
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("/infoBook");
        modelAndView.addObject("book",model.getAttribute("books"));
        return modelAndView;
    }

    @GetMapping("/home/search")
    public ModelAndView searchBook(Book bookEntity) {
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("/search");
        modelAndView.addObject("book", bookEntity);
        return modelAndView;
    }

    @PostMapping("/home/search")
    public ModelAndView search(@Valid @ModelAttribute("bookEntity") Book bookEntity, BindingResult result,
                               Model model, RedirectAttributes attributes) {
        if (result.hasFieldErrors("title")){
            return searchBook(bookEntity);
        }
        String book = String.valueOf(bookService.findBookTitle(bookEntity.getTitle()));

        if (bookService.findBookTitle(bookEntity.getTitle()).isEmpty()) {
            return error("BOOK_NOT_FOUND");
        }
        attributes.addFlashAttribute("books", book);
        model.addAttribute("books", book);
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

    @GetMapping("/home/error")
    public ModelAndView AccessDenied(User user) {
        ModelAndView modelAndView= new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("user" , user);
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
        modelAndView.setViewName("/createOrUpdateResult");
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
}
