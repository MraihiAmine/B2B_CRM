package B2B.CRM.dashboard.controllers.statistics;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.multipart.MultipartFile;

import B2B.CRM.dashboard.entities.Article;
import B2B.CRM.dashboard.repositories.ArticleRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/article/")
public class ArticleController {

    public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/uploads_users";
    static String trouve=null;

    private final ArticleRepository articleRepository;
    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }
    
    @GetMapping("list")
    public String listProviders(Model model) {
        //model.addAttribute("articles", null);
        List<Article> la = (List<Article>)articleRepository.findAll();
        if(la.size()==0)
            la = null;
        model.addAttribute("articles", la);
        model.addAttribute("trouve", trouve); 
        return "article/listArticles";
    }
    
    @GetMapping("add")
    public String showAddArticleForm(Model model) {

        model.addAttribute("providers");
        model.addAttribute("article", new Article());
        return "article/addArticle";
    }
    
    @PostMapping("add")
    //@ResponseBody
    public String addArticle(@Valid Article article, BindingResult result,
                             @RequestParam("files") MultipartFile[] files
    ) {

        MultipartFile file = files[0];
        Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());

        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Stockage du name du ficher dans la base
        StringBuilder fileName = new StringBuilder();
        fileName.append(file.getOriginalFilename());
        article.setPicture(fileName.toString());

        System.out.println(article);


        articleRepository.save(article);
        return "redirect:list";

        //return article.getLabel() + " " +article.getPrice() + " " + p.toString();
    }
    
    @GetMapping("delete/{id}")
    public String deleteProvider(@PathVariable("id") long id, Model model) {
        /*Article artice = articleRepository.findById(id)
            .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + id));*/
        Optional<Article>article = articleRepository.findById(id);
        
        if(article.isPresent())
        {
            articleRepository.delete(article.get());
            trouve="existe";
        }
        else {  // le problème
            trouve="inexiste";

        }
      return "redirect:../list";
    }
    
    @GetMapping("edit/{id}")
    public String showArticleFormToUpdate(@PathVariable("id") long id, Model model) {
        Article article = articleRepository.findById(id)
            .orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + id));

        model.addAttribute("article", article);
        
        return "article/updateArticle";
    }
    @PostMapping("edit")
    public String updateArticle( @Valid Article article, BindingResult result,
        Model model) {
        if (result.hasErrors()) {
            model.addAttribute("article", article);
            System.out.println("result: " + result);
            return "article/updateArticle";
        }
        articleRepository.save(article);
        return "redirect:list";
    }

    @GetMapping("show/{id}")
    public String showArticleDetails(@PathVariable("id") long id, Model model) {
        Article article = articleRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + id));

        model.addAttribute("article", article);

        return "article/showArticle";
    }


}
